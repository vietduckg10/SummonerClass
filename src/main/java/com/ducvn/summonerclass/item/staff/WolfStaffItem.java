package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.projectile.WolfStaffProjectileEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedWolfEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedWolfArmor;
import com.ducvn.summonerclass.item.armor.basic.WolfArmor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
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

public class WolfStaffItem extends StaffItem implements IVanishable, IStaffItem {
    public WolfStaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aSummon 4 wolves when hit enemy, right click to shoot projectile\u00A7r"
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
                WolfStaffProjectileEntity wolfStaffProjectileEntity = new WolfStaffProjectileEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                wolfStaffProjectileEntity.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                wolfStaffProjectileEntity.setUpProjectile(wolfStaffProjectileEntity, this, player, hand);
                wolfStaffProjectileEntity.setBonusMinion(getBonusMinion(player));
                wolfStaffProjectileEntity.setBonusMinion(getBonusMinion(player));
                world.addFreshEntity(wolfStaffProjectileEntity);
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
        int wolfNumber = 4 + getBonusMinion((PlayerEntity) user);
        for (int num = 1; num <= wolfNumber; num++) {
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedWolfEntity wolf = new SummonedWolfEntity(SummonerClassEntitiesRegister.SUMMONED_WOLF.get(), user.level);
            if (xAdjust == 0 && zAdjust == 0) {
                wolf.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            } else {
                wolf.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            wolf.setAttackTarget(entity, user);
            wolf.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s wolf"));
            if (hasMinionWitherEffect(stack)){
                wolf.setWitherHit();
            }
            if (hasMinionPoisonEffect(stack)){
                wolf.setPoisonHit();
            }
            if (hasMinionFireEffect(stack)){
                wolf.setFireHit();
            }
            if (hasMinionSlownessEffect(stack)){
                wolf.setSlownessHit();
            }
            if (hasMinionWeaknessEffect(stack)){
                wolf.setWeaknessHit();
            }
            if (hasMinionThornEffect(stack)){
                wolf.setThorn();
            }
            if (hasMinionSupremeEffect(stack)){
                wolf.setSupreme();
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                    || hasMinionSupremeEffect(stack)){
                double bonusAttack = SummonerClassConfig.minion_damage.get();
                if (hasAdvanceArmorSet((PlayerEntity) user)){
                    bonusAttack = bonusAttack + 0.5D;
                }
                wolf = (SummonedWolfEntity) addBonusAttack(wolf, wolf.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * bonusAttack);
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_HEALTH.get(), 3)
                    || hasMinionSupremeEffect(stack)){
                wolf = (SummonedWolfEntity) addBonusHealth(wolf, wolf.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerClassConfig.minion_health.get());
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                    || hasMinionSupremeEffect(stack)){
                wolf.setExplode();
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                    || hasMinionSupremeEffect(stack)){
                wolf.setBuffMaster();
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_SPEED.get(), 1)
                    || hasMinionSupremeEffect(stack) || hasFullArmorSet((PlayerEntity) user)){
                wolf = (SummonedWolfEntity) addBonusSpeed(wolf, wolf.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * SummonerClassConfig.minion_speed.get());
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                    || hasMinionSupremeEffect(stack)){
                wolf = (SummonedWolfEntity) addBonusKnockbackRes(wolf, SummonerClassConfig.minion_resistance.get());
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                    || hasMinionSupremeEffect(stack)){
                wolf.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerClassConfig.minion_invisible_duration.get()));
            }
            if (hasMinionArmorEffect((PlayerEntity) user,SummonerClassEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                    || hasMinionSupremeEffect(stack)){
                wolf.setMagnetize();
            }
            if (roll.nextInt(100) < 3){
                wolf.setEasterEgg();
            }
            user.level.addFreshEntity(wolf);
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
            if (!(stack.getItem() instanceof WolfArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof WolfArmor){
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
            if (!(stack.getItem() instanceof AdvancedWolfArmor)){
                return false;
            }
        }
        return true;
    }
}
