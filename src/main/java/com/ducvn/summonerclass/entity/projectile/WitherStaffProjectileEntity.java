package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedWitherEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class WitherStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    private boolean easterEgg = false;
    public WitherStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public WitherStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.WITHER_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public WitherStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.WITHER_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.WITHER_SKELETON_SKULL.asItem();
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
                int xAdjust = roll.nextInt(3) - 1;
                int yAdjust = roll.nextInt(3);
                int zAdjust = roll.nextInt(3) - 1;
                SummonedWitherEntity wither = new SummonedWitherEntity(SummonerClassEntitiesRegister.SUMMONED_WITHER.get(), level);
                if (xAdjust == 0 && zAdjust == 0){
                    wither.setPos(this.getOwner().position().x,
                            this.getOwner().position().y + 3D,
                            this.getOwner().position().z);
                }
                else {
                    wither.setPos(this.getOwner().position().x + xAdjust,
                            this.getOwner().position().y + yAdjust,
                            this.getOwner().position().z + zAdjust);
                }
                wither.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                wither.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s wither"));
                giveMinionEnchantment(wither);
                if (easterEgg){
                    wither.setEasterEgg();
                }
                level.addFreshEntity(wither);
                this.getOwner().hurt(DamageSource.MAGIC, 20);
            }
            this.remove();
        }
    }
}
