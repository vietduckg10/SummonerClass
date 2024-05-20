package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedPolarBearEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedSnowGolemEntity;
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

public class PolarBearStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public PolarBearStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public PolarBearStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.POLAR_BEAR_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public PolarBearStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.POLAR_BEAR_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.COD.asItem();
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
                int numPolarBear = roll.nextInt(2) + 1 + bonusMinion;
                for (int num = 1; num <= numPolarBear; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedPolarBearEntity polarbear = new SummonedPolarBearEntity(SummonerClassEntitiesRegister.SUMMONED_POLAR_BEAR.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        polarbear.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        polarbear.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    polarbear.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    polarbear.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s polar bear"));
                    giveMinionEnchantment(polarbear);
                    level.addFreshEntity(polarbear);
                    if (roll.nextInt(100) < 3){
                        SummonedSnowGolemEntity snowGolem = new SummonedSnowGolemEntity(SummonerClassEntitiesRegister.SUMMONED_SNOW_GOLEM.get(), level);
                        snowGolem.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                        snowGolem.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s snow golem"));
                        snowGolem.setNeverMelt();
                        giveMinionEnchantment(snowGolem);
                        level.addFreshEntity(snowGolem);
                        snowGolem.startRiding(polarbear);
                    }
                }
            }
            this.remove();
        }
    }
}
