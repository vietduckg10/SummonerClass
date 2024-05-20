package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedCreeperEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedSpiderEntity;
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

public class SpiderStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public SpiderStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public SpiderStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.SPIDER_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public SpiderStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.SPIDER_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SPIDER_EYE.asItem();
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
                int spiderNumber = 3 + bonusMinion;
                for (int num = 1; num <= spiderNumber; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedSpiderEntity spider = new SummonedSpiderEntity(SummonerClassEntitiesRegister.SUMMONED_SPIDER.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        spider.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        spider.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    spider.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    spider.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s spider"));
                    giveMinionEnchantment(spider);
                    level.addFreshEntity(spider);
                    if (roll.nextInt(100) < 3){
                        SummonedCreeperEntity creeper = new SummonedCreeperEntity(SummonerClassEntitiesRegister.SUMMONED_CREEPER.get(), level);
                        creeper.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                        creeper.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s creeper"));
                        giveMinionEnchantment(creeper);
                        level.addFreshEntity(creeper);
                        creeper.startRiding(spider);
                    }
                }
            }
            this.remove();
        }
    }
}
