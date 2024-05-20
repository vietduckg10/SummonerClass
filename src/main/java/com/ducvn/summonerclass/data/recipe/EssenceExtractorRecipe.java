package com.ducvn.summonerclass.data.recipe;

import com.ducvn.summonerclass.block.SummonerClassBlocksRegister;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class EssenceExtractorRecipe implements IEssenceExtractorRecipe {
    private final ResourceLocation id;
    private final ItemStack output;
    private Ingredient input;
    private Ingredient additionInput;
    private final int extractTime;

    public EssenceExtractorRecipe(ResourceLocation id, ItemStack output, Ingredient input, @Nullable Ingredient additionInput, int extractTime) {
        this.id = id;
        this.output = output;
        this.input = input;
        this.additionInput = additionInput;
        this.extractTime = extractTime;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(null, this.input, this.additionInput);
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        if (inv.getItem(0).isEmpty()) {
            return false;
        }
        if (inv.getItem(1).isEmpty()) {
            return this.input.test(inv.getItem(0)) && this.additionInput.test(Items.AIR.getDefaultInstance());
        }
        else {
            return this.input.test(inv.getItem(0)) && this.additionInput.test(inv.getItem(1));
        }
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return output;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public Ingredient getInput() {
        return input;
    }

    @Nullable
    public Ingredient getAdditionInput(){
        return additionInput;
    }

    public int getExtractTime(){
        return this.extractTime;
    }

    public ItemStack getIcon() {
        return new ItemStack(SummonerClassBlocksRegister.ESSENCE_EXTRACTOR.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SummonerClassRecipeTypesRegister.ESSENCE_EXTRACTOR_SERIALIZER.get();
    }

    public static class EssenceExtractorRecipeType implements IRecipeType<EssenceExtractorRecipe> {
        @Override
        public String toString() {
            return EssenceExtractorRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<EssenceExtractorRecipe> {

        @Override
        public EssenceExtractorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "output"), true);
            Ingredient input = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "input"));
            Ingredient additionInput = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "addition_input"));
            int extractTime = JSONUtils.getAsInt(json, "time");

            input.getItems()[0].setCount(
                    CraftingHelper.getItemStack(
                            JSONUtils.getAsJsonObject(json, "input"), true)
                            .getCount()
            );
            if (!(CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "addition_input"), true).getItem()
                    instanceof AirItem)){
                additionInput.getItems()[0].setCount(
                        CraftingHelper.getItemStack(
                                JSONUtils.getAsJsonObject(json, "addition_input"), true)
                                .getCount()
                );
            }

            return new EssenceExtractorRecipe(recipeId, output,
                    input, additionInput, extractTime);
        }

        @Nullable
        @Override
        public EssenceExtractorRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            Ingredient additionInput = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();

            return new EssenceExtractorRecipe(recipeId, output,
                    input, additionInput, 0);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, EssenceExtractorRecipe recipe) {
            Ingredient input = recipe.getIngredients().get(0);
            Ingredient additionInput = recipe.getIngredients().get(1);
            input.toNetwork(buffer);
            additionInput.toNetwork(buffer);

            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
