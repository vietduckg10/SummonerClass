package com.ducvn.summonerclass.entity.summonedmob;

import com.ducvn.summonerclass.item.armor.advanced.AdvancedCreeperArmor;
import com.ducvn.summonercoremod.config.SummonerCoreConfig;
import com.ducvn.summonercoremod.entity.summonedmob.ISummonedEntity;
import com.ducvn.summonercoremod.utils.SummonerClassUtils;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class SummonedCreeperEntity extends CreeperEntity implements ISummonedEntity {
    private static final DataParameter<Boolean> DATA_EASTER_EGG_ID = EntityDataManager.defineId(SummonedCreeperEntity.class, DataSerializers.BOOLEAN);
    private UUID target;
    private UUID master;
    private boolean witherHit = false;
    private boolean poisonHit = false;
    private boolean fireHit = false;
    private boolean slownessHit = false;
    private boolean weaknessHit = false;
    private boolean hasThorn = false;
    private boolean canExplode = false;
    private boolean canBuff = false;
    private boolean isMagnetize = false;
    private boolean isSupreme = false;
    private int explosionRadius = 3;

    public SummonedCreeperEntity(EntityType<? extends SummonedCreeperEntity> p_i225714_1_, World p_i225714_2_) {
        super(p_i225714_1_, p_i225714_2_);
    }

    private static List<Integer> combatEffectId = new ArrayList<>(
            Arrays.asList(
                    1, 3, 5, 8, 10, 11, 12, 14, 21, 22
            )
    );

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new CreeperSwellGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE);
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
    }

    @Override
    protected boolean shouldDropExperience() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isInvisibleTo(PlayerEntity player) {
        if (player.getScoreboardName().equals(this.getCustomName().getString().replace("'s creeper",""))){
            return false;
        }
        else {
            return super.isInvisibleTo(player);
        }
    }

    public void setAttackTarget(LivingEntity entity, LivingEntity inputMaster){
        this.setTarget(entity);
        target = entity.getUUID();
        master = inputMaster.getUUID();
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putBoolean("EasterEgg", this.entityData.get(DATA_EASTER_EGG_ID));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        this.entityData.set(DATA_EASTER_EGG_ID, p_70037_1_.getBoolean("EasterEgg"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_EASTER_EGG_ID, false);
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public Boolean getEasterEgg() {
        return this.entityData.get(DATA_EASTER_EGG_ID);
    }

    public void setEasterEgg(){
        this.entityData.set(DATA_EASTER_EGG_ID, true);
    }
    public void setWitherHit(){
        witherHit = true;
    }
    public void setPoisonHit(){
        poisonHit = true;
    }
    public void setFireHit(){
        fireHit = true;
    }
    public void setSlownessHit(){
        slownessHit = true;
    }
    public void setWeaknessHit(){
        weaknessHit = true;
    }
    public void setThorn(){
        hasThorn = true;
    }
    public void setExplode(){
        canExplode = true;
    }
    public void setBuffMaster(){
        canBuff = true;
    }
    public void setMagnetize(){
        isMagnetize = true;
    }
    public void setSupreme(){
        isSupreme = true;
    }

    @Override
    public boolean isFlyingEntity() {
        return false;
    }

    public void setCharged(){
        this.entityData.set(DATA_IS_POWERED, true);
    }
    public UUID getMaster() {
        return master;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (target instanceof LivingEntity){
            if (witherHit){
                ((LivingEntity) target).addEffect(new EffectInstance(
                        Effects.WITHER,
                        SummonerCoreConfig.minion_wither_duration.get(),
                        SummonerCoreConfig.minion_wither_amplifier.get()));
            }
            if (poisonHit){
                ((LivingEntity) target).addEffect(new EffectInstance(
                        Effects.POISON,
                        SummonerCoreConfig.minion_poison_duration.get(),
                        SummonerCoreConfig.minion_poison_amplifier.get()));
            }
            if (fireHit){
                target.setSecondsOnFire(SummonerCoreConfig.minion_fire_duration.get());
            }
            if (slownessHit){
                ((LivingEntity) target).addEffect(new EffectInstance(
                        Effects.MOVEMENT_SLOWDOWN,
                        SummonerCoreConfig.minion_slowness_duration.get(),
                        SummonerCoreConfig.minion_slowness_amplifier.get()));
            }
            if (weaknessHit){
                ((LivingEntity) target).addEffect(new EffectInstance(
                        Effects.WEAKNESS,
                        SummonerCoreConfig.minion_weakness_duration.get(),
                        SummonerCoreConfig.minion_weakness_amplifier.get()));
            }
        }
        return super.doHurtTarget(target);
    }

    @Override
    public boolean hurt(DamageSource source, float p_70097_2_) {
        if (source.getEntity() != null){
            if (source.getEntity().getUUID() == master){
                this.kill();
            }
        }
        if (source.getEntity() instanceof LivingEntity && hasThorn){
            source.getEntity().hurt(DamageSource.thorns(null), SummonerCoreConfig.minion_thorn_damage.get().floatValue());
        }
        if (!level.isClientSide && canBuff){
            Random roll = new Random();
            if (roll.nextFloat() < SummonerCoreConfig.minion_buff_chance.get() && ((ServerWorld) level).getEntity(master) != null) {
                int effectId = combatEffectId.get(roll.nextInt(combatEffectId.size()));
                ((LivingEntity) ((ServerWorld) level).getEntity(master)).addEffect(new EffectInstance(
                        Effect.byId(effectId),
                        300));
            }
        }
        return super.hurt(source, p_70097_2_);
    }

    @Override
    public void die(DamageSource p_70645_1_) {
        if (!level.isClientSide && canExplode){
            level.explode(null, DamageSource.GENERIC, null,
                    this.position().x, this.position().y, this.position().z,
                    SummonerCoreConfig.minion_explode_range.get().floatValue(), false, Explosion.Mode.NONE);
        }
        super.die(p_70645_1_);
    }

    @Override
    public void explodeCreeper() {
        if (!this.level.isClientSide) {
            Explosion.Mode explosion$mode = Explosion.Mode.NONE;
            float f = this.isPowered() ? 2.0F : 1.0F;
            this.dead = true;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, explosion$mode);
            this.remove();
            if (hasAdvanceArmorSet((PlayerEntity) ((ServerWorld) level).getEntity(master))){
                AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
                areaeffectcloudentity.setRadius(2.5F);
                areaeffectcloudentity.setRadiusOnUse(-0.5F);
                areaeffectcloudentity.setWaitTime(10);
                areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
                areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());

                if (witherHit){
                    areaeffectcloudentity.addEffect(new EffectInstance(
                            Effects.WITHER,
                            SummonerCoreConfig.minion_wither_duration.get(),
                            SummonerCoreConfig.minion_wither_amplifier.get()));
                }
                if (poisonHit){
                    areaeffectcloudentity.addEffect(new EffectInstance(
                            Effects.POISON,
                            SummonerCoreConfig.minion_poison_duration.get(),
                            SummonerCoreConfig.minion_poison_amplifier.get()));
                }
                if (slownessHit){
                    areaeffectcloudentity.addEffect(new EffectInstance(
                            Effects.MOVEMENT_SLOWDOWN,
                            SummonerCoreConfig.minion_slowness_duration.get(),
                            SummonerCoreConfig.minion_slowness_amplifier.get()));
                }
                if (weaknessHit){
                    areaeffectcloudentity.addEffect(new EffectInstance(
                            Effects.WEAKNESS,
                            SummonerCoreConfig.minion_weakness_duration.get(),
                            SummonerCoreConfig.minion_weakness_amplifier.get()));
                }
                this.level.addFreshEntity(areaeffectcloudentity);
            }
        }
    }

    public boolean hasNoArmor(PlayerEntity player){
        List<ItemStack> armors = player.inventory.armor;
        for (ItemStack stack : armors){
            if (stack.getItem() != Items.AIR){
                return false;
            }
        }
        return true;
    }

    private boolean hasAdvanceArmorSet(PlayerEntity player){
        List<ItemStack> armorList = player.inventory.armor;

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof AdvancedCreeperArmor)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void tick() {
        if (!level.isClientSide){
            if (((ServerWorld) level).getEntity(target) == null
                    || level.getPlayerByUUID(master) == null) {
                this.remove();
            } else {
                if ((isSupreme && !hasNoArmor(level.getPlayerByUUID(master)))
                        || !((ServerWorld) level).getEntity(target).isAlive()) {
                    this.remove();
                }
                else {
                    if (((ServerWorld) level).getEntity(target) != this.getTarget()) {
                        this.setTarget((LivingEntity) ((ServerWorld) level).getEntity(target));
                    }
                    if (isMagnetize && tickCount % 10 == 0){
                        AxisAlignedBB aabb = new AxisAlignedBB(
                                this.blockPosition().offset(
                                        -SummonerCoreConfig.minion_magnetic_range.get(),
                                        0,
                                        -SummonerCoreConfig.minion_magnetic_range.get()),
                                this.blockPosition().offset(
                                        SummonerCoreConfig.minion_magnetic_range.get(),
                                        SummonerCoreConfig.minion_magnetic_range.get(),
                                        SummonerCoreConfig.minion_magnetic_range.get()));
                        List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, aabb);
                        for (LivingEntity entity : entityList){
                            boolean pull = false;
                            if (entity instanceof PlayerEntity){
                                if (entity.getUUID() != master){
                                    pull = true;
                                }
                            }
                            else {
                                if (entity instanceof ISummonedEntity){
                                    if (!SummonerClassUtils.isTheSameMaster((ISummonedEntity) entity, master)){
                                        pull = true;
                                    }
                                }
                                else {
                                    pull = true;
                                }
                            }
                            if (pull){
                                entity.setDeltaMovement(
                                        this.position()
                                                .subtract(entity.position())
                                                .multiply(0.2D, 0.2D, 0.2D)
                                );
                            }
                        }
                    }
                }
            }
        }
        super.tick();
    }

}
