package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.projectile.PiglinStaffProjectileEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedHoglinEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedPiglinBruteEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedPiglinEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedPiglinArmor;
import com.ducvn.summonerclass.item.armor.basic.PiglinArmor;
import com.ducvn.summonerclass.potion.SummonerClassPotionsRegister;
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

public class PiglinStaffItem extends StaffItem implements IVanishable, IStaffItem {
    public PiglinStaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aSummon 1-4 piglins when hit enemy, right click to shoot projectile\u00A7r"
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
                PiglinStaffProjectileEntity piglinStaffProjectile = new PiglinStaffProjectileEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                piglinStaffProjectile.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                piglinStaffProjectile.setUpProjectile(piglinStaffProjectile, this, player, hand);
                piglinStaffProjectile.setBonusMinion(getBonusMinion(player));
                world.addFreshEntity(piglinStaffProjectile);
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
        int piglinNumber = roll.nextInt(4) + 1 + getBonusMinion((PlayerEntity) user);
        for (int num = 1; num <= piglinNumber; num++) {
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            if (hasFullArmorSet((PlayerEntity) user)
                    && roll.nextInt(100) < (hasAdvanceArmorSet((PlayerEntity) user) ? 10 : 5)){
                SummonedPiglinBruteEntity piglin = new SummonedPiglinBruteEntity(SummonerClassEntitiesRegister.SUMMONED_PIGLIN_BRUTE.get(), user.level);
                if (xAdjust == 0 && zAdjust == 0){
                    piglin.setPos(user.position().x,
                            user.position().y + 3D,
                            user.position().z);
                }
                else {
                    piglin.setPos(user.position().x + xAdjust,
                            user.position().y + yAdjust,
                            user.position().z + zAdjust);
                }
                piglin.setAttackTarget(entity, (LivingEntity) user);
                piglin.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s piglin"));
                giveMinionEnchantment(piglin, stack, (PlayerEntity) user);
                piglin.setItemInHand(Hand.MAIN_HAND, Items.GOLDEN_AXE.getDefaultInstance());
                piglin.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                user.level.addFreshEntity(piglin);
            }
            else {
                SummonedPiglinEntity piglin = new SummonedPiglinEntity(SummonerClassEntitiesRegister.SUMMONED_PIGLIN.get(), user.level);
                if (xAdjust == 0 && zAdjust == 0){
                    piglin.setPos(user.position().x,
                            user.position().y + 3D,
                            user.position().z);
                }
                else {
                    piglin.setPos(user.position().x + xAdjust,
                            user.position().y + yAdjust,
                            user.position().z + zAdjust);
                }
                piglin.setAttackTarget(entity, (LivingEntity) user);
                piglin.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s piglin"));
                if (hasFullArmorSet((PlayerEntity) user)){
                    ItemStack piglinCrossBow = Items.CROSSBOW.getDefaultInstance();
                    if (xAdjust == 0 && zAdjust == 0) {
                        piglin.setPos(user.position().x,
                                user.position().y + 3D,
                                user.position().z);
                    } else {
                        piglin.setPos(user.position().x + xAdjust,
                                user.position().y + yAdjust,
                                user.position().z + zAdjust);
                    }
                    if (hasMinionWitherEffect(stack)){
                        ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                        arrow.setCount(64);
                        piglin.setItemInHand(Hand.OFF_HAND,
                                PotionUtils.setPotion(
                                        arrow,
                                        SummonerClassPotionsRegister.WITHER_POTION.get())
                        );
                        piglin.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                    }
                    if (hasMinionPoisonEffect(stack)){
                        ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                        arrow.setCount(64);
                        piglin.setItemInHand(Hand.OFF_HAND,
                                PotionUtils.setPotion(
                                        arrow,
                                        Potions.POISON)
                        );
                        piglin.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                    }
                    if (hasMinionFireEffect(stack)){
                        piglinCrossBow.enchant(Enchantments.MULTISHOT, 1);
                    }
                    if (hasMinionSlownessEffect(stack)){
                        ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                        arrow.setCount(64);
                        piglin.setItemInHand(Hand.OFF_HAND,
                                PotionUtils.setPotion(
                                        arrow,
                                        Potions.SLOWNESS)
                        );
                        piglin.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                    }
                    if (hasMinionWeaknessEffect(stack)){
                        ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                        arrow.setCount(64);
                        piglin.setItemInHand(Hand.OFF_HAND,
                                PotionUtils.setPotion(
                                        arrow,
                                        Potions.WEAKNESS)
                        );
                        piglin.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                    }
                    if (hasMinionThornEffect(stack)){
                        piglin.setThorn();
                    }
                    if (hasMinionSupremeEffect(stack)){
                        piglin.setSupreme();
                    }
                    if (hasMinionArmorEffect((PlayerEntity) user, SummonerClassEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                            || hasMinionSupremeEffect(stack)){
                        piglinCrossBow.enchant(Enchantments.QUICK_CHARGE, 1);
                    }
                    if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_HEALTH.get(), 3)
                            || hasMinionSupremeEffect(stack)){
                        piglin = (SummonedPiglinEntity) addBonusHealth(piglin, piglin.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerClassConfig.minion_health.get());
                    }
                    if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                            || hasMinionSupremeEffect(stack)){
                        piglin.setExplode();
                    }
                    if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                            || hasMinionSupremeEffect(stack)){
                        piglin.setBuffMaster();
                    }
                    if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_SPEED.get(), 1)
                            || hasMinionSupremeEffect(stack)){
                        piglin = (SummonedPiglinEntity) addBonusSpeed(piglin, piglin.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * SummonerClassConfig.minion_speed.get());
                    }
                    if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                            || hasMinionSupremeEffect(stack)){
                        piglin = (SummonedPiglinEntity) addBonusKnockbackRes(piglin, SummonerClassConfig.minion_resistance.get());
                    }
                    if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                            || hasMinionSupremeEffect(stack)){
                        piglin.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerClassConfig.minion_invisible_duration.get()));
                    }
                    if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                            || hasMinionSupremeEffect(stack)){
                        piglin.setMagnetize();
                    }
                    piglin.setItemInHand(Hand.MAIN_HAND, piglinCrossBow);
                }
                else {
                    piglin.setItemInHand(Hand.MAIN_HAND, Items.GOLDEN_AXE.getDefaultInstance());
                    giveMinionEnchantment(piglin, stack, (PlayerEntity) user);
                }
                piglin.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                user.level.addFreshEntity(piglin);
            }
        }
        if (roll.nextInt(100) < 3){
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedHoglinEntity hoglin = new SummonedHoglinEntity(SummonerClassEntitiesRegister.SUMMONED_HOGLIN.get(), user.level);
            if (xAdjust == 0 && zAdjust == 0){
                hoglin.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            }
            else {
                hoglin.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            hoglin.setAttackTarget(entity, (LivingEntity) user);
            hoglin.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s hoglin"));
            giveMinionEnchantment(hoglin, stack, (PlayerEntity) user);
            hoglin.setBaby(true);
            user.level.addFreshEntity(hoglin);
        }
        if (hasAdvanceArmorSet((PlayerEntity) user) && roll.nextInt(10) < 2){
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedHoglinEntity hoglin = new SummonedHoglinEntity(SummonerClassEntitiesRegister.SUMMONED_HOGLIN.get(), user.level);
            if (xAdjust == 0 && zAdjust == 0){
                hoglin.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            }
            else {
                hoglin.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            hoglin.setAttackTarget(entity, (LivingEntity) user);
            hoglin.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s hoglin"));
            giveMinionEnchantment(hoglin, stack, (PlayerEntity) user);
            hoglin.isAdult();
            user.level.addFreshEntity(hoglin);
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
            if (!(stack.getItem() instanceof PiglinArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof PiglinArmor){
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
            if (!(stack.getItem() instanceof AdvancedPiglinArmor)){
                return false;
            }
        }
        return true;
    }
}
