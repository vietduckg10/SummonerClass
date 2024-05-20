package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.projectile.SpiderStaffProjectileEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedCreeperEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedSpiderEntity;
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

public class SpiderStaffItem extends StaffItem implements IVanishable, IStaffItem {
    public SpiderStaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aSummon 3 spiders when hit enemy, right click to shoot projectile\u00A7r"
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
                SpiderStaffProjectileEntity spiderStaffProjectile = new SpiderStaffProjectileEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                spiderStaffProjectile.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                spiderStaffProjectile.setUpProjectile(spiderStaffProjectile, this, player, hand);
                spiderStaffProjectile.setBonusMinion(getBonusMinion(player));
                spiderStaffProjectile.setBonusMinion(getBonusMinion(player));
                world.addFreshEntity(spiderStaffProjectile);
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
        int spiderNumber = 3 + getBonusMinion((PlayerEntity) user);
        for (int num = 1; num <= spiderNumber; num++) {
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedSpiderEntity spider = new SummonedSpiderEntity(SummonerClassEntitiesRegister.SUMMONED_SPIDER.get(), user.level);
            if (xAdjust == 0 && zAdjust == 0) {
                spider.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            } else {
                spider.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            spider.setAttackTarget(entity, user);
            spider.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s spider"));
            giveMinionEnchantment(spider, stack, (PlayerEntity) user);
            user.level.addFreshEntity(spider);
            if (roll.nextInt(100) < 3){
                SummonedCreeperEntity creeper = new SummonedCreeperEntity(SummonerClassEntitiesRegister.SUMMONED_CREEPER.get(), user.level);
                creeper.setAttackTarget(entity, user);
                creeper.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s creeper"));
                giveMinionEnchantment(creeper, stack, (PlayerEntity) user);
                user.level.addFreshEntity(creeper);
                creeper.startRiding(spider);
            }
        }
    }
}
