package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.projectile.CreeperStaffProjectileEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedCreeperEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedCreeperArmor;
import com.ducvn.summonerclass.item.armor.basic.CreeperArmor;
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

public class CreeperStaffItem extends StaffItem implements IVanishable, IStaffItem {
    public CreeperStaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aSummon 3 creepers when hit enemy, right click to shoot projectile\u00A7r"
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
                CreeperStaffProjectileEntity creeperStaffProjectile = new CreeperStaffProjectileEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                creeperStaffProjectile.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                creeperStaffProjectile.setUpProjectile(creeperStaffProjectile, this, player, hand);
                creeperStaffProjectile.setBonusMinion(getBonusMinion(player));
                creeperStaffProjectile.setBonusMinion(getBonusMinion(player));
                world.addFreshEntity(creeperStaffProjectile);
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
        int creeperNumber = 3 + getBonusMinion((PlayerEntity) user);
        for (int num = 1; num <= creeperNumber; num++) {
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedCreeperEntity creeper = new SummonedCreeperEntity(SummonerClassEntitiesRegister.SUMMONED_CREEPER.get(), user.level);
            if (xAdjust == 0 && zAdjust == 0) {
                creeper.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            } else {
                creeper.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            creeper.setAttackTarget(entity, user);
            creeper.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s creeper"));
            if (hasFullArmorSet((PlayerEntity) user)
                    &&  roll.nextInt(10) < ((hasAdvanceArmorSet((PlayerEntity) user)) ? 5 : 3)){
                creeper.setCharged();
                if (roll.nextInt(100) < 5){
                    creeper.setEasterEgg();
                }
            }
            giveMinionEnchantment(creeper, stack, (PlayerEntity) user);
            user.level.addFreshEntity(creeper);
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
            if (!(stack.getItem() instanceof CreeperArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof CreeperArmor){
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
            if (!(stack.getItem() instanceof AdvancedCreeperArmor)){
                return false;
            }
        }
        return true;
    }
}
