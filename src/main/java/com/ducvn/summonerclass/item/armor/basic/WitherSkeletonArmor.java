package com.ducvn.summonerclass.item.armor.basic;

import com.ducvn.summonerclass.item.armor.SummonerArmor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class WitherSkeletonArmor extends SummonerArmor {
    public int killChance = 1;
    public WitherSkeletonArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                "\u00A7aYour wither skeleton have \u00A7r" +
                        (killChance == 1 ? "\u00A7a" : "\u00A7d") + killChance + "%\u00A7r" +
                        "\u00A7a chance to instant kill target\u00A7r"
                )
        );
    }
}
