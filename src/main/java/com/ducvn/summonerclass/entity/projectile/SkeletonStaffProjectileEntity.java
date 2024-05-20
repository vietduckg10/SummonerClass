package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedSkeletonEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedSkeletonArmor;
import com.ducvn.summonerclass.item.armor.basic.SkeletonArmor;
import com.ducvn.summonerclass.potion.SummonerClassPotionsRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.Random;

public class SkeletonStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public SkeletonStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public SkeletonStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.SKELETON_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public SkeletonStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.SKELETON_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.BONE.asItem();
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
                int numSkeleton = 2 + bonusMinion;
                boolean summonMelee = false;
                if (hasFullArmorSet((PlayerEntity) this.getOwner())
                        && roll.nextInt(10) < (hasAdvanceArmorSet((PlayerEntity) this.getOwner()) ? 5 : 3)){
                    numSkeleton = numSkeleton + 2;
                    summonMelee = true;
                }
                for (int num = 1; num <= numSkeleton; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedSkeletonEntity skeleton = new SummonedSkeletonEntity(SummonerClassEntitiesRegister.SUMMONED_SKELETON.get(), level);
                    ItemStack skeletonBow = Items.BOW.getDefaultInstance();
                    if (xAdjust == 0 && zAdjust == 0){
                        skeleton.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        skeleton.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    skeleton.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    skeleton.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s skeleton"));
                    if (hasFullArmorSet((PlayerEntity) this.getOwner())){
                        skeleton.setImmuneSunLight();
                    }
                    if (witherHit){
                        if (summonMelee && num >= (numSkeleton - 1)){
                            skeleton.setWitherHit();
                        }
                        else {
                            skeleton.setItemInHand(Hand.OFF_HAND,
                                    PotionUtils.setPotion(
                                            Items.TIPPED_ARROW.getDefaultInstance(),
                                            SummonerClassPotionsRegister.WITHER_POTION.get())
                            );
                            skeleton.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                        }
                    }
                    if (poisonHit){
                        if (summonMelee && num >= (numSkeleton - 1)){
                            skeleton.setPoisonHit();
                        }
                        else {
                            skeleton.setItemInHand(Hand.OFF_HAND,
                                    PotionUtils.setPotion(
                                            Items.TIPPED_ARROW.getDefaultInstance(),
                                            Potions.POISON)
                            );
                            skeleton.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                        }
                    }
                    if (fireHit){
                        if (summonMelee && num >= (numSkeleton - 1)){
                            skeleton.setFireHit();
                        }
                        else {
                            skeletonBow.enchant(Enchantments.FLAMING_ARROWS, 1);
                        }
                    }
                    if (slownessHit){
                        if (summonMelee && num >= (numSkeleton - 1)){
                            skeleton.setSlownessHit();
                        }
                        else {
                            skeleton.setItemInHand(Hand.OFF_HAND,
                                    PotionUtils.setPotion(
                                            Items.TIPPED_ARROW.getDefaultInstance(),
                                            Potions.SLOWNESS)
                            );
                            skeleton.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                        }
                    }
                    if (weaknessHit){
                        if (summonMelee && num >= (numSkeleton - 1)){
                            skeleton.setWeaknessHit();
                        }
                        else {
                            skeleton.setItemInHand(Hand.OFF_HAND,
                                    PotionUtils.setPotion(
                                            Items.TIPPED_ARROW.getDefaultInstance(),
                                            Potions.WEAKNESS)
                            );
                            skeleton.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                        }
                    }
                    if (hasThorn){
                        skeleton.setThorn();
                    }
                    if (isSupreme){
                        skeleton.setSupreme();
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                            || isSupreme){
                        if (summonMelee && num >= (numSkeleton - 1)){
                            skeleton = (SummonedSkeletonEntity) addBonusAttack(skeleton, skeleton.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * SummonerClassConfig.minion_damage.get());
                        }
                        else {
                            skeletonBow.enchant(Enchantments.POWER_ARROWS, 1);
                        }
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_HEALTH.get(), 3)
                            || isSupreme){
                        skeleton = (SummonedSkeletonEntity) addBonusHealth(skeleton, skeleton.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerClassConfig.minion_health.get());
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(), SummonerClassEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                            || isSupreme){
                        skeleton.setExplode();
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                            || isSupreme){
                        skeleton.setBuffMaster();
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_SPEED.get(), 1)
                            || isSupreme){
                        skeleton = (SummonedSkeletonEntity) addBonusSpeed(skeleton, skeleton.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * SummonerClassConfig.minion_speed.get());
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                            || isSupreme){
                        skeleton = (SummonedSkeletonEntity) addBonusKnockbackRes(skeleton, SummonerClassConfig.minion_resistance.get());
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                            || isSupreme){
                        skeleton.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerClassConfig.minion_invisible_duration.get()));
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                            || isSupreme){
                        skeleton.setMagnetize();
                    }
                    if (summonMelee && num >= (numSkeleton - 1)){
                        if (hasAdvanceArmorSet((PlayerEntity) this.getOwner())){
                            skeleton.setItemSlot(
                                    EquipmentSlotType.CHEST,
                                    Items.CHAINMAIL_CHESTPLATE.getDefaultInstance());
                            skeleton.setItemSlot(
                                    EquipmentSlotType.LEGS,
                                    Items.CHAINMAIL_LEGGINGS.getDefaultInstance());
                            skeleton.setItemSlot(
                                    EquipmentSlotType.FEET,
                                    Items.CHAINMAIL_BOOTS.getDefaultInstance());
                            skeleton.setItemSlot(
                                    EquipmentSlotType.MAINHAND,
                                    Items.IRON_SWORD.getDefaultInstance());
                            skeleton.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                            skeleton.setDropChance(EquipmentSlotType.CHEST, 0.0f);
                            skeleton.setDropChance(EquipmentSlotType.LEGS, 0.0f);
                            skeleton.setDropChance(EquipmentSlotType.FEET, 0.0f);
                        }
                    }
                    else {
                        skeleton.setItemInHand(Hand.MAIN_HAND, skeletonBow);
                        skeleton.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                        if (hasAdvanceArmorSet((PlayerEntity) this.getOwner())){
                            skeleton.setItemSlot(
                                    EquipmentSlotType.CHEST,
                                    Items.CHAINMAIL_CHESTPLATE.getDefaultInstance());
                            skeleton.setItemSlot(
                                    EquipmentSlotType.LEGS,
                                    Items.CHAINMAIL_LEGGINGS.getDefaultInstance());
                            skeleton.setItemSlot(
                                    EquipmentSlotType.FEET,
                                    Items.CHAINMAIL_BOOTS.getDefaultInstance());
                            skeleton.setDropChance(EquipmentSlotType.CHEST, 0.0f);
                            skeleton.setDropChance(EquipmentSlotType.LEGS, 0.0f);
                            skeleton.setDropChance(EquipmentSlotType.FEET, 0.0f);
                        }
                    }
                    level.addFreshEntity(skeleton);
                    if (roll.nextInt(100) < 3){
                        SummonedSkeletonEntity riderSkeleton = new SummonedSkeletonEntity(SummonerClassEntitiesRegister.SUMMONED_SKELETON.get(), level);
                        ItemStack riderSkeletonBow = Items.BOW.getDefaultInstance();
                        riderSkeleton.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                        riderSkeleton.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s skeleton"));
                        riderSkeleton.setItemInHand(Hand.MAIN_HAND, riderSkeletonBow);
                        riderSkeleton.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                        riderSkeleton.setItemSlot(
                                EquipmentSlotType.HEAD,
                                Items.LEATHER_HELMET.getDefaultInstance());
                        riderSkeleton.setDropChance(EquipmentSlotType.HEAD, 0.0f);
                        level.addFreshEntity(riderSkeleton);
                        riderSkeleton.startRiding(skeleton);
                    }
                }
            }
            this.remove();
        }
    }

    private boolean hasFullArmorSet(PlayerEntity player) {
        List<ItemStack> armorList = getArmorSet(player);
        boolean isCombined = true;
        boolean haveAtLeastOne = false;

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof ArmorItem)){
                return false;
            }
            if (!(stack.getItem() instanceof SkeletonArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof SkeletonArmor){
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
        List<ItemStack> armorList = getArmorSet(player);

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof AdvancedSkeletonArmor)){
                return false;
            }
        }
        return true;
    }
}
