package com.ducvn.summonerclass.entity.summonedmob;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedSlimeArmor;
import com.ducvn.summonerclass.item.armor.basic.SlimeArmor;
import com.ducvn.summonercoremod.config.SummonerCoreConfig;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import com.ducvn.summonercoremod.entity.summonedmob.ISummonedEntity;
import com.ducvn.summonercoremod.utils.SummonerClassUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class SummonedSlimeEntity extends SlimeEntity implements ISummonedEntity {
    private static final DataParameter<Boolean> DATA_EASTER_EGG_ID = EntityDataManager.defineId(SummonedWitherEntity.class, DataSerializers.BOOLEAN);
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

    public SummonedSlimeEntity(EntityType<? extends SummonedSlimeEntity> p_i225714_1_, World p_i225714_2_) {
        super(p_i225714_1_, p_i225714_2_);
        this.setSize(2,true);
    }

    private static List<Integer> combatEffectId = new ArrayList<>(
            Arrays.asList(
                    1, 3, 5, 8, 10, 11, 12, 14, 21, 22
            )
    );

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH)
                .add(Attributes.MOVEMENT_SPEED)
                .add(Attributes.ATTACK_DAMAGE)
                .add(Attributes.FOLLOW_RANGE)
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
        if (player.getScoreboardName().equals(this.getCustomName().getString().replace("'s slime",""))){
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
    public void push(Entity entity) {
        super.push(entity);
        if (entity instanceof LivingEntity && this.isDealsDamage()){
            if (!(entity instanceof ISummonedEntity)){
                performAttack((LivingEntity) entity);
            }
            else {
                if (((ISummonedEntity) entity).getMaster() != master){
                    performAttack((LivingEntity) entity);
                }
            }
        }

    }

    @Override
    public void playerTouch(PlayerEntity player) {
        if (this.isDealsDamage() && player.getUUID() != master) {
            performAttack(player);
        }

    }

    private void performAttack(LivingEntity livingEntity){
        this.dealDamage(livingEntity);
        if (witherHit){
            livingEntity.addEffect(new EffectInstance(
                    Effects.WITHER,
                    SummonerCoreConfig.minion_wither_duration.get(),
                    SummonerCoreConfig.minion_wither_amplifier.get()));
        }
        if (poisonHit){
            livingEntity.addEffect(new EffectInstance(
                    Effects.POISON,
                    SummonerCoreConfig.minion_poison_duration.get(),
                    SummonerCoreConfig.minion_poison_amplifier.get()));
        }
        if (fireHit){
            livingEntity.setSecondsOnFire(SummonerCoreConfig.minion_fire_duration.get());
        }
        if (slownessHit){
            livingEntity.addEffect(new EffectInstance(
                    Effects.MOVEMENT_SLOWDOWN,
                    SummonerCoreConfig.minion_slowness_duration.get(),
                    SummonerCoreConfig.minion_slowness_amplifier.get()));
        }
        if (weaknessHit){
            livingEntity.addEffect(new EffectInstance(
                    Effects.WEAKNESS,
                    SummonerCoreConfig.minion_weakness_duration.get(),
                    SummonerCoreConfig.minion_weakness_amplifier.get()));
        }
    }

    @Override
    public boolean hurt(DamageSource source, float p_70097_2_) {
        if (source.getEntity() != null){
            if (source.getEntity().getUUID() == master){
                if (hasAdvanceArmorSet(level.getPlayerByUUID(master))){
                    this.remove();
                }
                else {
                    this.kill();
                }
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
        if (!level.isClientSide){
            if (canExplode){
                level.explode(null, DamageSource.GENERIC, null,
                        this.position().x, this.position().y, this.position().z,
                        SummonerCoreConfig.minion_explode_range.get().floatValue(), false, Explosion.Mode.NONE);
            }
            if (((ServerWorld) level).getEntity(target) != null && master != null){
                if (hasAdvanceArmorSet(level.getPlayerByUUID(master)) && ((ServerWorld) level).getEntity(target) != null
                && !this.isOnFire()){
                    SummonedSlimeEntity revivedSlime = new SummonedSlimeEntity(SummonerClassEntitiesRegister.SUMMONED_SLIME.get(), level);
                    revivedSlime.setPos(this.getX(), this.getY(), this.getZ());
                    revivedSlime.setAttackTarget((LivingEntity) ((ServerWorld) level).getEntity(target), level.getPlayerByUUID(master));
                    revivedSlime.setCustomName(new StringTextComponent(level.getPlayerByUUID(master).getScoreboardName() + "'s slime"));
                    if (this.witherHit){
                        revivedSlime.setWitherHit();
                    }
                    if (this.poisonHit){
                        revivedSlime.setPoisonHit();
                    }
                    if (this.fireHit){
                        revivedSlime.setFireHit();
                    }
                    if (this.slownessHit){
                        revivedSlime.setSlownessHit();
                    }
                    if (this.weaknessHit){
                        revivedSlime.setWeaknessHit();
                    }
                    if (this.hasThorn){
                        revivedSlime.setThorn();
                    }
                    if (this.isSupreme){
                        revivedSlime.setSupreme();
                    }
                    revivedSlime.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(this.getAttributeBaseValue(Attributes.KNOCKBACK_RESISTANCE));
                    revivedSlime.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
                    if (this.canExplode || this.isSupreme){
                        revivedSlime.setExplode();
                    }
                    if (this.canBuff || this.isSupreme){
                        revivedSlime.setBuffMaster();
                    }
                    if (this.hasEffect(Effects.INVISIBILITY) || this.isSupreme){
                        revivedSlime.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerCoreConfig.minion_invisible_duration.get()));
                    }
                    if (this.isMagnetize || this.isSupreme){
                        revivedSlime.setMagnetize();
                    }
                    revivedSlime.setSize(this.getSize(),true);
                    level.addFreshEntity(revivedSlime);
                }
            }
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
            if (!(stack.getItem() instanceof SlimeArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof SlimeArmor){
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
        List<ItemStack> armorList = player.inventory.armor;

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof AdvancedSlimeArmor)){
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
                    if (hasFullArmorSet(level.getPlayerByUUID(master)) && (tickCount % 100 == 0) && this.getSize() < 12){
                        this.setSize(this.getSize() + 1, true);
                    }
                }
            }
        }
        super.tick();
    }

}
