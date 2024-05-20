package com.ducvn.summonerclass.integration.jei;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.data.recipe.EssenceExtractorRecipe;
import com.ducvn.summonerclass.data.recipe.SummonerClassRecipeTypesRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.stream.Collectors;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(SummonerClassMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new EssenceExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(rm.getAllRecipesFor(SummonerClassRecipeTypesRegister.ESSENCE_EXTRACTOR_RECIPE).stream()
                        .filter(r -> r instanceof EssenceExtractorRecipe).collect(Collectors.toList()),
                EssenceExtractorRecipeCategory.UID);
    }
}
