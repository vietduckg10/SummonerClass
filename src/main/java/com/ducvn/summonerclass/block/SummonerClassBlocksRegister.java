package com.ducvn.summonerclass.block;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.item.SummonerClassItemGroup;
import com.ducvn.summonerclass.item.SummonerClassItemsRegister;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SummonerClassBlocksRegister {
    public static final DeferredRegister<Block> SUMMONER_CLASS_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SummonerClassMod.MODID);
    public static void init(IEventBus eventBus) {
        SUMMONER_CLASS_BLOCKS.register(eventBus);
    }
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = SUMMONER_CLASS_BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        SummonerClassItemsRegister.SUMMONER_CLASS_ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(SummonerClassItemGroup.SUMMONER_CLASS_ITEM_GROUP).stacksTo(1)));
    }

    public static final RegistryObject<Block> ESSENCE_EXTRACTOR = registerBlock("essence_extractor",
            () -> new EssenceExtractorBlock(AbstractBlock.Properties.of(Material.STONE)
                    .strength(2.0F, 6.0F)
            )
    );
}
