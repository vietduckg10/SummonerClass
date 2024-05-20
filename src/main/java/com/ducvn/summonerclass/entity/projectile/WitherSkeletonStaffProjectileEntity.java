package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedWitherSkeletonEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedWitherSkeletonArmor;
import com.ducvn.summonerclass.item.armor.basic.WitherSkeletonArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.Random;

public class WitherSkeletonStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public WitherSkeletonStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public WitherSkeletonStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.WITHER_SKELETON_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public WitherSkeletonStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.WITHER_SKELETON_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.WITHER_SKELETON_SKULL.asItem();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    protected void onHitEntity(EntityRayTraceResult traceResult) {
        if (!level.isClientSide){
            if (traceResult.getEntity() instanceof LivingEntity && this.getOwner() != null
                    && this.getOwner() instanceof PlayerEntity){
                LivingEntity livingEntity = (LivingEntity) traceResult.getEntity();
                DamageSource damageSource = DamageSource.playerAttack((PlayerEntity) this.getOwner());
                livingEntity.hurt(damageSource, 1.0F);
                Random roll = new Random();
                int skeletonNumber = 2 + bonusMinion;
                for (int num = 1; num <= skeletonNumber; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedWitherSkeletonEntity witherSkeleton = new SummonedWitherSkeletonEntity(SummonerClassEntitiesRegister.SUMMONED_WITHER_SKELETON.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        witherSkeleton.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        witherSkeleton.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    witherSkeleton.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    witherSkeleton.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s wither skeleton"));
                    giveMinionEnchantment(witherSkeleton);
                    if (hasFullArmorSet((PlayerEntity) this.getOwner())){
                        witherSkeleton.setInstantKill(1);
                        if (hasAdvanceArmorSet((PlayerEntity) this.getOwner())){
                            witherSkeleton.setInstantKill(2);
                        }
                    }
                    if (roll.nextInt(100) < 3){
                        if (roll.nextBoolean()){
                            witherSkeleton.setItemSlot(
                                    EquipmentSlotType.MAINHAND,
                                    Items.STICK.getDefaultInstance());
                        }
                        else {
                            witherSkeleton.setItemSlot(
                                    EquipmentSlotType.MAINHAND,
                                    Items.DEAD_BUSH.getDefaultInstance());
                        }
                    }
                    else {
                        witherSkeleton.setItemSlot(
                                EquipmentSlotType.MAINHAND,
                                Items.STONE_AXE.getDefaultInstance());
                    }
                    witherSkeleton.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                    level.addFreshEntity(witherSkeleton);
                }
            }
            this.remove();
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
            if (!(stack.getItem() instanceof WitherSkeletonArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof WitherSkeletonArmor){
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
            if (!(stack.getItem() instanceof AdvancedWitherSkeletonArmor)){
                return false;
            }
        }
        return true;
    }
}
