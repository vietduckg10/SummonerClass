package com.ducvn.summonerclass.entity.summonedmob;

import com.ducvn.summonercoremod.config.SummonerCoreConfig;
import com.ducvn.summonercoremod.entity.summonedmob.ISummonedEntity;
import com.ducvn.summonercoremod.utils.SummonerClassUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

import java.util.*;

public class SummonedPiglinEntity extends PiglinEntity implements ISummonedEntity {
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

    public SummonedPiglinEntity(EntityType<? extends SummonedPiglinEntity> p_i225714_1_, World p_i225714_2_) {
        super(p_i225714_1_, p_i225714_2_);
    }

    private static List<Integer> combatEffectId = new ArrayList<>(
            Arrays.asList(
                    1, 3, 5, 8, 10, 11, 12, 14, 21, 22
            )
    );

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.35F)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
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
        if (player.getScoreboardName().equals(this.getCustomName().getString().replace("'s piglin",""))){
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
        this.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        this.getBrain().setMemory(MemoryModuleType.ANGRY_AT, target);
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
    public boolean isFlyingEntity() {
        return false;
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
    public boolean wantsToPickUp(ItemStack p_230293_1_) {
        return false;
    }

    @Override
    protected boolean isImmuneToZombification() {
        return true;
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
