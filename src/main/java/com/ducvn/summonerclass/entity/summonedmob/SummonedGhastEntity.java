package com.ducvn.summonerclass.entity.summonedmob;

import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.projectile.EffectFireballEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedGhastArmor;
import com.ducvn.summonerclass.item.armor.basic.GhastArmor;
import com.ducvn.summonerclass.utils.SummonerClassUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ArmorItem;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class SummonedGhastEntity extends GhastEntity implements ISummonedEntity {
    private static final DataParameter<Boolean> DATA_EASTER_EGG_ID = EntityDataManager.defineId(SummonedGhastEntity.class, DataSerializers.BOOLEAN);
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

    public SummonedGhastEntity(EntityType<? extends GhastEntity> p_i50206_1_, World p_i50206_2_) {
        super(p_i50206_1_, p_i50206_2_);
    }

    private static List<Integer> combatEffectId = new ArrayList<>(
            Arrays.asList(
                    1, 3, 5, 8, 10, 11, 12, 14, 21, 22
            )
    );

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new SummonedGhastEntity.RandomFlyGoal(this));
        this.goalSelector.addGoal(7, new SummonedGhastEntity.LookAroundGoal(this));
        this.goalSelector.addGoal(7, new SummonedGhastEntity.FireballAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_213812_1_) -> {
            return Math.abs(p_213812_1_.getY() - this.getY()) <= 4.0D;
        }));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FLYING_SPEED, (double)0.7F)
                .add(Attributes.MOVEMENT_SPEED, (double)0.7F)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 100.0D)
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
        if (player.getScoreboardName().equals(this.getCustomName().getString().replace("'s ghast",""))){
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
    public void setEasterEgg(){
        this.entityData.set(DATA_EASTER_EGG_ID, true);
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
            if (!(stack.getItem() instanceof GhastArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof GhastArmor){
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
            if (!(stack.getItem() instanceof AdvancedGhastArmor)){
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
                    if (hasAdvanceArmorSet(level.getPlayerByUUID(master))){
                        this.addEffect(new EffectInstance(Effects.INVISIBILITY, 100));
                    }
                    else {
                        if (this.hasEffect(Effects.INVISIBILITY)){
                            this.removeEffect(Effects.INVISIBILITY);
                        }
                    }
                }
            }
        }
        super.tick();
    }

    static class FireballAttackGoal extends Goal {
        private final SummonedGhastEntity ghast;
        public int chargeTime;

        public FireballAttackGoal(SummonedGhastEntity p_i45837_1_) {
            this.ghast = p_i45837_1_;
        }

        public boolean canUse() {
            return this.ghast.getTarget() != null;
        }

        public void start() {
            this.chargeTime = 0;
        }

        public void stop() {
            this.ghast.setCharging(false);
        }

        public void tick() {
            LivingEntity livingentity = this.ghast.getTarget();
            double d0 = 64.0D;
            if (livingentity.distanceToSqr(this.ghast) < 4096.0D && this.ghast.canSee(livingentity)) {
                World world = this.ghast.level;
                ++this.chargeTime;
                if (this.chargeTime == 10 && !this.ghast.isSilent()) {
                    world.levelEvent((PlayerEntity)null, 1015, this.ghast.blockPosition(), 0);
                }

                if (this.chargeTime == 20) {
                    double d1 = 4.0D;
                    Vector3d vector3d = this.ghast.getViewVector(1.0F);
                    double d2 = livingentity.getX() - (this.ghast.getX() + vector3d.x * 4.0D);
                    double d3 = livingentity.getY(0.5D) - (0.5D + this.ghast.getY(0.5D));
                    double d4 = livingentity.getZ() - (this.ghast.getZ() + vector3d.z * 4.0D);
                    if (!this.ghast.isSilent()) {
                        world.levelEvent((PlayerEntity)null, 1016, this.ghast.blockPosition(), 0);
                    }
                    if (ghast.hasFullArmorSet(ghast.level.getPlayerByUUID(ghast.getMaster())) && ghast.getMaster() != null){
                        EffectFireballEntity effectfireballentity = new EffectFireballEntity(world, this.ghast, d2, d3, d4);
                        effectfireballentity.explosionPower = this.ghast.getExplosionPower();
                        effectfireballentity.setPos(this.ghast.getX() + vector3d.x * 4.0D, this.ghast.getY(0.5D) + 0.5D, effectfireballentity.getZ() + vector3d.z * 4.0D);
                        effectfireballentity.setMaster(ghast.master);
                        if (ghast.witherHit){
                            effectfireballentity.setWitherHit();
                        }
                        if (ghast.poisonHit){
                            effectfireballentity.setPoisonHit();
                        }
                        if (ghast.weaknessHit){
                            effectfireballentity.setWeaknessHit();
                        }
                        if (ghast.slownessHit){
                            effectfireballentity.setSlownessHit();
                        }
                        world.addFreshEntity(effectfireballentity);
                    }
                    else {
                        FireballEntity fireballentity = new FireballEntity(world, this.ghast, d2, d3, d4);
                        fireballentity.explosionPower = this.ghast.getExplosionPower();
                        fireballentity.setPos(this.ghast.getX() + vector3d.x * 4.0D, this.ghast.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z * 4.0D);
                        world.addFreshEntity(fireballentity);
                    }
                    this.chargeTime = -40;
                }
            } else if (this.chargeTime > 0) {
                --this.chargeTime;
            }

            this.ghast.setCharging(this.chargeTime > 10);
        }
    }

    static class LookAroundGoal extends Goal {
        private final SummonedGhastEntity ghast;

        public LookAroundGoal(SummonedGhastEntity p_i45839_1_) {
            this.ghast = p_i45839_1_;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return true;
        }

        public void tick() {
            if (this.ghast.getTarget() == null) {
                Vector3d vector3d = this.ghast.getDeltaMovement();
                this.ghast.yRot = -((float) MathHelper.atan2(vector3d.x, vector3d.z)) * (180F / (float)Math.PI);
                this.ghast.yBodyRot = this.ghast.yRot;
            } else {
                LivingEntity livingentity = this.ghast.getTarget();
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.ghast) < 4096.0D) {
                    double d1 = livingentity.getX() - this.ghast.getX();
                    double d2 = livingentity.getZ() - this.ghast.getZ();
                    this.ghast.yRot = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
                    this.ghast.yBodyRot = this.ghast.yRot;
                }
            }

        }
    }
    
    static class RandomFlyGoal extends Goal {
        private final SummonedGhastEntity ghast;

        public RandomFlyGoal(SummonedGhastEntity p_i45836_1_) {
            this.ghast = p_i45836_1_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            MovementController movementcontroller = this.ghast.getMoveControl();
            if (!movementcontroller.hasWanted()) {
                return true;
            } else {
                double d0 = movementcontroller.getWantedX() - this.ghast.getX();
                double d1 = movementcontroller.getWantedY() - this.ghast.getY();
                double d2 = movementcontroller.getWantedZ() - this.ghast.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            Random random = this.ghast.getRandom();
            double d0 = this.ghast.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.ghast.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.ghast.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.ghast.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
        }
    }

}
