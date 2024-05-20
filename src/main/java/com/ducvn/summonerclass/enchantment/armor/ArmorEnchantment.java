package com.ducvn.summonerclass.enchantment.armor;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ArmorEnchantment extends Enchantment {
    public ArmorEnchantment(Rarity pRarity, EnchantmentType pCategory, EquipmentSlotType... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof ArmorEnchantment);
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}
