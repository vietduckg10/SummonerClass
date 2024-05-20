package com.ducvn.summonerclass.entity;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.entity.projectile.*;
import com.ducvn.summonerclass.entity.summonedmob.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonerClassEntitiesRegister {
    public static final DeferredRegister<EntityType<?>> ENTITIE_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SummonerClassMod.MODID);

    public static void init(IEventBus eventBus){
        ENTITIE_TYPES.register(eventBus);
    }

    public static final RegistryObject<EntityType<BeeStaffProjectileEntity>> BEE_STAFF_PROJECTILE = ENTITIE_TYPES.register("bee_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<BeeStaffProjectileEntity>) BeeStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("bee_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedBeeEntity>> SUMMONED_BEE = ENTITIE_TYPES.register("summoned_bee", () ->
            EntityType.Builder.of(SummonedBeeEntity::new, EntityClassification.MISC)
                    .build("summoned_bee"));

    public static final RegistryObject<EntityType<ZombieStaffProjectileEntity>> ZOMBIE_STAFF_PROJECTILE = ENTITIE_TYPES.register("zombie_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<ZombieStaffProjectileEntity>) ZombieStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("zombie_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedZombieEntity>> SUMMONED_ZOMBIE = ENTITIE_TYPES.register("summoned_zombie", () ->
            EntityType.Builder.of(SummonedZombieEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_zombie").toString()));

    public static final RegistryObject<EntityType<SkeletonStaffProjectileEntity>> SKELETON_STAFF_PROJECTILE = ENTITIE_TYPES.register("skeleton_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<SkeletonStaffProjectileEntity>) SkeletonStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("skeleton_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedSkeletonEntity>> SUMMONED_SKELETON = ENTITIE_TYPES.register("summoned_skeleton", () ->
            EntityType.Builder.of(SummonedSkeletonEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_skeleton").toString()));

    public static final RegistryObject<EntityType<CreeperStaffProjectileEntity>> CREEPER_STAFF_PROJECTILE = ENTITIE_TYPES.register("creeper_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<CreeperStaffProjectileEntity>) CreeperStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("creeper_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedCreeperEntity>> SUMMONED_CREEPER = ENTITIE_TYPES.register("summoned_creeper", () ->
            EntityType.Builder.of(SummonedCreeperEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_creeper").toString()));

    public static final RegistryObject<EntityType<EndermanStaffProjectileEntity>> ENDERMAN_STAFF_PROJECTILE = ENTITIE_TYPES.register("enderman_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<EndermanStaffProjectileEntity>) EndermanStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("enderman_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedEndermanEntity>> SUMMONED_ENDERMAN = ENTITIE_TYPES.register("summoned_enderman", () ->
            EntityType.Builder.of(SummonedEndermanEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_enderman").toString()));

    public static final RegistryObject<EntityType<WitherSkeletonStaffProjectileEntity>> WITHER_SKELETON_STAFF_PROJECTILE = ENTITIE_TYPES.register("wither_skeleton_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<WitherSkeletonStaffProjectileEntity>) WitherSkeletonStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("wither_skeleton_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedWitherSkeletonEntity>> SUMMONED_WITHER_SKELETON = ENTITIE_TYPES.register("summoned_wither_skeleton", () ->
            EntityType.Builder.of(SummonedWitherSkeletonEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_wither_skeleton").toString()));

    public static final RegistryObject<EntityType<SpiderStaffProjectileEntity>> SPIDER_STAFF_PROJECTILE = ENTITIE_TYPES.register("spider_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<SpiderStaffProjectileEntity>) SpiderStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("spider_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedSpiderEntity>> SUMMONED_SPIDER = ENTITIE_TYPES.register("summoned_spider", () ->
            EntityType.Builder.of(SummonedSpiderEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_spider").toString()));

    public static final RegistryObject<EntityType<BlazeStaffProjectileEntity>> BLAZE_STAFF_PROJECTILE = ENTITIE_TYPES.register("blaze_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<BlazeStaffProjectileEntity>) BlazeStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("blaze_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedBlazeEntity>> SUMMONED_BLAZE = ENTITIE_TYPES.register("summoned_blaze", () ->
            EntityType.Builder.of(SummonedBlazeEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_blaze").toString()));
    public static final RegistryObject<EntityType<SmallEffectFireballEntity>> EFFECT_FIREBALL_PROJECTILE = ENTITIE_TYPES.register("effect_fireball_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<SmallEffectFireballEntity>) SmallEffectFireballEntity::new, EntityClassification.MISC)
                    .sized(0.3125F, 0.3125F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("effect_fireball_projectile"));

    public static final RegistryObject<EntityType<IronGolemStaffProjectileEntity>> IRON_GOLEM_STAFF_PROJECTILE = ENTITIE_TYPES.register("iron_golem_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<IronGolemStaffProjectileEntity>) IronGolemStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("iron_golem_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedIronGolemEntity>> SUMMONED_IRON_GOLEM = ENTITIE_TYPES.register("summoned_iron_golem", () ->
            EntityType.Builder.of(SummonedIronGolemEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_iron_golem").toString()));

    public static final RegistryObject<EntityType<WolfStaffProjectileEntity>> WOLF_STAFF_PROJECTILE = ENTITIE_TYPES.register("wolf_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<WolfStaffProjectileEntity>) WolfStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("wolf_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedWolfEntity>> SUMMONED_WOLF = ENTITIE_TYPES.register("summoned_wolf", () ->
            EntityType.Builder.of(SummonedWolfEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_wolf").toString()));

    public static final RegistryObject<EntityType<PhantomStaffProjectileEntity>> PHANTOM_STAFF_PROJECTILE = ENTITIE_TYPES.register("phantom_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<PhantomStaffProjectileEntity>) PhantomStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("phantom_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedPhantomEntity>> SUMMONED_PHANTOM = ENTITIE_TYPES.register("summoned_phantom", () ->
            EntityType.Builder.of(SummonedPhantomEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_phantom").toString()));

    public static final RegistryObject<EntityType<GuardianStaffProjectileEntity>> GUARDIAN_STAFF_PROJECTILE = ENTITIE_TYPES.register("guardian_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<GuardianStaffProjectileEntity>) GuardianStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("guardian_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedGuardianEntity>> SUMMONED_GUARDIAN = ENTITIE_TYPES.register("summoned_guardian", () ->
            EntityType.Builder.of(SummonedGuardianEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_guardian").toString()));

    public static final RegistryObject<EntityType<PolarBearStaffProjectileEntity>> POLAR_BEAR_STAFF_PROJECTILE = ENTITIE_TYPES.register("polar_bear_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<PolarBearStaffProjectileEntity>) PolarBearStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("polar_bear_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedPolarBearEntity>> SUMMONED_POLAR_BEAR = ENTITIE_TYPES.register("summoned_polar_bear", () ->
            EntityType.Builder.of(SummonedPolarBearEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_polar_bear").toString()));

    public static final RegistryObject<EntityType<SlimeStaffProjectileEntity>> SLIME_STAFF_PROJECTILE = ENTITIE_TYPES.register("slime_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<SlimeStaffProjectileEntity>) SlimeStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("slime_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedSlimeEntity>> SUMMONED_SLIME = ENTITIE_TYPES.register("summoned_slime", () ->
            EntityType.Builder.of(SummonedSlimeEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_slime").toString()));

    public static final RegistryObject<EntityType<WitchStaffProjectileEntity>> WITCH_STAFF_PROJECTILE = ENTITIE_TYPES.register("witch_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<WitchStaffProjectileEntity>) WitchStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("witch_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedWitchEntity>> SUMMONED_WITCH = ENTITIE_TYPES.register("summoned_witch", () ->
            EntityType.Builder.of(SummonedWitchEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_witch").toString()));

    public static final RegistryObject<EntityType<GhastStaffProjectileEntity>> GHAST_STAFF_PROJECTILE = ENTITIE_TYPES.register("ghast_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<GhastStaffProjectileEntity>) GhastStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("ghast_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedGhastEntity>> SUMMONED_GHAST = ENTITIE_TYPES.register("summoned_ghast", () ->
            EntityType.Builder.of(SummonedGhastEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_ghast").toString()));

    public static final RegistryObject<EntityType<SnowGolemStaffProjectileEntity>> SNOW_GOLEM_STAFF_PROJECTILE = ENTITIE_TYPES.register("snow_golem_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<SnowGolemStaffProjectileEntity>) SnowGolemStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("snow_golem_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedSnowGolemEntity>> SUMMONED_SNOW_GOLEM = ENTITIE_TYPES.register("summoned_snow_golem", () ->
            EntityType.Builder.of(SummonedSnowGolemEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_snow_golem").toString()));

    public static final RegistryObject<EntityType<IllagerStaffProjectileEntity>> ILLAGER_STAFF_PROJECTILE = ENTITIE_TYPES.register("illager_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<IllagerStaffProjectileEntity>) IllagerStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("illager_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedPillagerEntity>> SUMMONED_PILLAGER = ENTITIE_TYPES.register("summoned_pillager", () ->
            EntityType.Builder.of(SummonedPillagerEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_pillager").toString()));
    public static final RegistryObject<EntityType<SummonedVindicatorEntity>> SUMMONED_VINDICATOR = ENTITIE_TYPES.register("summoned_vindicator", () ->
            EntityType.Builder.of(SummonedVindicatorEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_vindicator").toString()));
    public static final RegistryObject<EntityType<SummonedRavagerEntity>> SUMMONED_RAVAGER = ENTITIE_TYPES.register("summoned_ravager", () ->
            EntityType.Builder.of(SummonedRavagerEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_ravager").toString()));

    public static final RegistryObject<EntityType<PiglinStaffProjectileEntity>> PIGLIN_STAFF_PROJECTILE = ENTITIE_TYPES.register("piglin_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<PiglinStaffProjectileEntity>) PiglinStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("piglin_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedPiglinEntity>> SUMMONED_PIGLIN = ENTITIE_TYPES.register("summoned_piglin", () ->
            EntityType.Builder.of(SummonedPiglinEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_piglin").toString()));
    public static final RegistryObject<EntityType<SummonedPiglinBruteEntity>> SUMMONED_PIGLIN_BRUTE = ENTITIE_TYPES.register("summoned_piglin_brute", () ->
            EntityType.Builder.of(SummonedPiglinBruteEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_piglin_brute").toString()));
    public static final RegistryObject<EntityType<SummonedHoglinEntity>> SUMMONED_HOGLIN = ENTITIE_TYPES.register("summoned_hoglin", () ->
            EntityType.Builder.of(SummonedHoglinEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_hoglin").toString()));

    public static final RegistryObject<EntityType<WitherStaffProjectileEntity>> WITHER_STAFF_PROJECTILE = ENTITIE_TYPES.register("wither_staff_projectile", () ->
            EntityType.Builder.of((EntityType.IFactory<WitherStaffProjectileEntity>) WitherStaffProjectileEntity::new, EntityClassification.MISC)
                    .sized(0.1F,0.1F)
                    .build("wither_staff_projectile"));
    public static final RegistryObject<EntityType<SummonedWitherEntity>> SUMMONED_WITHER = ENTITIE_TYPES.register("summoned_wither", () ->
            EntityType.Builder.of(SummonedWitherEntity::new, EntityClassification.MISC)
                    .build(new ResourceLocation(SummonerClassMod.MODID + "summoned_wither").toString()));
}
