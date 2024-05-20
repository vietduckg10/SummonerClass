package com.ducvn.summonerclass.potion;

import com.ducvn.summonerclass.SummonerClassMod;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonerClassPotionsRegister {
    public static final DeferredRegister<Potion> VANILLA_POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, SummonerClassMod.MODID);
    public static void init(IEventBus bus){
        VANILLA_POTIONS.register(bus);
    }

    public static final RegistryObject<Potion> WITHER_POTION = VANILLA_POTIONS.register("wither_potion", () ->
            new Potion(new EffectInstance(Effects.WITHER, 900)));
}
