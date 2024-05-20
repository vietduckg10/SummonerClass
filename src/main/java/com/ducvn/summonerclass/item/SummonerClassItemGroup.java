package com.ducvn.summonerclass.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SummonerClassItemGroup {
    public static final ItemGroup SUMMONER_CLASS_ITEM_GROUP = new ItemGroup("summonerclass") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SummonerClassItemsRegister.BEE_STAFF.get());
        }
    };
}
