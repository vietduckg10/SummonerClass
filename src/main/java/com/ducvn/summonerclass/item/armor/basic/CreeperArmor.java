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

public class CreeperArmor extends SummonerArmor {
    public int bonusChance = 30;
    public CreeperArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                "\u00A7aYour creeper have \u00A7r" +
                        (bonusChance == 30 ? "\u00A7a" : "\u00A7d")+ bonusChance + "%\u00A7r" +
                        "\u00A7a to be a charged creeper\u00A7r"
                )
        );
    }
}
