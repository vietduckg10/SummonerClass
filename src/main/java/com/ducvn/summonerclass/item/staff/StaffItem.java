package com.ducvn.summonerclass.item.staff;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.enchantment.staff.StaffEnchantment;
import com.ducvn.summonerclass.entity.summonedmob.*;
import com.google.common.collect.ImmutableSet;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.List;
import java.util.Set;

public class StaffItem extends Item implements IVanishable, IStaffItem {
    private static Set enchantmentsAllowed = ImmutableSet.of(
            Enchantments.UNBREAKING,
            Enchantments.KNOCKBACK,
            Enchantments.MENDING);
    public StaffItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantmentsAllowed.contains(enchantment) || enchantment instanceof StaffEnchantment;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    public boolean hasMinionWitherEffect(ItemStack stack){
        return EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_WITHER.get());
    }
    public boolean hasMinionPoisonEffect(ItemStack stack){
        return EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_POISON.get());
    }
    public boolean hasMinionFireEffect(ItemStack stack){
        return EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_FIRE.get());
    }
    public boolean hasMinionSlownessEffect(ItemStack stack){
        return EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_SLOWNESS.get());
    }
    public boolean hasMinionWeaknessEffect(ItemStack stack){
        return EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_WEAKNESS.get());
    }
    public boolean hasMinionThornEffect(ItemStack stack){
        return EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_THORN.get());
    }
    public boolean hasMinionSupremeEffect(ItemStack stack){
        return EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_SUPREME.get());
    }

    public int getBonusMinion(PlayerEntity player){
        List<ItemStack> armorList = player.inventory.armor;
        int bonusMinion = 0;
        for (ItemStack stack : armorList){
            if (EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_INCREASE.get())){
                bonusMinion = bonusMinion + SummonerClassConfig.minion_increase.get();
            }
        }
        return bonusMinion;
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
        if (entity instanceof BeeEntity || entity instanceof BlazeEntity || entity instanceof PhantomEntity){
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

    public void giveMinionEnchantment(ISummonedEntity summonedMob, ItemStack stack, PlayerEntity user){
        if (hasMinionWitherEffect(stack)){
            summonedMob.setWitherHit();
        }
        if (hasMinionPoisonEffect(stack)){
            summonedMob.setPoisonHit();
        }
        if (hasMinionFireEffect(stack)){
            summonedMob.setFireHit();
        }
        if (hasMinionSlownessEffect(stack)){
            summonedMob.setSlownessHit();
        }
        if (hasMinionWeaknessEffect(stack)){
            summonedMob.setWeaknessHit();
        }
        if (hasMinionThornEffect(stack)){
            summonedMob.setThorn();
        }
        if (hasMinionSupremeEffect(stack)){
            summonedMob.setSupreme();
        }
        if (hasMinionArmorEffect(user, SummonerClassEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                || hasMinionSupremeEffect(stack)){
            summonedMob = (ISummonedEntity) addBonusAttack((LivingEntity) summonedMob, summonedMob.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * SummonerClassConfig.minion_damage.get());
        }
        if (hasMinionArmorEffect(user, SummonerClassEnchantmentsRegister.MINION_HEALTH.get(), 3)
                || hasMinionSupremeEffect(stack)){
            summonedMob = (ISummonedEntity) addBonusHealth((LivingEntity) summonedMob, summonedMob.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerClassConfig.minion_health.get());
        }
        if (hasMinionArmorEffect(user, SummonerClassEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                || hasMinionSupremeEffect(stack)){
            summonedMob.setExplode();
        }
        if (hasMinionArmorEffect(user, SummonerClassEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                || hasMinionSupremeEffect(stack)){
            summonedMob.setBuffMaster();
        }
        if (hasMinionArmorEffect(user, SummonerClassEnchantmentsRegister.MINION_SPEED.get(), 1)
                || hasMinionSupremeEffect(stack)){
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
        if (hasMinionArmorEffect(user, SummonerClassEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                || hasMinionSupremeEffect(stack)){
            summonedMob = (ISummonedEntity) addBonusKnockbackRes((LivingEntity) summonedMob, SummonerClassConfig.minion_resistance.get());
        }
        if (hasMinionArmorEffect(user, SummonerClassEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                || hasMinionSupremeEffect(stack)){
            summonedMob.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerClassConfig.minion_invisible_duration.get()));
        }
        if (hasMinionArmorEffect(user, SummonerClassEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                || hasMinionSupremeEffect(stack)){
            summonedMob.setMagnetize();
        }
    }

//    public boolean hasNoArmor(PlayerEntity player){
//        List<ItemStack> armors = player.inventory.armor;
//        for (ItemStack stack : armors){
//            if (stack.getItem() != Items.AIR){
//                return false;
//            }
//        }
//        return true;
//    }
    public List<ItemStack> getArmorSet(PlayerEntity player){
        return player.inventory.armor;
    }
}
