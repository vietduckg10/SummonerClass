package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedZombieEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedZombieArmor;
import com.ducvn.summonerclass.item.armor.basic.ZombieArmor;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import com.ducvn.summonercoremod.entity.projectile.IStaffProjectile;
import com.ducvn.summonercoremod.entity.projectile.StaffProjectileEntity;
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

public class ZombieStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    private boolean easterEgg = false;
    public ZombieStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public ZombieStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.ZOMBIE_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public ZombieStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.ZOMBIE_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.ROTTEN_FLESH.asItem();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    public void setEasterEgg() {
        this.easterEgg = true;
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
                int numZombie = 2 + bonusMinion;
                boolean summonBaby = false;
                if (hasFullArmorSet((PlayerEntity) this.getOwner())
                        && roll.nextInt(10) < (hasAdvanceArmorSet((PlayerEntity) this.getOwner()) ? 5 : 3)){
                    numZombie++;
                    summonBaby = true;
                }
                for (int num = 1; num <= numZombie; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedZombieEntity zombie = new SummonedZombieEntity(SummonerClassEntitiesRegister.SUMMONED_ZOMBIE.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        zombie.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        zombie.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    zombie.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    zombie.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s zombie"));
                    if (hasFullArmorSet((PlayerEntity) this.getOwner())){
                        zombie.setImmuneSunLight();
                    }
                    if (num == numZombie && summonBaby){
                        zombie.setBaby(true);
                    }
                    giveMinionEnchantment(zombie);
                    if (hasAdvanceArmorSet((PlayerEntity) this.getOwner())){
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
                    if (easterEgg){
                        zombie.setEasterEgg();
                    }
                    level.addFreshEntity(zombie);
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
            if (!(stack.getItem() instanceof ZombieArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof ZombieArmor){
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
            if (!(stack.getItem() instanceof AdvancedZombieArmor)){
                return false;
            }
        }
        return true;
    }
}
