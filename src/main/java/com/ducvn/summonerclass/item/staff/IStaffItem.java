package com.ducvn.summonerclass.item.staff;

import net.minecraft.item.ItemStack;

public interface IStaffItem {
    boolean hasMinionWitherEffect(ItemStack stack);
    boolean hasMinionPoisonEffect(ItemStack stack);
    boolean hasMinionFireEffect(ItemStack stack);
    boolean hasMinionSlownessEffect(ItemStack stack);
    boolean hasMinionWeaknessEffect(ItemStack stack);
    boolean hasMinionThornEffect(ItemStack stack);
    boolean hasMinionSupremeEffect(ItemStack stack);
}
