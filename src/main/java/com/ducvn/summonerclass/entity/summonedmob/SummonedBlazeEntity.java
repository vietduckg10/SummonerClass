package com.ducvn.summonerclass.entity.summonedmob;

import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.projectile.SmallEffectFireballEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedBlazeArmor;
import com.ducvn.summonerclass.item.armor.basic.BlazeArmor;
import com.ducvn.summonerclass.utils.SummonerClassUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class SummonedBlazeEntity extends BlazeEntity implements ISummonedEntity {
    private static final DataParameter<Boolean> DATA_EASTER_EGG_ID = EntityDataManager.defineId(SummonedBlazeEntity.class, DataSerializers.BOOLEAN);
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

    public SummonedBlazeEntity(EntityType<? extends SummonedBlazeEntity> p_i225714_1_, World p_i225714_2_) {
        super(p_i225714_1_, p_i225714_2_);
    }

    private static List<Integer> combatEffectId = new ArrayList<>(
            Arrays.asList(
                    1, 3, 5, 8, 10, 11, 12, 14, 21, 22
            )
    );

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new SummonedBlazeEntity.FireballAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 0.0F));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FLYING_SPEED, (double)0.6F)
                .add(Attributes.MOVEMENT_SPEED, (double)0.23F)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE);
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
        if (player.getScoreboardName().equals(this.getCustomName().getString().replace("'s blaze",""))){
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
    public UUID getMaster() {
        return master;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (target instanceof LivingEntity){
            if (witherHit){
                ((LivingEntity) target).addEffect(new EffectInstance(
                        Effects.WITHER,
                        SummonerClassConfig.minion_wither_duration.get(),
                        SummonerClassConfig.minion_wither_amplifier.get()));
            }
            if (poisonHit){
                ((LivingEntity) target).addEffect(new EffectInstance(
                        Effects.POISON,
                        SummonerClassConfig.minion_poison_duration.get(),
                        SummonerClassConfig.minion_poison_amplifier.get()));
            }
            if (fireHit){
                target.setSecondsOnFire(SummonerClassConfig.minion_fire_duration.get());
            }
            if (slownessHit){
                ((LivingEntity) target).addEffect(new EffectInstance(
                        Effects.MOVEMENT_SLOWDOWN,
                        SummonerClassConfig.minion_slowness_duration.get(),
                        SummonerClassConfig.minion_slowness_amplifier.get()));
            }
            if (weaknessHit){
                ((LivingEntity) target).addEffect(new EffectInstance(
                        Effects.WEAKNESS,
                        SummonerClassConfig.minion_weakness_duration.get(),
                        SummonerClassConfig.minion_weakness_amplifier.get()));
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
            source.getEntity().hurt(DamageSource.thorns(null), SummonerClassConfig.minion_thorn_damage.get().floatValue());
        }
        if (!level.isClientSide && canBuff){
            Random roll = new Random();
            if (roll.nextFloat() < SummonerClassConfig.minion_buff_chance.get() && ((ServerWorld) level).getEntity(master) != null) {
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
                    SummonerClassConfig.minion_explode_range.get().floatValue(), false, Explosion.Mode.NONE);
        }
        super.die(p_70645_1_);
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

    private boolean hasFullArmorSet(PlayerEntity player) {
        List<ItemStack> armorList = player.inventory.armor;
        boolean isCombined = true;
        boolean haveAtLeastOne = false;

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof ArmorItem)){
                return false;
            }
            if (!(stack.getItem() instanceof BlazeArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof BlazeArmor){
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
        List<ItemStack> armorList = player.inventory.armor;

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof AdvancedBlazeArmor)){
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
                                        -SummonerClassConfig.minion_magnetic_range.get(),
                                        0,
                                        -SummonerClassConfig.minion_magnetic_range.get()),
                                this.blockPosition().offset(
                                        SummonerClassConfig.minion_magnetic_range.get(),
                                        SummonerClassConfig.minion_magnetic_range.get(),
                                        SummonerClassConfig.minion_magnetic_range.get()));
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
    static class FireballAttackGoal extends Goal {
        private final SummonedBlazeEntity blaze;
        private int attackStep;
        private int attackTime;
        private int lastSeen;

        public FireballAttackGoal(SummonedBlazeEntity p_i45846_1_) {
            this.blaze = p_i45846_1_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.blaze.getTarget();
            return livingentity != null && livingentity.isAlive() && this.blaze.canAttack(livingentity);
        }

        public void start() {
            this.attackStep = 0;
        }

        public void stop() {
            this.blaze.setCharged(false);
            this.lastSeen = 0;
        }

        public void tick() {
            --this.attackTime;
            LivingEntity livingentity = this.blaze.getTarget();
            if (livingentity != null) {
                boolean flag = this.blaze.getSensing().canSee(livingentity);
                if (flag) {
                    this.lastSeen = 0;
                } else {
                    ++this.lastSeen;
                }

                double d0 = this.blaze.distanceToSqr(livingentity);
                if (d0 < 4.0D) {
                    if (!flag) {
                        return;
                    }

                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.blaze.doHurtTarget(livingentity);
                    }

                    this.blaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
                } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                    double d1 = livingentity.getX() - this.blaze.getX();
                    double d2 = livingentity.getY(0.5D) - this.blaze.getY(0.5D);
                    double d3 = livingentity.getZ() - this.blaze.getZ();
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                            this.blaze.setCharged(true);
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 100;
                            this.attackStep = 0;
                            this.blaze.setCharged(false);
                        }

                        if (this.attackStep > 1) {
                            float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                            if (!this.blaze.isSilent()) {
                                this.blaze.level.levelEvent((PlayerEntity)null, 1018, this.blaze.blockPosition(), 0);
                            }
                            Random roll = new Random();

                            for(int i = 0; i < 1; ++i) {
                                if (blaze.hasFullArmorSet(blaze.level.getPlayerByUUID(blaze.getMaster())) && blaze.getMaster() != null
                                        && roll.nextInt(10) < (blaze.hasAdvanceArmorSet(blaze.level.getPlayerByUUID(blaze.getMaster())) ? 5 : 3)){
                                    SmallEffectFireballEntity smallEffectFireballEntity = new SmallEffectFireballEntity(blaze.level, blaze, d1 + this.blaze.getRandom().nextGaussian() * (double)f, d2, d3 + this.blaze.getRandom().nextGaussian() * (double)f);
                                    smallEffectFireballEntity.setPos(smallEffectFireballEntity.getX(), this.blaze.getY(0.5D) + 0.5D, smallEffectFireballEntity.getZ());
                                    smallEffectFireballEntity.setMaster(blaze.master);
                                    if (blaze.witherHit){
                                        smallEffectFireballEntity.setWitherHit();
                                    }
                                    if (blaze.poisonHit){
                                        smallEffectFireballEntity.setPoisonHit();
                                    }
                                    if (blaze.weaknessHit){
                                        smallEffectFireballEntity.setWeaknessHit();
                                    }
                                    if (blaze.slownessHit){
                                        smallEffectFireballEntity.setSlownessHit();
                                    }
                                    this.blaze.level.addFreshEntity(smallEffectFireballEntity);
                                }
                                else {
                                    if (blaze.hasAdvanceArmorSet(blaze.level.getPlayerByUUID(blaze.getMaster()))
                                            && roll.nextInt(10) < 2){
                                        DragonFireballEntity dragonFireball = new DragonFireballEntity(blaze.level,
                                                blaze, d1 + this.blaze.getRandom().nextGaussian() * (double)f, d2, d3 + this.blaze.getRandom().nextGaussian() * (double)f);
                                        dragonFireball.setPos(dragonFireball.getX(), this.blaze.getY(0.5D) + 0.5D, dragonFireball.getZ());
                                        this.blaze.level.addFreshEntity(dragonFireball);
                                    }
                                    else {
                                        SmallFireballEntity smallfireballentity = new SmallFireballEntity(this.blaze.level, this.blaze, d1 + this.blaze.getRandom().nextGaussian() * (double)f, d2, d3 + this.blaze.getRandom().nextGaussian() * (double)f);
                                        smallfireballentity.setPos(smallfireballentity.getX(), this.blaze.getY(0.5D) + 0.5D, smallfireballentity.getZ());
                                        this.blaze.level.addFreshEntity(smallfireballentity);
                                    }
                                }
                            }
                        }
                    }

                    this.blaze.getLookControl().setLookAt(livingentity, 10.0F, 10.0F);
                } else if (this.lastSeen < 5) {
                    this.blaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
                }

                super.tick();
            }
        }

        private double getFollowDistance() {
            return this.blaze.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }

    public void aiStep() {
        if (this.getEasterEgg()){
            if (!this.onGround && this.getDeltaMovement().y < 0.0D) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
            }

            if (this.level.isClientSide) {
                if (this.random.nextInt(24) == 0 && !this.isSilent()) {
                    this.level.playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.BLAZE_BURN, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
                }

                for(int i = 0; i < 2; ++i) {
                    this.level.addParticle(ParticleTypes.DRAGON_BREATH, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
                }
            }
        }
        super.aiStep();
    }
}
