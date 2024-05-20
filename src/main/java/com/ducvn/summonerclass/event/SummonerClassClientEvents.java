package com.ducvn.summonerclass.event;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.tileentity.SummonerClassTileEntitiesRegister;
import com.ducvn.summonerclass.tileentity.renderer.EssenceExtractorTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SummonerClassMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SummonerClassClientEvents {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        ClientRegistry.bindTileEntityRenderer(SummonerClassTileEntitiesRegister.ESSENCE_EXTRACTOR_TILE.get(), EssenceExtractorTileEntityRenderer::new);
    }
}
