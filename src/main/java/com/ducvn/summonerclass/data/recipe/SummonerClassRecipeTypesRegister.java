package com.ducvn.summonerclass.data.recipe;

import com.ducvn.summonerclass.SummonerClassMod;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonerClassRecipeTypesRegister {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SummonerClassMod.MODID);

    public static void init(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);

        Registry.register(Registry.RECIPE_TYPE, EssenceExtractorRecipe.TYPE_ID, ESSENCE_EXTRACTOR_RECIPE);
    }

    public static final RegistryObject<EssenceExtractorRecipe.Serializer> ESSENCE_EXTRACTOR_SERIALIZER
            = RECIPE_SERIALIZER.register("essence_extract", EssenceExtractorRecipe.Serializer::new);

    public static IRecipeType<EssenceExtractorRecipe> ESSENCE_EXTRACTOR_RECIPE = new EssenceExtractorRecipe.EssenceExtractorRecipeType();
}
