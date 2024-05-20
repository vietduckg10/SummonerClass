package com.ducvn.summonerclass.item.armor.advanced;

import com.ducvn.summonerclass.item.armor.basic.WitherSkeletonArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;

public class AdvancedWitherSkeletonArmor extends WitherSkeletonArmor {
    public AdvancedWitherSkeletonArmor(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
        this.killChance = 2;
    }
}
