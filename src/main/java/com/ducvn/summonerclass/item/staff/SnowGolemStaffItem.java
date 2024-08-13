package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.projectile.SnowGolemStaffProjectileEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedSnowGolemEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedSnowGolemArmor;
import com.ducvn.summonerclass.item.armor.basic.SnowGolemArmor;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import com.ducvn.summonercoremod.item.staff.IStaffItem;
import com.ducvn.summonercoremod.item.staff.StaffItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
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

public class SnowGolemStaffItem extends StaffItem implements IVanishable, IStaffItem {
    public SnowGolemStaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aSummon 1-3 snow golems when hit enemy, right click to shoot projectile\u00A7r"
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
                SnowGolemStaffProjectileEntity snowGolemStaffProjectile = new SnowGolemStaffProjectileEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                snowGolemStaffProjectile.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                snowGolemStaffProjectile.setUpProjectile(snowGolemStaffProjectile, this, player, hand);
                snowGolemStaffProjectile.setBonusMinion(getBonusMinion(player));
                world.addFreshEntity(snowGolemStaffProjectile);
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
        int snowGolemNumber = roll.nextInt(3) + 1 + getBonusMinion((PlayerEntity) user);
        if (hasAdvanceArmorSet((PlayerEntity) user)){
            snowGolemNumber++;
        }
        for (int num = 1; num <= snowGolemNumber; num++) {
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedSnowGolemEntity snowGolem = new SummonedSnowGolemEntity(SummonerClassEntitiesRegister.SUMMONED_SNOW_GOLEM.get(), user.level);
            if (xAdjust == 0 && zAdjust == 0) {
                snowGolem.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            } else {
                snowGolem.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            snowGolem.setAttackTarget(entity, user);
            snowGolem.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s snow golem"));
            giveMinionEnchantment(snowGolem, stack, (PlayerEntity) user);
            if (hasFullArmorSet((PlayerEntity) user)){
                snowGolem.setNeverMelt();
            }
            if (roll.nextInt(100) < 3){
                snowGolem.setPumpkin(false);
            }
            user.level.addFreshEntity(snowGolem);
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
            if (!(stack.getItem() instanceof SnowGolemArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof SnowGolemArmor){
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
            if (!(stack.getItem() instanceof AdvancedSnowGolemArmor)){
                return false;
            }
        }
        return true;
    }
}
