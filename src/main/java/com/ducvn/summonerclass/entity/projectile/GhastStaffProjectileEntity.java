package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedGhastEntity;
import com.ducvn.summonercoremod.entity.projectile.IStaffProjectile;
import com.ducvn.summonercoremod.entity.projectile.StaffProjectileEntity;
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

public class GhastStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public GhastStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public GhastStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.GHAST_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public GhastStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.GHAST_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.GHAST_TEAR.asItem();
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
                int ghastNumber = roll.nextInt(2) + 1 + bonusMinion;
                for (int num = 1; num <= ghastNumber; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedGhastEntity ghast = new SummonedGhastEntity(SummonerClassEntitiesRegister.SUMMONED_GHAST.get(), level);
                    if (roll.nextInt(100) < 3){
                        ghast.setEasterEgg();
                    }
                    if (xAdjust == 0 && zAdjust == 0){
                        ghast.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        ghast.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    ghast.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    ghast.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s ghast"));
                    giveMinionEnchantment(ghast);
                    level.addFreshEntity(ghast);
                }
            }
            this.remove();
        }
    }
}
