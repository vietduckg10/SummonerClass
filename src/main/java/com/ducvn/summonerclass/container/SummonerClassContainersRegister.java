package com.ducvn.summonerclass.container;

import com.ducvn.summonerclass.SummonerClassMod;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonerClassContainersRegister {
    public static DeferredRegister<ContainerType<?>> SUMMONER_CLASS_CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, SummonerClassMod.MODID);
    public static void init(IEventBus eventBus) {
        SUMMONER_CLASS_CONTAINERS.register(eventBus);
    }

    public static final RegistryObject<ContainerType<EssenceExtractorContainer>> ESSENCE_EXTRACTOR_CONTAINER
            = SUMMONER_CLASS_CONTAINERS.register("essence_extractor_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.level;
                return new EssenceExtractorContainer(windowId, world, pos, inv, inv.player);
            })));
}
