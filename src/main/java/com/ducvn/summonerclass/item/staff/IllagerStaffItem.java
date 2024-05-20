package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.projectile.IllagerStaffProjectileEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedPillagerEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedRavagerEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedVindicatorEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedIllagerArmor;
import com.ducvn.summonerclass.item.armor.basic.IllagerArmor;
import com.ducvn.summonerclass.potion.SummonerClassPotionsRegister;
import com.google.common.collect.Lists;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
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

public class IllagerStaffItem extends StaffItem implements IVanishable, IStaffItem {
    public IllagerStaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aSummon 2 random vanilla illagers (except evoker) when hit enemy, right click to shoot projectile\u00A7r"
                )
        );
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aShift-right click to summon minion attack the entity hurt you recently\u00A7r"
                )
        );
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return super.getEquipmentSlot(stack);
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
                IllagerStaffProjectileEntity illagerStaffProjectile = new IllagerStaffProjectileEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                illagerStaffProjectile.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                illagerStaffProjectile.setUpProjectile(illagerStaffProjectile, this, player, hand);
                illagerStaffProjectile.setBonusMinion(getBonusMinion(player));
                world.addFreshEntity(illagerStaffProjectile);
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
        int illagerNumber = 2 + getBonusMinion((PlayerEntity) user);
        for (int num = 1; num <= illagerNumber; num++) {
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            if (roll.nextBoolean()){
                SummonedPillagerEntity illager = new SummonedPillagerEntity(SummonerClassEntitiesRegister.SUMMONED_PILLAGER.get(), user.level);
                ItemStack illagerCrossBow = Items.CROSSBOW.getDefaultInstance();
                if (xAdjust == 0 && zAdjust == 0) {
                    illager.setPos(user.position().x,
                            user.position().y + 3D,
                            user.position().z);
                } else {
                    illager.setPos(user.position().x + xAdjust,
                            user.position().y + yAdjust,
                            user.position().z + zAdjust);
                }
                illager.setAttackTarget(entity, user);
                illager.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s pillager"));
                if (hasMinionWitherEffect(stack) && !hasFullArmorSet((PlayerEntity) user)){
                    ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                    arrow.setCount(64);
                    illager.setItemInHand(Hand.OFF_HAND,
                            PotionUtils.setPotion(
                                    arrow,
                                    SummonerClassPotionsRegister.WITHER_POTION.get())
                    );
                    illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                }
                if (hasMinionPoisonEffect(stack) && !hasFullArmorSet((PlayerEntity) user)){
                    ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                    arrow.setCount(64);
                    illager.setItemInHand(Hand.OFF_HAND,
                            PotionUtils.setPotion(
                                    arrow,
                                    Potions.POISON)
                    );
                    illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                }
                if (hasMinionFireEffect(stack)){
                    illagerCrossBow.enchant(Enchantments.MULTISHOT, 1);
                }
                if (hasMinionSlownessEffect(stack) && !hasFullArmorSet((PlayerEntity) user)){
                    ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                    arrow.setCount(64);
                    illager.setItemInHand(Hand.OFF_HAND,
                            PotionUtils.setPotion(
                                    arrow,
                                    Potions.SLOWNESS)
                    );
                    illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                }
                if (hasMinionWeaknessEffect(stack) && !hasFullArmorSet((PlayerEntity) user)){
                    ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                    arrow.setCount(64);
                    illager.setItemInHand(Hand.OFF_HAND,
                            PotionUtils.setPotion(
                                    arrow,
                                    Potions.WEAKNESS)
                    );
                    illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                }
                if (hasMinionThornEffect(stack)){
                    illager.setThorn();
                }
                if (hasMinionSupremeEffect(stack)){
                    illager.setSupreme();
                }
                if (hasMinionArmorEffect((PlayerEntity) user, SummonerClassEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                        || hasMinionSupremeEffect(stack)){
                    illagerCrossBow.enchant(Enchantments.QUICK_CHARGE, 1);
                }
                if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_HEALTH.get(), 3)
                        || hasMinionSupremeEffect(stack)){
                    illager = (SummonedPillagerEntity) addBonusHealth(illager, illager.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerClassConfig.minion_health.get());
                }
                if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                        || hasMinionSupremeEffect(stack)){
                    illager.setExplode();
                }
                if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                        || hasMinionSupremeEffect(stack)){
                    illager.setBuffMaster();
                }
                if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_SPEED.get(), 1)
                        || hasMinionSupremeEffect(stack)){
                    illager = (SummonedPillagerEntity) addBonusSpeed(illager, illager.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * SummonerClassConfig.minion_speed.get());
                }
                if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                        || hasMinionSupremeEffect(stack)){
                    illager = (SummonedPillagerEntity) addBonusKnockbackRes(illager, SummonerClassConfig.minion_resistance.get());
                }
                if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                        || hasMinionSupremeEffect(stack)){
                    illager.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerClassConfig.minion_invisible_duration.get()));
                }
                if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                        || hasMinionSupremeEffect(stack)){
                    illager.setMagnetize();
                }
                illager.setItemInHand(Hand.MAIN_HAND, illagerCrossBow);
                illager.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                if (hasFullArmorSet((PlayerEntity) user)){
                    ItemStack offHandItem = Items.FIREWORK_ROCKET.getDefaultInstance();
                    offHandItem.setCount(64);
                    CompoundNBT explosionData = new CompoundNBT();
                    List<Integer> list = Lists.newArrayList();
                    list.add(15728640);
                    list.add(1852922);
                    explosionData.putIntArray("Colors", list);
                    explosionData.putByte("Type", (byte) FireworkRocketItem.Shape.BURST.getId());
                    ListNBT listnbt = new ListNBT();
                    listnbt.add(explosionData);
                    offHandItem.getOrCreateTagElement("Fireworks").put("Explosions", listnbt);
                    illager.setItemInHand(Hand.OFF_HAND, offHandItem);
                    illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                }
                if (roll.nextInt(100) < 3){
                    illager.setBaby(true);
                }
                user.level.addFreshEntity(illager);
            }
            else {
                SummonedVindicatorEntity illager = new SummonedVindicatorEntity(SummonerClassEntitiesRegister.SUMMONED_VINDICATOR.get(), user.level);
                if (xAdjust == 0 && zAdjust == 0) {
                    illager.setPos(user.position().x,
                            user.position().y + 3D,
                            user.position().z);
                } else {
                    illager.setPos(user.position().x + xAdjust,
                            user.position().y + yAdjust,
                            user.position().z + zAdjust);
                }
                illager.setAttackTarget(entity, user);
                illager.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s vindicator"));
                giveMinionEnchantment(illager, stack, (PlayerEntity) user);
                if (hasFullArmorSet((PlayerEntity) user)){
                    illager.setItemInHand(Hand.MAIN_HAND, Items.GOLDEN_AXE.getDefaultInstance());
                }
                else {
                    illager.setItemInHand(Hand.MAIN_HAND, Items.IRON_AXE.getDefaultInstance());
                }
                illager.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                if (roll.nextInt(100) < 3){
                    illager.setBaby(true);
                }
                user.level.addFreshEntity(illager);
            }
        }
        if (hasAdvanceArmorSet((PlayerEntity) user) && roll.nextInt(10) < 1){
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedRavagerEntity ravager = new SummonedRavagerEntity(SummonerClassEntitiesRegister.SUMMONED_RAVAGER.get(), user.level);
            if (xAdjust == 0 && zAdjust == 0) {
                ravager.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            } else {
                ravager.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            ravager.setAttackTarget(entity, user);
            ravager.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s ravager"));
            giveMinionEnchantment(ravager, stack, (PlayerEntity) user);
            user.level.addFreshEntity(ravager);
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
            if (!(stack.getItem() instanceof IllagerArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof IllagerArmor){
                haveAtLeastOne = true;
                if (EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
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
            if (!(stack.getItem() instanceof AdvancedIllagerArmor)){
                return false;
            }
        }
        return true;
    }
}
