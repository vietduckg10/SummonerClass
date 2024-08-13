package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.projectile.SkeletonStaffProjectileEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedSkeletonEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedSkeletonArmor;
import com.ducvn.summonerclass.item.armor.basic.SkeletonArmor;
import com.ducvn.summonercoremod.config.SummonerCoreConfig;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import com.ducvn.summonercoremod.item.staff.IStaffItem;
import com.ducvn.summonercoremod.item.staff.StaffItem;
import com.ducvn.summonercoremod.potion.SummonerCorePotionsRegister;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SkeletonStaffItem extends StaffItem implements IVanishable, IStaffItem {
    public SkeletonStaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aSummon 2 skeletons when hit enemy, right click to shoot projectile\u00A7r"
                )
        );
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aShift-right click to summon minion attack the entity hurt you recently\u00A7r"
                )
        );
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide){
            if (player.isShiftKeyDown()){
                if (player.getLastHurtByMob() != null && player.getLastHurtByMob().isAlive()){
                    performSummon(player.getItemInHand(hand), player.getLastHurtByMob(), player);
                    player.getItemInHand(hand).hurtAndBreak(1, player, (p_220048_0_) -> {
                        p_220048_0_.broadcastBreakEvent(hand);
                    });
                }
            }
            else {
                SkeletonStaffProjectileEntity skeletonStaffProjectile = new SkeletonStaffProjectileEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                skeletonStaffProjectile.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                skeletonStaffProjectile.setUpProjectile(skeletonStaffProjectile, this, player, hand);
                skeletonStaffProjectile.setBonusMinion(getBonusMinion(player));
                skeletonStaffProjectile.setBonusMinion(getBonusMinion(player));
                world.addFreshEntity(skeletonStaffProjectile);
                player.getItemInHand(hand).hurtAndBreak(1, player, (p_220048_0_) -> {
                    p_220048_0_.broadcastBreakEvent(hand);
                });
            }
            return ActionResult.success(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity user) {
        if (!user.level.isClientSide && user instanceof PlayerEntity){
            performSummon(stack, entity, user);
            user.getItemInHand(Hand.MAIN_HAND).hurtAndBreak(1, user, (p_220048_0_) -> {
                p_220048_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
        }
        return super.hurtEnemy(stack, entity, user);
    }

    private void performSummon(ItemStack stack, LivingEntity entity, LivingEntity user){
        Random roll = new Random();
        int numSkeleton = 2 + getBonusMinion((PlayerEntity) user);
        boolean summonMelee = false;
        if (hasFullArmorSet((PlayerEntity) user)
                && roll.nextInt(10) < (hasAdvanceArmorSet((PlayerEntity) user) ? 5 : 3)){
            numSkeleton = numSkeleton + 2;
            summonMelee = true;
        }
        for (int num = 1; num <= numSkeleton; num++) {
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedSkeletonEntity skeleton = new SummonedSkeletonEntity(SummonerClassEntitiesRegister.SUMMONED_SKELETON.get(), user.level);
            ItemStack skeletonBow = Items.BOW.getDefaultInstance();
            if (xAdjust == 0 && zAdjust == 0) {
                skeleton.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            } else {
                skeleton.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            skeleton.setAttackTarget(entity, user);
            skeleton.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s skeleton"));
            if (hasFullArmorSet((PlayerEntity) user)){
                skeleton.setImmuneSunLight();
            }
            if (hasMinionWitherEffect(stack)){
                if (summonMelee && num >= (numSkeleton - 1)){
                    skeleton.setWitherHit();
                }
                else {
                    skeleton.setItemInHand(Hand.OFF_HAND,
                            PotionUtils.setPotion(
                                    Items.TIPPED_ARROW.getDefaultInstance(),
                                    SummonerCorePotionsRegister.WITHER_POTION.get())
                    );
                    skeleton.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                }
            }
            if (hasMinionPoisonEffect(stack)){
                if (summonMelee && num >= (numSkeleton - 1)){
                    skeleton.setPoisonHit();
                }
                else {
                    skeleton.setItemInHand(Hand.OFF_HAND,
                            PotionUtils.setPotion(
                                    Items.TIPPED_ARROW.getDefaultInstance(),
                                    Potions.POISON)
                    );
                    skeleton.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                }
            }
            if (hasMinionFireEffect(stack)){
                if (summonMelee && num >= (numSkeleton - 1)){
                    skeleton.setFireHit();
                }
                else {
                    skeletonBow.enchant(Enchantments.FLAMING_ARROWS, 1);
                }
            }
            if (hasMinionSlownessEffect(stack)){
                if (summonMelee && num >= (numSkeleton - 1)){
                    skeleton.setSlownessHit();
                }
                else {
                    skeleton.setItemInHand(Hand.OFF_HAND,
                            PotionUtils.setPotion(
                                    Items.TIPPED_ARROW.getDefaultInstance(),
                                    Potions.SLOWNESS)
                    );
                    skeleton.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                }
            }
            if (hasMinionWeaknessEffect(stack)){
                if (summonMelee && num >= (numSkeleton - 1)){
                    skeleton.setWeaknessHit();
                }
                else {
                    skeleton.setItemInHand(Hand.OFF_HAND,
                            PotionUtils.setPotion(
                                    Items.TIPPED_ARROW.getDefaultInstance(),
                                    Potions.WEAKNESS)
                    );
                    skeleton.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                }
            }
            if (hasMinionThornEffect(stack)){
                skeleton.setThorn();
            }
            if (hasMinionSupremeEffect(stack)){
                skeleton.setSupreme();
            }
            if (hasMinionArmorEffect((PlayerEntity) user, SummonerCoreEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                    || hasMinionSupremeEffect(stack)){
                if (summonMelee && num >= (numSkeleton - 1)){
                    skeleton = (SummonedSkeletonEntity) addBonusAttack(skeleton, skeleton.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * SummonerCoreConfig.minion_damage.get());
                }
                else {
                    skeletonBow.enchant(Enchantments.POWER_ARROWS, 1);
                }
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerCoreEnchantmentsRegister.MINION_HEALTH.get(), 3)
                    || hasMinionSupremeEffect(stack)){
                skeleton = (SummonedSkeletonEntity) addBonusHealth(skeleton, skeleton.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerCoreConfig.minion_health.get());
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerCoreEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                    || hasMinionSupremeEffect(stack)){
                skeleton.setExplode();
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerCoreEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                    || hasMinionSupremeEffect(stack)){
                skeleton.setBuffMaster();
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerCoreEnchantmentsRegister.MINION_SPEED.get(), 1)
                    || hasMinionSupremeEffect(stack)){
                skeleton = (SummonedSkeletonEntity) addBonusSpeed(skeleton, skeleton.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * SummonerCoreConfig.minion_speed.get());
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerCoreEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                    || hasMinionSupremeEffect(stack)){
                skeleton = (SummonedSkeletonEntity) addBonusKnockbackRes(skeleton, SummonerCoreConfig.minion_resistance.get());
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerCoreEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                    || hasMinionSupremeEffect(stack)){
                skeleton.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerCoreConfig.minion_invisible_duration.get()));
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerCoreEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                    || hasMinionSupremeEffect(stack)){
                skeleton.setMagnetize();
            }
            if (summonMelee && num >= (numSkeleton - 1)){
                if (hasAdvanceArmorSet((PlayerEntity) user)){
                    skeleton.setItemSlot(
                            EquipmentSlotType.CHEST,
                            Items.CHAINMAIL_CHESTPLATE.getDefaultInstance());
                    skeleton.setItemSlot(
                            EquipmentSlotType.LEGS,
                            Items.CHAINMAIL_LEGGINGS.getDefaultInstance());
                    skeleton.setItemSlot(
                            EquipmentSlotType.FEET,
                            Items.CHAINMAIL_BOOTS.getDefaultInstance());
                    skeleton.setItemSlot(
                            EquipmentSlotType.MAINHAND,
                            Items.IRON_SWORD.getDefaultInstance());
                    skeleton.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                    skeleton.setDropChance(EquipmentSlotType.CHEST, 0.0f);
                    skeleton.setDropChance(EquipmentSlotType.LEGS, 0.0f);
                    skeleton.setDropChance(EquipmentSlotType.FEET, 0.0f);
                }
            }
            else {
                skeleton.setItemInHand(Hand.MAIN_HAND, skeletonBow);
                skeleton.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                if (hasAdvanceArmorSet((PlayerEntity) user)){
                    skeleton.setItemSlot(
                            EquipmentSlotType.CHEST,
                            Items.CHAINMAIL_CHESTPLATE.getDefaultInstance());
                    skeleton.setItemSlot(
                            EquipmentSlotType.LEGS,
                            Items.CHAINMAIL_LEGGINGS.getDefaultInstance());
                    skeleton.setItemSlot(
                            EquipmentSlotType.FEET,
                            Items.CHAINMAIL_BOOTS.getDefaultInstance());
                    skeleton.setDropChance(EquipmentSlotType.CHEST, 0.0f);
                    skeleton.setDropChance(EquipmentSlotType.LEGS, 0.0f);
                    skeleton.setDropChance(EquipmentSlotType.FEET, 0.0f);
                }
            }
            user.level.addFreshEntity(skeleton);
            if (roll.nextInt(100) < 3){
                SummonedSkeletonEntity riderSkeleton = new SummonedSkeletonEntity(SummonerClassEntitiesRegister.SUMMONED_SKELETON.get(), user.level);
                ItemStack riderSkeletonBow = Items.BOW.getDefaultInstance();
                riderSkeleton.setAttackTarget(entity, user);
                riderSkeleton.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s skeleton"));
                riderSkeleton.setItemInHand(Hand.MAIN_HAND, riderSkeletonBow);
                riderSkeleton.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                riderSkeleton.setItemSlot(
                        EquipmentSlotType.HEAD,
                        Items.LEATHER_HELMET.getDefaultInstance());
                riderSkeleton.setDropChance(EquipmentSlotType.HEAD, 0.0f);
                user.level.addFreshEntity(riderSkeleton);
                riderSkeleton.startRiding(skeleton);
            }
        }
    }

    private boolean hasFullArmorSet(PlayerEntity player) {
        List<ItemStack> armorList = getArmorSet(player);
        boolean isCombined = true;
        boolean haveAtLeastOne = false;

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof ArmorItem)){
                return false;
            }
            if (!(stack.getItem() instanceof SkeletonArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof SkeletonArmor){
                haveAtLeastOne = true;
                if (EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
                    return true;
                }
            }
        }
        if (isCombined && haveAtLeastOne){
            return true;
        }
        else {
            return false;
        }
    }
    private boolean hasAdvanceArmorSet(PlayerEntity player){
        List<ItemStack> armorList = getArmorSet(player);

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof AdvancedSkeletonArmor)){
                return false;
            }
        }
        return true;
    }
}
