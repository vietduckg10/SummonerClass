package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.projectile.IronGolemStaffProjectileEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedIronGolemEntity;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import com.ducvn.summonercoremod.item.staff.IStaffItem;
import com.ducvn.summonercoremod.item.staff.StaffItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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

public class IronGolemStaffItem extends StaffItem implements IVanishable, IStaffItem {
    public IronGolemStaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aSummon 1 iron golem when hit enemy, right click to shoot projectile\u00A7r"
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
                IronGolemStaffProjectileEntity ironGolemStaffProjectile = new IronGolemStaffProjectileEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                ironGolemStaffProjectile.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                ironGolemStaffProjectile.setUpProjectile(ironGolemStaffProjectile, this, player, hand);
                ironGolemStaffProjectile.setBonusMinion(getBonusMinion(player));
                ironGolemStaffProjectile.setBonusMinion(getBonusMinion(player));
                world.addFreshEntity(ironGolemStaffProjectile);
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
        int golemNumber = 1 + getBonusMinion((PlayerEntity) user);
        for (int num = 1; num <= golemNumber; num++) {
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedIronGolemEntity ironGolem = new SummonedIronGolemEntity(SummonerClassEntitiesRegister.SUMMONED_IRON_GOLEM.get(), user.level);
            if (xAdjust == 0 && zAdjust == 0) {
                ironGolem.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            } else {
                ironGolem.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            ironGolem.setAttackTarget(entity, user);
            ironGolem.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s iron golem"));
            giveMinionEnchantment(ironGolem, stack, (PlayerEntity) user);
            if (hasMinionArmorEffect((PlayerEntity) user, SummonerCoreEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                    || hasMinionSupremeEffect(stack)){
                ironGolem.setMagnetize();
            }
            if (roll.nextInt(100) < 3){
                ironGolem.setEasterEgg();
            }
            user.level.addFreshEntity(ironGolem);
        }
    }
}
