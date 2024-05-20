package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedPillagerEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedRavagerEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedVindicatorEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedIllagerArmor;
import com.ducvn.summonerclass.item.armor.basic.IllagerArmor;
import com.ducvn.summonerclass.potion.SummonerClassPotionsRegister;
import com.google.common.collect.Lists;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
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

public class IllagerStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public IllagerStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public IllagerStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.ILLAGER_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public IllagerStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.ILLAGER_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.TOTEM_OF_UNDYING.asItem();
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
                int illagerNumber = 2 + bonusMinion;
                for (int num = 1; num <= illagerNumber; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    if (roll.nextBoolean()){
                        SummonedPillagerEntity illager = new SummonedPillagerEntity(SummonerClassEntitiesRegister.SUMMONED_PILLAGER.get(), level);
                        ItemStack illagerCrossBow = Items.CROSSBOW.getDefaultInstance();
                        if (xAdjust == 0 && zAdjust == 0) {
                            illager.setPos(this.getOwner().position().x,
                                    this.getOwner().position().y + 3D,
                                    this.getOwner().position().z);
                        } else {
                            illager.setPos(this.getOwner().position().x + xAdjust,
                                    this.getOwner().position().y + yAdjust,
                                    this.getOwner().position().z + zAdjust);
                        }
                        illager.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                        illager.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s pillager"));
                        if (witherHit && !hasFullArmorSet((PlayerEntity) this.getOwner())){
                            ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                            arrow.setCount(64);
                            illager.setItemInHand(Hand.OFF_HAND,
                                    PotionUtils.setPotion(
                                            arrow,
                                            SummonerClassPotionsRegister.WITHER_POTION.get())
                            );
                            illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                        }
                        if (poisonHit && !hasFullArmorSet((PlayerEntity) this.getOwner())){
                            ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                            arrow.setCount(64);
                            illager.setItemInHand(Hand.OFF_HAND,
                                    PotionUtils.setPotion(
                                            arrow,
                                            Potions.POISON)
                            );
                            illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                        }
                        if (fireHit){
                            illagerCrossBow.enchant(Enchantments.MULTISHOT, 1);
                        }
                        if (slownessHit && !hasFullArmorSet((PlayerEntity) this.getOwner())){
                            ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                            arrow.setCount(64);
                            illager.setItemInHand(Hand.OFF_HAND,
                                    PotionUtils.setPotion(
                                            arrow,
                                            Potions.SLOWNESS)
                            );
                            illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                        }
                        if (weaknessHit && !hasFullArmorSet((PlayerEntity) this.getOwner())){
                            ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                            arrow.setCount(64);
                            illager.setItemInHand(Hand.OFF_HAND,
                                    PotionUtils.setPotion(
                                            arrow,
                                            Potions.WEAKNESS)
                            );
                            illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                        }
                        if (hasThorn){
                            illager.setThorn();
                        }
                        if (isSupreme){
                            illager.setSupreme();
                        }
                        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(), SummonerClassEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                                || isSupreme){
                            illagerCrossBow.enchant(Enchantments.QUICK_CHARGE, 1);
                        }
                        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_HEALTH.get(), 3)
                                || isSupreme){
                            illager = (SummonedPillagerEntity) addBonusHealth(illager, illager.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerClassConfig.minion_health.get());
                        }
                        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                                || isSupreme){
                            illager.setExplode();
                        }
                        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                                || isSupreme){
                            illager.setBuffMaster();
                        }
                        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_SPEED.get(), 1)
                                || isSupreme){
                            illager = (SummonedPillagerEntity) addBonusSpeed(illager, illager.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * SummonerClassConfig.minion_speed.get());
                        }
                        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                                || isSupreme){
                            illager = (SummonedPillagerEntity) addBonusKnockbackRes(illager, SummonerClassConfig.minion_resistance.get());
                        }
                        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                                || isSupreme){
                            illager.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerClassConfig.minion_invisible_duration.get()));
                        }
                        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                                || isSupreme){
                            illager.setMagnetize();
                        }
                        if (hasFullArmorSet((PlayerEntity) this.getOwner())){
                            ItemStack offHandItem = Items.FIREWORK_ROCKET.getDefaultInstance();
                            offHandItem.setCount(64);
                            CompoundNBT explosionData = new CompoundNBT();
                            List<Integer> list = Lists.newArrayList();
                            list.add(15728640);
                            list.add(1852922);
                            explosionData.putIntArray("Colors", list);
                            explosionData.putByte("Type", (byte) FireworkRocketItem.Shape.BURST.getId());
                            ListNBT listnbt = new ListNBT();
                            listnbt.add(explosionData);
                            offHandItem.getOrCreateTagElement("Fireworks").put("Explosions", listnbt);
                            illager.setItemInHand(Hand.OFF_HAND, offHandItem);
                            illager.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                        }
                        illager.setItemInHand(Hand.MAIN_HAND, illagerCrossBow);
                        illager.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                        if (roll.nextInt(100) < 100){
                            illager.setBaby(true);
                        }
                        level.addFreshEntity(illager);
                    }
                    else {
                        SummonedVindicatorEntity illager = new SummonedVindicatorEntity(SummonerClassEntitiesRegister.SUMMONED_VINDICATOR.get(), this.getOwner().level);
                        if (xAdjust == 0 && zAdjust == 0) {
                            illager.setPos(this.getOwner().position().x,
                                    this.getOwner().position().y + 3D,
                                    this.getOwner().position().z);
                        } else {
                            illager.setPos(this.getOwner().position().x + xAdjust,
                                    this.getOwner().position().y + yAdjust,
                                    this.getOwner().position().z + zAdjust);
                        }
                        illager.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                        illager.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s vindicator"));
                        giveMinionEnchantment(illager);

                        if (hasFullArmorSet((PlayerEntity) this.getOwner())){
                            illager.setItemInHand(Hand.MAIN_HAND, Items.GOLDEN_AXE.getDefaultInstance());
                        }
                        else {
                            illager.setItemInHand(Hand.MAIN_HAND, Items.IRON_AXE.getDefaultInstance());
                        }
                        illager.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                        if (roll.nextInt(100) < 3){
                            illager.setBaby(true);
                        }
                        level.addFreshEntity(illager);
                    }
                }
                if (hasAdvanceArmorSet((PlayerEntity) this.getOwner()) && roll.nextInt(100) < 100){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedRavagerEntity ravager = new SummonedRavagerEntity(SummonerClassEntitiesRegister.SUMMONED_RAVAGER.get(), this.getOwner().level);
                    if (xAdjust == 0 && zAdjust == 0) {
                        ravager.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    } else {
                        ravager.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    ravager.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    ravager.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s ravager"));
                    giveMinionEnchantment(ravager);
                    level.addFreshEntity(ravager);
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
            if (!(stack.getItem() instanceof IllagerArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof IllagerArmor){
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
            if (!(stack.getItem() instanceof AdvancedIllagerArmor)){
                return false;
            }
        }
        return true;
    }
}
