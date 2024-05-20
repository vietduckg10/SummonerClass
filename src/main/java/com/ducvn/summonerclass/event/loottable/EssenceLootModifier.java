package com.ducvn.summonerclass.event.loottable;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class EssenceLootModifier extends LootModifier {
    private final Item itemDrop;
    protected EssenceLootModifier(ILootCondition[] conditionsIn, Item itemDrop) {
        super(conditionsIn);
        this.itemDrop = itemDrop;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(itemDrop));
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<EssenceLootModifier> {

        @Override
        public EssenceLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            Item itemDrop = ForgeRegistries.ITEMS.getValue(
                    new ResourceLocation(JSONUtils.getAsString(object, "item")));
            return new EssenceLootModifier(conditionsIn, itemDrop);
        }

        @Override
        public JsonObject write(EssenceLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("item", ForgeRegistries.ITEMS.getKey(instance.itemDrop).toString());
            return json;
        }
    }
}
