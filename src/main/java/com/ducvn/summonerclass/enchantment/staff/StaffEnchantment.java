package com.ducvn.summonerclass.enchantment.staff;

import com.ducvn.summonerclass.item.staff.StaffItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class StaffEnchantment extends Enchantment {
    public StaffEnchantment(Rarity pRarity, EnchantmentType pCategory, EquipmentSlotType... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof StaffEnchantment);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof StaffItem;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }
}
