package com.ducvn.summonerclass;

import com.ducvn.summonerclass.block.SummonerClassBlocksRegister;
import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.container.SummonerClassContainersRegister;
import com.ducvn.summonerclass.data.recipe.SummonerClassRecipeTypesRegister;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRenderer;
import com.ducvn.summonerclass.item.SummonerClassItemsRegister;
import com.ducvn.summonerclass.potion.SummonerClassPotionsRegister;
import com.ducvn.summonerclass.renderer.*;
import com.ducvn.summonerclass.screen.EssenceExtractorScreen;
import com.ducvn.summonerclass.tileentity.SummonerClassTileEntitiesRegister;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SummonerClassMod.MODID)
public class SummonerClassMod
{
    public static final String MODID = "summonerclass";
    public static final UUID Summoner_Class_UUID = UUID.fromString("c68688e7-dcf9-4707-af8f-d7b38b902003");

    public SummonerClassMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        // Register mod stuff :3
        SummonerClassItemsRegister.init(eventBus);
        SummonerClassBlocksRegister.init(eventBus);
        SummonerClassTileEntitiesRegister.init(eventBus);
        SummonerClassContainersRegister.init(eventBus);
        SummonerClassRecipeTypesRegister.init(eventBus);
        SummonerClassPotionsRegister.init(eventBus);
        SummonerClassEntitiesRegister.init(eventBus);
        SummonerClassEnchantmentsRegister.init(eventBus);

        // Register ourselves for server and other game events we are interested in
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SummonerClassConfig.SPEC, "SummonerClass-common.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> SummonerClassEntitiesRenderer::registerEntityRenders);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_ZOMBIE.get(), SummonedZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_BEE.get(), SummonedBeeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_SKELETON.get(), SkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_CREEPER.get(), SummonedCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_ENDERMAN.get(), EndermanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_WITHER_SKELETON.get(), WitherSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_SPIDER.get(), SpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_BLAZE.get(), BlazeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_IRON_GOLEM.get(), SummonedIronGolemRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_WOLF.get(), SummonedWolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_PHANTOM.get(), PhantomRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_GUARDIAN.get(), GuardianRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_POLAR_BEAR.get(), PolarBearRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_SLIME.get(), SummonedSlimeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_WITCH.get(), SummonedWitchRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_GHAST.get(), SummonedGhastRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_SNOW_GOLEM.get(), SnowManRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_PILLAGER.get(), SummonedPillagerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_VINDICATOR.get(), SummonedVindicatorRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_RAVAGER.get(), RavagerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_PIGLIN.get(), SummonedPiglinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_PIGLIN_BRUTE.get(), SummonedPiglinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_HOGLIN.get(), HoglinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SUMMONED_WITHER.get(), SummonedWitherRenderer::new);
        event.enqueueWork(() -> {
            ScreenManager.register(SummonerClassContainersRegister.ESSENCE_EXTRACTOR_CONTAINER.get(),
                    EssenceExtractorScreen::new);
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        }
    }
}
