package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.summonedmob.*;
import com.ducvn.summonerclass.item.staff.IStaffItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class StaffProjectileEntity extends ProjectileItemEntity implements IStaffProjectile {
    public boolean witherHit = false;
    public boolean poisonHit = false;
    public boolean fireHit = false;
    public boolean slownessHit = false;
    public boolean weaknessHit = false;
    public boolean hasThorn = false;
    public boolean isSupreme = false;
    public int bonusMinion = 0;
    public StaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public StaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50156_1_, double p_i50156_2_, double p_i50156_4_, double p_i50156_6_, World p_i50156_8_) {
        super(p_i50156_1_, p_i50156_2_, p_i50156_4_, p_i50156_6_, p_i50156_8_);
    }

    public StaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50157_1_, LivingEntity p_i50157_2_, World p_i50157_3_) {
        super(p_i50157_1_, p_i50157_2_, p_i50157_3_);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    public boolean isNoGravity() {
        return true;
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
    public void setSupreme(){
        isSupreme = true;
    }
    public void setBonusMinion(int num){
        bonusMinion = num;
    }
    public boolean hasMinionArmorEffect(PlayerEntity player, Enchantment enchantment, int slot){
        ItemStack armor = player.inventory.getArmor(slot);
        if (EnchantmentHelper.getEnchantments(armor).containsKey(enchantment)){
            return true;
        }
        else {
            return false;
        }
    }
    public LivingEntity addBonusAttack(LivingEntity entity, double bonus){
        ModifiableAttributeInstance attackAttribute = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeModifier DAMAGE_MODIFIER = new AttributeModifier(
                SummonerClassMod.Summoner_Class_UUID, "Bonus Minion Damage",
                bonus, AttributeModifier.Operation.ADDITION);
        attackAttribute.addTransientModifier(DAMAGE_MODIFIER);
        return entity;
    }
    public LivingEntity addBonusHealth(LivingEntity entity, double bonus){
        ModifiableAttributeInstance healthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier HEALTH_MODIFIER = new AttributeModifier(
                SummonerClassMod.Summoner_Class_UUID, "Bonus Minion Health",
                bonus, AttributeModifier.Operation.ADDITION);
        healthAttribute.addTransientModifier(HEALTH_MODIFIER);
        entity.heal((float) bonus);
        return entity;
    }
    public LivingEntity addBonusSpeed(LivingEntity entity, double bonus){
        if (entity instanceof BeeEntity){
            entity.flyingSpeed = (float) bonus;
        }
        else {
            ModifiableAttributeInstance speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier SPEED_MODIFIER = new AttributeModifier(
                    SummonerClassMod.Summoner_Class_UUID, "Bonus Minion Speed",
                    bonus, AttributeModifier.Operation.ADDITION);
            speedAttribute.addTransientModifier(SPEED_MODIFIER);
        }
        return entity;
    }
    public LivingEntity addBonusKnockbackRes(LivingEntity entity, double bonus){
        ModifiableAttributeInstance knockbackResAttribute = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        AttributeModifier KNOCKBACK_RES_MODIFIER = new AttributeModifier(
                SummonerClassMod.Summoner_Class_UUID, "Bonus Minion Knockback Res",
                bonus, AttributeModifier.Operation.ADDITION);
        knockbackResAttribute.addTransientModifier(KNOCKBACK_RES_MODIFIER);
        return entity;
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        this.remove();
    }

    public List<ItemStack> getArmorSet(PlayerEntity player){
        return player.inventory.armor;
    }

    public void giveMinionEnchantment(ISummonedEntity summonedMob){
        if (witherHit){
            summonedMob.setWitherHit();
        }
        if (poisonHit){
            summonedMob.setPoisonHit();
        }
        if (fireHit){
            summonedMob.setFireHit();
        }
        if (slownessHit){
            summonedMob.setSlownessHit();
        }
        if (weaknessHit){
            summonedMob.setWeaknessHit();
        }
        if (hasThorn){
            summonedMob.setThorn();
        }
        if (isSupreme){
            summonedMob.setSupreme();
        }
        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(), SummonerClassEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                || isSupreme){
            summonedMob = (ISummonedEntity) addBonusAttack((LivingEntity) summonedMob, summonedMob.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * SummonerClassConfig.minion_damage.get());
        }
        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_HEALTH.get(), 3)
                || isSupreme){
            summonedMob = (ISummonedEntity) addBonusHealth((LivingEntity) summonedMob, summonedMob.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerClassConfig.minion_health.get());
        }
        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(), SummonerClassEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                || isSupreme){
            summonedMob.setExplode();
        }
        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                || isSupreme){
            summonedMob.setBuffMaster();
        }
        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_SPEED.get(), 1)
                || isSupreme){
            if (summonedMob instanceof SummonedBeeEntity
                    || summonedMob instanceof SummonedBlazeEntity
                    || summonedMob instanceof SummonedPhantomEntity
                    || summonedMob instanceof SummonedGhastEntity) {
                summonedMob = (ISummonedEntity) addBonusSpeed(
                        (LivingEntity) summonedMob,
                        summonedMob.getAttribute(Attributes.FLYING_SPEED).getValue() / 5);
            }
            else {
                summonedMob = (ISummonedEntity) addBonusSpeed(
                        (LivingEntity) summonedMob,
                        summonedMob.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * SummonerClassConfig.minion_speed.get());
            }
        }
        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                || isSupreme){
            summonedMob = (ISummonedEntity) addBonusKnockbackRes((LivingEntity) summonedMob, SummonerClassConfig.minion_resistance.get());
        }
        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                || isSupreme){
            summonedMob.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerClassConfig.minion_invisible_duration.get()));
        }
        if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                || isSupreme){
            summonedMob.setMagnetize();
        }
    }

    public void setUpProjectile(IStaffProjectile projectile, IStaffItem staffItem, PlayerEntity player, Hand hand){
        if (staffItem.hasMinionWitherEffect(player.getItemInHand(hand))){
            projectile.setWitherHit();
        }
        if (staffItem.hasMinionPoisonEffect(player.getItemInHand(hand))){
            projectile.setPoisonHit();
        }
        if (staffItem.hasMinionFireEffect(player.getItemInHand(hand))){
            projectile.setFireHit();
        }
        if (staffItem.hasMinionSlownessEffect(player.getItemInHand(hand))){
            projectile.setSlownessHit();
        }
        if (staffItem.hasMinionWeaknessEffect(player.getItemInHand(hand))){
            projectile.setWeaknessHit();
        }
        if (staffItem.hasMinionThornEffect(player.getItemInHand(hand))){
            projectile.setThorn();
        }
        if (staffItem.hasMinionSupremeEffect(player.getItemInHand(hand))){
            projectile.setSupreme();
        }
    }
}
