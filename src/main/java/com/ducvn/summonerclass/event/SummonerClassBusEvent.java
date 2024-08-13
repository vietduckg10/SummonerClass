package com.ducvn.summonerclass.event;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonercoremod.event.loottable.EssenceLootModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = SummonerClassMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SummonerClassBusEvent {
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().registerAll(
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_bee")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_zombie")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_spider")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_creeper")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_skeleton")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_enderman")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_iron_golem")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_wolf")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_wither_skeleton")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_blaze")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_phantom")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_guardian")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_polar_bear")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_slime")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_witch")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_ghast")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_snow_golem")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_pillager")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_vindicator")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_evoker")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_piglin")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_piglin_brute")),
                new EssenceLootModifier.Serializer().setRegistryName
                        (new ResourceLocation(SummonerClassMod.MODID,"essence_from_wither"))
        );
    }
}
