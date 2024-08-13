package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedSnowGolemEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedSnowGolemArmor;
import com.ducvn.summonerclass.item.armor.basic.SnowGolemArmor;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import com.ducvn.summonercoremod.entity.projectile.IStaffProjectile;
import com.ducvn.summonercoremod.entity.projectile.StaffProjectileEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
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

public class SnowGolemStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public SnowGolemStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public SnowGolemStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.SNOW_GOLEM_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public SnowGolemStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.SNOW_GOLEM_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL.asItem();
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
                int snowGolemNumber = roll.nextInt(3) + 1 + bonusMinion;
                if (hasAdvanceArmorSet((PlayerEntity) this.getOwner())){
                    snowGolemNumber++;
                }
                for (int num = 1; num <= snowGolemNumber; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedSnowGolemEntity snowGolem = new SummonedSnowGolemEntity(SummonerClassEntitiesRegister.SUMMONED_SNOW_GOLEM.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        snowGolem.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        snowGolem.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    snowGolem.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    snowGolem.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s snow golem"));
                    giveMinionEnchantment(snowGolem);
                    if (hasFullArmorSet((PlayerEntity) this.getOwner())){
                        snowGolem.setNeverMelt();
                    }
                    if (roll.nextInt(100) < 3){
                        snowGolem.setPumpkin(false);
                    }
                    level.addFreshEntity(snowGolem);
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
