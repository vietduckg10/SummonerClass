package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedWitchEntity;
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

public class WitchStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public WitchStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public WitchStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.WITCH_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public WitchStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.WITCH_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.GLASS_BOTTLE.asItem();
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
                int witchNumber = 1 + bonusMinion;
                for (int num = 1; num <= witchNumber; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedWitchEntity witch = new SummonedWitchEntity(SummonerClassEntitiesRegister.SUMMONED_WITCH.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        witch.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        witch.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    witch.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    witch.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s witch"));
                    giveMinionEnchantment(witch);
                    if (roll.nextInt(100) < 3){
                        witch.setEasterEgg();
                    }
                    level.addFreshEntity(witch);
                }
            }
            this.remove();
        }
    }
}
