package com.ducvn.summonerclass.tileentity;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.block.SummonerClassBlocksRegister;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonerClassTileEntitiesRegister {
    public static DeferredRegister<TileEntityType<?>> SUMMONER_CLASS_TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SummonerClassMod.MODID);

    public static void init(IEventBus eventBus){
        SUMMONER_CLASS_TILE_ENTITIES.register(eventBus);
    }

    public static final RegistryObject<TileEntityType<EssenceExtractorTileEntity>> ESSENCE_EXTRACTOR_TILE =
            SUMMONER_CLASS_TILE_ENTITIES.register("essence_extractor_tile", () ->
                    TileEntityType.Builder.of(
                            EssenceExtractorTileEntity::new, SummonerClassBlocksRegister.ESSENCE_EXTRACTOR.get()).build(null)
            );
}
