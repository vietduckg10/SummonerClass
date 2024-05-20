package com.ducvn.summonerclass.integration.jei;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.block.SummonerClassBlocksRegister;
import com.ducvn.summonerclass.data.recipe.EssenceExtractorRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EssenceExtractorRecipeCategory implements IRecipeCategory<EssenceExtractorRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(SummonerClassMod.MODID, "essence_extract");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(SummonerClassMod.MODID, "textures/gui/essence_extractor_gui.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic arrow;

    public EssenceExtractorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 91);
        this.icon = helper.createDrawableIngredient(new ItemStack(SummonerClassBlocksRegister.ESSENCE_EXTRACTOR.get()));
        this.arrow = helper.createDrawable(TEXTURE, 176, 0, 17, 24);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends EssenceExtractorRecipe> getRecipeClass() {
        return EssenceExtractorRecipe.class;
    }

    @Override
    public String getTitle() {
        return SummonerClassBlocksRegister.ESSENCE_EXTRACTOR.get().getName().getString();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(EssenceExtractorRecipe recipe, IIngredients ingredients) {
        if (recipe.getIngredients().get(1).getItems()[0].getItem().equals(Items.AIR)){
            List<Ingredient> ingredientList = new ArrayList<>(Arrays.asList(recipe.getIngredients().get(0)));
            ingredients.setInputIngredients(ingredientList);
        }
        else {
            ingredients.setInputIngredients(recipe.getIngredients());
        }
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EssenceExtractorRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 79, 18);
        recipeLayout.getItemStacks().init(1, true, 101, 39);

        recipeLayout.getItemStacks().init(2, false, 79, 62);
        recipeLayout.getItemStacks().set(ingredients);
    }

    @Override
    public void draw(EssenceExtractorRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        this.arrow.draw(matrixStack, 79, 37);
    }
}
