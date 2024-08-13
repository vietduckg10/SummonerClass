package com.ducvn.summonerclass.event;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SummonerClassMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SummonerClassEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(SummonerClassEntitiesRegister.SUMMONED_ZOMBIE.get(), SummonedZombieEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_BEE.get(), SummonedBeeEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_SKELETON.get(), SummonedSkeletonEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_CREEPER.get(), SummonedCreeperEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_ENDERMAN.get(), SummonedEndermanEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_WITHER_SKELETON.get(), SummonedWitherSkeletonEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_SPIDER.get(), SummonedSpiderEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_BLAZE.get(), SummonedBlazeEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_IRON_GOLEM.get(), SummonedIronGolemEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_WOLF.get(), SummonedWolfEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_PHANTOM.get(), SummonedPhantomEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_GUARDIAN.get(), SummonedGuardianEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_POLAR_BEAR.get(), SummonedPolarBearEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_SLIME.get(), SummonedSlimeEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_WITCH.get(), SummonedWitchEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_GHAST.get(), SummonedGhastEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_SNOW_GOLEM.get(), SummonedSnowGolemEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_PILLAGER.get(), SummonedPillagerEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_VINDICATOR.get(), SummonedVindicatorEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_RAVAGER.get(), SummonedRavagerEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_PIGLIN.get(), SummonedPiglinEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_PIGLIN_BRUTE.get(), SummonedPiglinBruteEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_HOGLIN.get(), SummonedHoglinEntity.createAttributes().build());
        event.put(SummonerClassEntitiesRegister.SUMMONED_WITHER.get(), SummonedWitherEntity.createAttributes().build());
    }
}