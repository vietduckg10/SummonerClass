package com.ducvn.summonerclass.item.armor.basic;

import com.ducvn.summonerclass.item.armor.SummonerArmor;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BeeArmor extends SummonerArmor {
    public int numBee = 2;
    public BeeArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslationTextComponent(
                "\u00A7aBee staff summon \u00A7r" +
                        (EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())
                                ? "\u00A7a" + 2
                                : (numBee == 2 ? "\u00A7a" : "\u00A7d") + numBee) + "\u00A7r" +
                        "\u00A7a more bees\u00A7r"
                )
        );
    }
}
