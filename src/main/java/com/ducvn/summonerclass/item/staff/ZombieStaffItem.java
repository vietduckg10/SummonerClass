package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.projectile.ZombieStaffProjectileEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedZombieEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedZombieArmor;
import com.ducvn.summonerclass.item.armor.basic.ZombieArmor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class ZombieStaffItem extends StaffItem implements IVanishable, IStaffItem {
    public ZombieStaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aSummon 2 zombies when hit enemy, right click to shoot projectile\u00A7r"
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
                ZombieStaffProjectileEntity zombieStaffProjectile = new ZombieStaffProjectileEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                zombieStaffProjectile.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                zombieStaffProjectile.setUpProjectile(zombieStaffProjectile, this, player, hand);
                zombieStaffProjectile.setBonusMinion(getBonusMinion(player));
                zombieStaffProjectile.setBonusMinion(getBonusMinion(player));
                if (player.getItemInHand(hand).getHoverName().getString().toLowerCase().equals("matty's awesome staff")
                || player.getItemInHand(hand).getHoverName().getString().toLowerCase().equals("matty")) {
                    zombieStaffProjectile.setEasterEgg();
                }
                world.addFreshEntity(zombieStaffProjectile);
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
        int numZombie = 2 + getBonusMinion((PlayerEntity) user);
        boolean summonBaby = false;
        if (hasFullArmorSet((PlayerEntity) user)
                && roll.nextInt(10) < (hasAdvanceArmorSet((PlayerEntity) user) ? 5 : 3)){
            numZombie++;
            summonBaby = true;
        }
        for (int num = 1; num <= numZombie; num++) {
            int xAdjust = roll.nextInt(3) - 1;
            int yAdjust = roll.nextInt(3);
            int zAdjust = roll.nextInt(3) - 1;
            SummonedZombieEntity zombie = new SummonedZombieEntity(SummonerClassEntitiesRegister.SUMMONED_ZOMBIE.get(), user.level);
            if (xAdjust == 0 && zAdjust == 0) {
                zombie.setPos(user.position().x,
                        user.position().y + 3D,
                        user.position().z);
            } else {
                zombie.setPos(user.position().x + xAdjust,
                        user.position().y + yAdjust,
                        user.position().z + zAdjust);
            }
            zombie.setAttackTarget(entity, user);
            zombie.setCustomName(new StringTextComponent(user.getScoreboardName() + "'s zombie"));
            if (hasFullArmorSet((PlayerEntity) user)){
                zombie.setImmuneSunLight();
            }
            if (num == numZombie && summonBaby){
                zombie.setBaby(true);
            }
            giveMinionEnchantment(zombie, stack, (PlayerEntity) user);
            if (hasAdvanceArmorSet((PlayerEntity) user)){
                zombie.setItemSlot(
                        EquipmentSlotType.CHEST,
                        Items.CHAINMAIL_CHESTPLATE.getDefaultInstance());
                zombie.setItemSlot(
                        EquipmentSlotType.LEGS,
                        Items.CHAINMAIL_LEGGINGS.getDefaultInstance());
                zombie.setItemSlot(
                        EquipmentSlotType.FEET,
                        Items.CHAINMAIL_BOOTS.getDefaultInstance());
                zombie.setItemSlot(
                        EquipmentSlotType.MAINHAND,
                        Items.IRON_AXE.getDefaultInstance());
                zombie.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                zombie.setDropChance(EquipmentSlotType.CHEST, 0.0f);
                zombie.setDropChance(EquipmentSlotType.LEGS, 0.0f);
                zombie.setDropChance(EquipmentSlotType.FEET, 0.0f);
            }
            if (stack.getHoverName().getString().toLowerCase().equals("matty's awesome staff")
                    || stack.getHoverName().getString().toLowerCase().equals("matty")){
                zombie.setEasterEgg();
            }
            user.level.addFreshEntity(zombie);
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
            if (!(stack.getItem() instanceof ZombieArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof ZombieArmor){
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
            if (!(stack.getItem() instanceof AdvancedZombieArmor)){
                return false;
            }
        }
        return true;
    }
}
