package com.ducvn.summonerclass.item;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.item.armor.advanced.*;
import com.ducvn.summonerclass.item.armor.basic.*;
import com.ducvn.summonerclass.item.staff.*;
import com.ducvn.summonercoremod.item.SummonerCoreItemGroup;
import com.ducvn.summonercoremod.item.other.EssenceItem;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonerClassItemsRegister {
    public static final DeferredRegister<Item> SUMMONER_CLASS_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SummonerClassMod.MODID);

    public static void init(IEventBus bus){
        SUMMONER_CLASS_ITEMS.register(bus);
    }

    // Bee
    public static final RegistryObject<Item> BEE_STAFF = SUMMONER_CLASS_ITEMS.register("bee_staff", () ->
            new BeeStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> BEE_HELMET = SUMMONER_CLASS_ITEMS.register("bee_helmet", () ->
            new BeeArmor(SummonerClassArmorMaterial.BEE, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> BEE_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("bee_chestplate", () ->
            new BeeArmor(SummonerClassArmorMaterial.BEE, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> BEE_LEGGINGS = SUMMONER_CLASS_ITEMS.register("bee_leggings", () ->
            new BeeArmor(SummonerClassArmorMaterial.BEE, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> BEE_BOOTS = SUMMONER_CLASS_ITEMS.register("bee_boots", () ->
            new BeeArmor(SummonerClassArmorMaterial.BEE, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_BEE_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_bee_helmet", () ->
            new AdvancedBeeArmor(SummonerClassArmorMaterial.ADVANCED_BEE, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_BEE_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_bee_chestplate", () ->
            new AdvancedBeeArmor(SummonerClassArmorMaterial.ADVANCED_BEE, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_BEE_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_bee_leggings", () ->
            new AdvancedBeeArmor(SummonerClassArmorMaterial.ADVANCED_BEE, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_BEE_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_bee_boots", () ->
            new AdvancedBeeArmor(SummonerClassArmorMaterial.ADVANCED_BEE, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Zombie
    public static final RegistryObject<Item> ZOMBIE_STAFF = SUMMONER_CLASS_ITEMS.register("zombie_staff", () ->
            new ZombieStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> ZOMBIE_HELMET = SUMMONER_CLASS_ITEMS.register("zombie_helmet", () ->
            new ZombieArmor(SummonerClassArmorMaterial.ZOMBIE, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ZOMBIE_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("zombie_chestplate", () ->
            new ZombieArmor(SummonerClassArmorMaterial.ZOMBIE, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ZOMBIE_LEGGINGS = SUMMONER_CLASS_ITEMS.register("zombie_leggings", () ->
            new ZombieArmor(SummonerClassArmorMaterial.ZOMBIE, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ZOMBIE_BOOTS = SUMMONER_CLASS_ITEMS.register("zombie_boots", () ->
            new ZombieArmor(SummonerClassArmorMaterial.ZOMBIE, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ZOMBIE_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_zombie_helmet", () ->
            new AdvancedZombieArmor(SummonerClassArmorMaterial.ADVANCED_ZOMBIE, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ZOMBIE_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_zombie_chestplate", () ->
            new AdvancedZombieArmor(SummonerClassArmorMaterial.ADVANCED_ZOMBIE, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ZOMBIE_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_zombie_leggings", () ->
            new AdvancedZombieArmor(SummonerClassArmorMaterial.ADVANCED_ZOMBIE, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ZOMBIE_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_zombie_boots", () ->
            new AdvancedZombieArmor(SummonerClassArmorMaterial.ADVANCED_ZOMBIE, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Skeleton
    public static final RegistryObject<Item> SKELETON_STAFF = SUMMONER_CLASS_ITEMS.register("skeleton_staff", () ->
            new SkeletonStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> SKELETON_HELMET = SUMMONER_CLASS_ITEMS.register("skeleton_helmet", () ->
            new SkeletonArmor(SummonerClassArmorMaterial.SKELETON, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SKELETON_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("skeleton_chestplate", () ->
            new SkeletonArmor(SummonerClassArmorMaterial.SKELETON, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SKELETON_LEGGINGS = SUMMONER_CLASS_ITEMS.register("skeleton_leggings", () ->
            new SkeletonArmor(SummonerClassArmorMaterial.SKELETON, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SKELETON_BOOTS = SUMMONER_CLASS_ITEMS.register("skeleton_boots", () ->
            new SkeletonArmor(SummonerClassArmorMaterial.SKELETON, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SKELETON_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_skeleton_helmet", () ->
            new AdvancedSkeletonArmor(SummonerClassArmorMaterial.ADVANCED_SKELETON, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SKELETON_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_skeleton_chestplate", () ->
            new AdvancedSkeletonArmor(SummonerClassArmorMaterial.ADVANCED_SKELETON, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SKELETON_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_skeleton_leggings", () ->
            new AdvancedSkeletonArmor(SummonerClassArmorMaterial.ADVANCED_SKELETON, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SKELETON_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_skeleton_boots", () ->
            new AdvancedSkeletonArmor(SummonerClassArmorMaterial.ADVANCED_SKELETON, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Creeper
    public static final RegistryObject<Item> CREEPER_STAFF = SUMMONER_CLASS_ITEMS.register("creeper_staff", () ->
            new CreeperStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> CREEPER_HELMET = SUMMONER_CLASS_ITEMS.register("creeper_helmet", () ->
            new CreeperArmor(SummonerClassArmorMaterial.CREEPER, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> CREEPER_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("creeper_chestplate", () ->
            new CreeperArmor(SummonerClassArmorMaterial.CREEPER, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> CREEPER_LEGGINGS = SUMMONER_CLASS_ITEMS.register("creeper_leggings", () ->
            new CreeperArmor(SummonerClassArmorMaterial.CREEPER, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> CREEPER_BOOTS = SUMMONER_CLASS_ITEMS.register("creeper_boots", () ->
            new CreeperArmor(SummonerClassArmorMaterial.CREEPER, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_CREEPER_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_creeper_helmet", () ->
            new AdvancedCreeperArmor(SummonerClassArmorMaterial.ADVANCED_CREEPER, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_CREEPER_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_creeper_chestplate", () ->
            new AdvancedCreeperArmor(SummonerClassArmorMaterial.ADVANCED_CREEPER, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_CREEPER_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_creeper_leggings", () ->
            new AdvancedCreeperArmor(SummonerClassArmorMaterial.ADVANCED_CREEPER, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_CREEPER_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_creeper_boots", () ->
            new AdvancedCreeperArmor(SummonerClassArmorMaterial.ADVANCED_CREEPER, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Enderman
    public static final RegistryObject<Item> ENDERMAN_STAFF = SUMMONER_CLASS_ITEMS.register("enderman_staff", () ->
            new EndermanStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> ENDERMAN_HELMET = SUMMONER_CLASS_ITEMS.register("enderman_helmet", () ->
            new EndermanArmor(SummonerClassArmorMaterial.ENDERMAN, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ENDERMAN_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("enderman_chestplate", () ->
            new EndermanArmor(SummonerClassArmorMaterial.ENDERMAN, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ENDERMAN_LEGGINGS = SUMMONER_CLASS_ITEMS.register("enderman_leggings", () ->
            new EndermanArmor(SummonerClassArmorMaterial.ENDERMAN, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ENDERMAN_BOOTS = SUMMONER_CLASS_ITEMS.register("enderman_boots", () ->
            new EndermanArmor(SummonerClassArmorMaterial.ENDERMAN, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ENDERMAN_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_enderman_helmet", () ->
            new AdvancedEndermanArmor(SummonerClassArmorMaterial.ADVANCED_ENDERMAN, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ENDERMAN_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_enderman_chestplate", () ->
            new AdvancedEndermanArmor(SummonerClassArmorMaterial.ADVANCED_ENDERMAN, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ENDERMAN_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_enderman_leggings", () ->
            new AdvancedEndermanArmor(SummonerClassArmorMaterial.ADVANCED_ENDERMAN, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ENDERMAN_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_enderman_boots", () ->
            new AdvancedEndermanArmor(SummonerClassArmorMaterial.ADVANCED_ENDERMAN, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // WitherSkeleton
    public static final RegistryObject<Item> WITHER_SKELETON_STAFF = SUMMONER_CLASS_ITEMS.register("wither_skeleton_staff", () ->
            new WitherSkeletonStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> WITHER_SKELETON_HELMET = SUMMONER_CLASS_ITEMS.register("wither_skeleton_helmet", () ->
            new WitherSkeletonArmor(SummonerClassArmorMaterial.WITHER_SKELETON, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WITHER_SKELETON_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("wither_skeleton_chestplate", () ->
            new WitherSkeletonArmor(SummonerClassArmorMaterial.WITHER_SKELETON, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WITHER_SKELETON_LEGGINGS = SUMMONER_CLASS_ITEMS.register("wither_skeleton_leggings", () ->
            new WitherSkeletonArmor(SummonerClassArmorMaterial.WITHER_SKELETON, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WITHER_SKELETON_BOOTS = SUMMONER_CLASS_ITEMS.register("wither_skeleton_boots", () ->
            new WitherSkeletonArmor(SummonerClassArmorMaterial.WITHER_SKELETON, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WITHER_SKELETON_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_wither_skeleton_helmet", () ->
            new AdvancedWitherSkeletonArmor(SummonerClassArmorMaterial.ADVANCED_WITHER_SKELETON, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WITHER_SKELETON_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_wither_skeleton_chestplate", () ->
            new AdvancedWitherSkeletonArmor(SummonerClassArmorMaterial.ADVANCED_WITHER_SKELETON, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WITHER_SKELETON_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_wither_skeleton_leggings", () ->
            new AdvancedWitherSkeletonArmor(SummonerClassArmorMaterial.ADVANCED_WITHER_SKELETON, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WITHER_SKELETON_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_wither_skeleton_boots", () ->
            new AdvancedWitherSkeletonArmor(SummonerClassArmorMaterial.ADVANCED_WITHER_SKELETON, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Spider
    public static final RegistryObject<Item> SPIDER_STAFF = SUMMONER_CLASS_ITEMS.register("spider_staff", () ->
            new SpiderStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> SPIDER_HELMET = SUMMONER_CLASS_ITEMS.register("spider_helmet", () ->
            new SpiderArmor(SummonerClassArmorMaterial.SPIDER, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SPIDER_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("spider_chestplate", () ->
            new SpiderArmor(SummonerClassArmorMaterial.SPIDER, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SPIDER_LEGGINGS = SUMMONER_CLASS_ITEMS.register("spider_leggings", () ->
            new SpiderArmor(SummonerClassArmorMaterial.SPIDER, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SPIDER_BOOTS = SUMMONER_CLASS_ITEMS.register("spider_boots", () ->
            new SpiderArmor(SummonerClassArmorMaterial.SPIDER, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SPIDER_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_spider_helmet", () ->
            new AdvancedSpiderArmor(SummonerClassArmorMaterial.ADVANCED_SPIDER, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SPIDER_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_spider_chestplate", () ->
            new AdvancedSpiderArmor(SummonerClassArmorMaterial.ADVANCED_SPIDER, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SPIDER_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_spider_leggings", () ->
            new AdvancedSpiderArmor(SummonerClassArmorMaterial.ADVANCED_SPIDER, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SPIDER_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_spider_boots", () ->
            new AdvancedSpiderArmor(SummonerClassArmorMaterial.ADVANCED_SPIDER, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Blaze
    public static final RegistryObject<Item> BLAZE_STAFF = SUMMONER_CLASS_ITEMS.register("blaze_staff", () ->
            new BlazeStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> BLAZE_HELMET = SUMMONER_CLASS_ITEMS.register("blaze_helmet", () ->
            new BlazeArmor(SummonerClassArmorMaterial.BLAZE, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> BLAZE_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("blaze_chestplate", () ->
            new BlazeArmor(SummonerClassArmorMaterial.BLAZE, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> BLAZE_LEGGINGS = SUMMONER_CLASS_ITEMS.register("blaze_leggings", () ->
            new BlazeArmor(SummonerClassArmorMaterial.BLAZE, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> BLAZE_BOOTS = SUMMONER_CLASS_ITEMS.register("blaze_boots", () ->
            new BlazeArmor(SummonerClassArmorMaterial.BLAZE, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_BLAZE_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_blaze_helmet", () ->
            new AdvancedBlazeArmor(SummonerClassArmorMaterial.ADVANCED_BLAZE, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_BLAZE_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_blaze_chestplate", () ->
            new AdvancedBlazeArmor(SummonerClassArmorMaterial.ADVANCED_BLAZE, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_BLAZE_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_blaze_leggings", () ->
            new AdvancedBlazeArmor(SummonerClassArmorMaterial.ADVANCED_BLAZE, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_BLAZE_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_blaze_boots", () ->
            new AdvancedBlazeArmor(SummonerClassArmorMaterial.ADVANCED_BLAZE, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Iron Golem
    public static final RegistryObject<Item> IRON_GOLEM_STAFF = SUMMONER_CLASS_ITEMS.register("iron_golem_staff", () ->
            new IronGolemStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> IRON_GOLEM_HELMET = SUMMONER_CLASS_ITEMS.register("iron_golem_helmet", () ->
            new IronGolemArmor(SummonerClassArmorMaterial.IRON_GOLEM, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> IRON_GOLEM_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("iron_golem_chestplate", () ->
            new IronGolemArmor(SummonerClassArmorMaterial.IRON_GOLEM, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> IRON_GOLEM_LEGGINGS = SUMMONER_CLASS_ITEMS.register("iron_golem_leggings", () ->
            new IronGolemArmor(SummonerClassArmorMaterial.IRON_GOLEM, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> IRON_GOLEM_BOOTS = SUMMONER_CLASS_ITEMS.register("iron_golem_boots", () ->
            new IronGolemArmor(SummonerClassArmorMaterial.IRON_GOLEM, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_IRON_GOLEM_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_iron_golem_helmet", () ->
            new AdvancedIronGolemArmor(SummonerClassArmorMaterial.ADVANCED_IRON_GOLEM, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_IRON_GOLEM_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_iron_golem_chestplate", () ->
            new AdvancedIronGolemArmor(SummonerClassArmorMaterial.ADVANCED_IRON_GOLEM, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_IRON_GOLEM_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_iron_golem_leggings", () ->
            new AdvancedIronGolemArmor(SummonerClassArmorMaterial.ADVANCED_IRON_GOLEM, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_IRON_GOLEM_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_iron_golem_boots", () ->
            new AdvancedIronGolemArmor(SummonerClassArmorMaterial.ADVANCED_IRON_GOLEM, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Wolf
    public static final RegistryObject<Item> WOLF_STAFF = SUMMONER_CLASS_ITEMS.register("wolf_staff", () ->
            new WolfStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> WOLF_HELMET = SUMMONER_CLASS_ITEMS.register("wolf_helmet", () ->
            new WolfArmor(SummonerClassArmorMaterial.WOLF, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WOLF_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("wolf_chestplate", () ->
            new WolfArmor(SummonerClassArmorMaterial.WOLF, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WOLF_LEGGINGS = SUMMONER_CLASS_ITEMS.register("wolf_leggings", () ->
            new WolfArmor(SummonerClassArmorMaterial.WOLF, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WOLF_BOOTS = SUMMONER_CLASS_ITEMS.register("wolf_boots", () ->
            new WolfArmor(SummonerClassArmorMaterial.WOLF, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WOLF_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_wolf_helmet", () ->
            new AdvancedWolfArmor(SummonerClassArmorMaterial.ADVANCED_WOLF, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WOLF_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_wolf_chestplate", () ->
            new AdvancedWolfArmor(SummonerClassArmorMaterial.ADVANCED_WOLF, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WOLF_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_wolf_leggings", () ->
            new AdvancedWolfArmor(SummonerClassArmorMaterial.ADVANCED_WOLF, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WOLF_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_wolf_boots", () ->
            new AdvancedWolfArmor(SummonerClassArmorMaterial.ADVANCED_WOLF, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Phantom
    public static final RegistryObject<Item> PHANTOM_STAFF = SUMMONER_CLASS_ITEMS.register("phantom_staff", () ->
            new PhantomStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> PHANTOM_HELMET = SUMMONER_CLASS_ITEMS.register("phantom_helmet", () ->
            new PhantomArmor(SummonerClassArmorMaterial.PHANTOM, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> PHANTOM_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("phantom_chestplate", () ->
            new PhantomArmor(SummonerClassArmorMaterial.PHANTOM, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> PHANTOM_LEGGINGS = SUMMONER_CLASS_ITEMS.register("phantom_leggings", () ->
            new PhantomArmor(SummonerClassArmorMaterial.PHANTOM, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> PHANTOM_BOOTS = SUMMONER_CLASS_ITEMS.register("phantom_boots", () ->
            new PhantomArmor(SummonerClassArmorMaterial.PHANTOM, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_PHANTOM_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_phantom_helmet", () ->
            new AdvancedPhantomArmor(SummonerClassArmorMaterial.ADVANCED_PHANTOM, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_PHANTOM_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_phantom_chestplate", () ->
            new AdvancedPhantomArmor(SummonerClassArmorMaterial.ADVANCED_PHANTOM, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_PHANTOM_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_phantom_leggings", () ->
            new AdvancedPhantomArmor(SummonerClassArmorMaterial.ADVANCED_PHANTOM, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_PHANTOM_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_phantom_boots", () ->
            new AdvancedPhantomArmor(SummonerClassArmorMaterial.ADVANCED_PHANTOM, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Guardian
    public static final RegistryObject<Item> GUARDIAN_STAFF = SUMMONER_CLASS_ITEMS.register("guardian_staff", () ->
            new GuardianStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> GUARDIAN_HELMET = SUMMONER_CLASS_ITEMS.register("guardian_helmet", () ->
            new GuardianArmor(SummonerClassArmorMaterial.GUARDIAN, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> GUARDIAN_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("guardian_chestplate", () ->
            new GuardianArmor(SummonerClassArmorMaterial.GUARDIAN, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> GUARDIAN_LEGGINGS = SUMMONER_CLASS_ITEMS.register("guardian_leggings", () ->
            new GuardianArmor(SummonerClassArmorMaterial.GUARDIAN, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> GUARDIAN_BOOTS = SUMMONER_CLASS_ITEMS.register("guardian_boots", () ->
            new GuardianArmor(SummonerClassArmorMaterial.GUARDIAN, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_GUARDIAN_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_guardian_helmet", () ->
            new AdvancedGuardianArmor(SummonerClassArmorMaterial.ADVANCED_GUARDIAN, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_GUARDIAN_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_guardian_chestplate", () ->
            new AdvancedGuardianArmor(SummonerClassArmorMaterial.ADVANCED_GUARDIAN, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_GUARDIAN_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_guardian_leggings", () ->
            new AdvancedGuardianArmor(SummonerClassArmorMaterial.ADVANCED_GUARDIAN, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_GUARDIAN_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_guardian_boots", () ->
            new AdvancedGuardianArmor(SummonerClassArmorMaterial.ADVANCED_GUARDIAN, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Polar Bear
    public static final RegistryObject<Item> POLAR_BEAR_STAFF = SUMMONER_CLASS_ITEMS.register("polar_bear_staff", () ->
            new PolarBearStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> POLAR_BEAR_HELMET = SUMMONER_CLASS_ITEMS.register("polar_bear_helmet", () ->
            new PolarBearArmor(SummonerClassArmorMaterial.POLAR_BEAR, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> POLAR_BEAR_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("polar_bear_chestplate", () ->
            new PolarBearArmor(SummonerClassArmorMaterial.POLAR_BEAR, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> POLAR_BEAR_LEGGINGS = SUMMONER_CLASS_ITEMS.register("polar_bear_leggings", () ->
            new PolarBearArmor(SummonerClassArmorMaterial.POLAR_BEAR, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> POLAR_BEAR_BOOTS = SUMMONER_CLASS_ITEMS.register("polar_bear_boots", () ->
            new PolarBearArmor(SummonerClassArmorMaterial.POLAR_BEAR, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_POLAR_BEAR_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_polar_bear_helmet", () ->
            new AdvancedPolarBearArmor(SummonerClassArmorMaterial.ADVANCED_POLAR_BEAR, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_POLAR_BEAR_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_polar_bear_chestplate", () ->
            new AdvancedPolarBearArmor(SummonerClassArmorMaterial.ADVANCED_POLAR_BEAR, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_POLAR_BEAR_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_polar_bear_leggings", () ->
            new AdvancedPolarBearArmor(SummonerClassArmorMaterial.ADVANCED_POLAR_BEAR, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_POLAR_BEAR_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_polar_bear_boots", () ->
            new AdvancedPolarBearArmor(SummonerClassArmorMaterial.ADVANCED_POLAR_BEAR, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Slime
    public static final RegistryObject<Item> SLIME_STAFF = SUMMONER_CLASS_ITEMS.register("slime_staff", () ->
            new SlimeStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> SLIME_HELMET = SUMMONER_CLASS_ITEMS.register("slime_helmet", () ->
            new SlimeArmor(SummonerClassArmorMaterial.SLIME, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SLIME_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("slime_chestplate", () ->
            new SlimeArmor(SummonerClassArmorMaterial.SLIME, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SLIME_LEGGINGS = SUMMONER_CLASS_ITEMS.register("slime_leggings", () ->
            new SlimeArmor(SummonerClassArmorMaterial.SLIME, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SLIME_BOOTS = SUMMONER_CLASS_ITEMS.register("slime_boots", () ->
            new SlimeArmor(SummonerClassArmorMaterial.SLIME, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SLIME_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_slime_helmet", () ->
            new AdvancedSlimeArmor(SummonerClassArmorMaterial.ADVANCED_SLIME, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SLIME_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_slime_chestplate", () ->
            new AdvancedSlimeArmor(SummonerClassArmorMaterial.ADVANCED_SLIME, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SLIME_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_slime_leggings", () ->
            new AdvancedSlimeArmor(SummonerClassArmorMaterial.ADVANCED_SLIME, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SLIME_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_slime_boots", () ->
            new AdvancedSlimeArmor(SummonerClassArmorMaterial.ADVANCED_SLIME, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    // Witch
    public static final RegistryObject<Item> WITCH_STAFF = SUMMONER_CLASS_ITEMS.register("witch_staff", () ->
            new WitchStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> WITCH_HELMET = SUMMONER_CLASS_ITEMS.register("witch_helmet", () ->
            new WitchArmor(SummonerClassArmorMaterial.WITCH, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WITCH_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("witch_chestplate", () ->
            new WitchArmor(SummonerClassArmorMaterial.WITCH, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WITCH_LEGGINGS = SUMMONER_CLASS_ITEMS.register("witch_leggings", () ->
            new WitchArmor(SummonerClassArmorMaterial.WITCH, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WITCH_BOOTS = SUMMONER_CLASS_ITEMS.register("witch_boots", () ->
            new WitchArmor(SummonerClassArmorMaterial.WITCH, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WITCH_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_witch_helmet", () ->
            new AdvancedWitchArmor(SummonerClassArmorMaterial.ADVANCED_WITCH, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WITCH_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_witch_chestplate", () ->
            new AdvancedWitchArmor(SummonerClassArmorMaterial.ADVANCED_WITCH, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WITCH_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_witch_leggings", () ->
            new AdvancedWitchArmor(SummonerClassArmorMaterial.ADVANCED_WITCH, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_WITCH_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_witch_boots", () ->
            new AdvancedWitchArmor(SummonerClassArmorMaterial.ADVANCED_WITCH, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );

    // Ghast
    public static final RegistryObject<Item> GHAST_STAFF = SUMMONER_CLASS_ITEMS.register("ghast_staff", () ->
            new GhastStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> GHAST_HELMET = SUMMONER_CLASS_ITEMS.register("ghast_helmet", () ->
            new GhastArmor(SummonerClassArmorMaterial.GHAST, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> GHAST_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("ghast_chestplate", () ->
            new GhastArmor(SummonerClassArmorMaterial.GHAST, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> GHAST_LEGGINGS = SUMMONER_CLASS_ITEMS.register("ghast_leggings", () ->
            new GhastArmor(SummonerClassArmorMaterial.GHAST, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> GHAST_BOOTS = SUMMONER_CLASS_ITEMS.register("ghast_boots", () ->
            new GhastArmor(SummonerClassArmorMaterial.GHAST, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_GHAST_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_ghast_helmet", () ->
            new AdvancedGhastArmor(SummonerClassArmorMaterial.ADVANCED_GHAST, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_GHAST_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_ghast_chestplate", () ->
            new AdvancedGhastArmor(SummonerClassArmorMaterial.ADVANCED_GHAST, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_GHAST_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_ghast_leggings", () ->
            new AdvancedGhastArmor(SummonerClassArmorMaterial.ADVANCED_GHAST, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_GHAST_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_ghast_boots", () ->
            new AdvancedGhastArmor(SummonerClassArmorMaterial.ADVANCED_GHAST, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );

    // Snow Golem
    public static final RegistryObject<Item> SNOW_GOLEM_STAFF = SUMMONER_CLASS_ITEMS.register("snow_golem_staff", () ->
            new SnowGolemStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> SNOW_GOLEM_HELMET = SUMMONER_CLASS_ITEMS.register("snow_golem_helmet", () ->
            new SnowGolemArmor(SummonerClassArmorMaterial.SNOW_GOLEM, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SNOW_GOLEM_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("snow_golem_chestplate", () ->
            new SnowGolemArmor(SummonerClassArmorMaterial.SNOW_GOLEM, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SNOW_GOLEM_LEGGINGS = SUMMONER_CLASS_ITEMS.register("snow_golem_leggings", () ->
            new SnowGolemArmor(SummonerClassArmorMaterial.SNOW_GOLEM, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SNOW_GOLEM_BOOTS = SUMMONER_CLASS_ITEMS.register("snow_golem_boots", () ->
            new SnowGolemArmor(SummonerClassArmorMaterial.SNOW_GOLEM, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SNOW_GOLEM_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_snow_golem_helmet", () ->
            new AdvancedSnowGolemArmor(SummonerClassArmorMaterial.ADVANCED_SNOW_GOLEM, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SNOW_GOLEM_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_snow_golem_chestplate", () ->
            new AdvancedSnowGolemArmor(SummonerClassArmorMaterial.ADVANCED_SNOW_GOLEM, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SNOW_GOLEM_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_snow_golem_leggings", () ->
            new AdvancedSnowGolemArmor(SummonerClassArmorMaterial.ADVANCED_SNOW_GOLEM, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_SNOW_GOLEM_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_snow_golem_boots", () ->
            new AdvancedSnowGolemArmor(SummonerClassArmorMaterial.ADVANCED_SNOW_GOLEM, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );

    // Illager
    public static final RegistryObject<Item> ILLAGER_STAFF = SUMMONER_CLASS_ITEMS.register("illager_staff", () ->
            new IllagerStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> ILLAGER_HELMET = SUMMONER_CLASS_ITEMS.register("illager_helmet", () ->
            new IllagerArmor(SummonerClassArmorMaterial.ILLAGER, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ILLAGER_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("illager_chestplate", () ->
            new IllagerArmor(SummonerClassArmorMaterial.ILLAGER, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ILLAGER_LEGGINGS = SUMMONER_CLASS_ITEMS.register("illager_leggings", () ->
            new IllagerArmor(SummonerClassArmorMaterial.ILLAGER, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ILLAGER_BOOTS = SUMMONER_CLASS_ITEMS.register("illager_boots", () ->
            new IllagerArmor(SummonerClassArmorMaterial.ILLAGER, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ILLAGER_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_illager_helmet", () ->
            new AdvancedIllagerArmor(SummonerClassArmorMaterial.ADVANCED_ILLAGER, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ILLAGER_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_illager_chestplate", () ->
            new AdvancedIllagerArmor(SummonerClassArmorMaterial.ADVANCED_ILLAGER, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ILLAGER_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_illager_leggings", () ->
            new AdvancedIllagerArmor(SummonerClassArmorMaterial.ADVANCED_ILLAGER, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_ILLAGER_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_illager_boots", () ->
            new AdvancedIllagerArmor(SummonerClassArmorMaterial.ADVANCED_ILLAGER, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );

    // Piglin
    public static final RegistryObject<Item> PIGLIN_STAFF = SUMMONER_CLASS_ITEMS.register("piglin_staff", () ->
            new PiglinStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    public static final RegistryObject<Item> PIGLIN_HELMET = SUMMONER_CLASS_ITEMS.register("piglin_helmet", () ->
            new PiglinArmor(SummonerClassArmorMaterial.PIGLIN, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> PIGLIN_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("piglin_chestplate", () ->
            new PiglinArmor(SummonerClassArmorMaterial.PIGLIN, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> PIGLIN_LEGGINGS = SUMMONER_CLASS_ITEMS.register("piglin_leggings", () ->
            new PiglinArmor(SummonerClassArmorMaterial.PIGLIN, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> PIGLIN_BOOTS = SUMMONER_CLASS_ITEMS.register("piglin_boots", () ->
            new PiglinArmor(SummonerClassArmorMaterial.PIGLIN, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_PIGLIN_HELMET = SUMMONER_CLASS_ITEMS.register("advanced_piglin_helmet", () ->
            new AdvancedPiglinArmor(SummonerClassArmorMaterial.ADVANCED_PIGLIN, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_PIGLIN_CHESTPLATE = SUMMONER_CLASS_ITEMS.register("advanced_piglin_chestplate", () ->
            new AdvancedPiglinArmor(SummonerClassArmorMaterial.ADVANCED_PIGLIN, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_PIGLIN_LEGGINGS = SUMMONER_CLASS_ITEMS.register("advanced_piglin_leggings", () ->
            new AdvancedPiglinArmor(SummonerClassArmorMaterial.ADVANCED_PIGLIN, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ADVANCED_PIGLIN_BOOTS = SUMMONER_CLASS_ITEMS.register("advanced_piglin_boots", () ->
            new AdvancedPiglinArmor(SummonerClassArmorMaterial.ADVANCED_PIGLIN, EquipmentSlotType.FEET,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );

    // Wither
    public static final RegistryObject<Item> WITHER_STAFF = SUMMONER_CLASS_ITEMS.register("wither_staff", () ->
            new WitherStaffItem(
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
                            .durability(100)
            )
    );
    // Illager: summon 2 random vanilla illagers + upgrade + 15% ravager
    // Piglin: Summon 1-4 piglins + upgrade (brute + archer) + 30% summon hoglin
    // Wither: Summon 1 Wither cost 19 hp set player hp to 1 while this still exist (does not affect absorption)

    // Essences
    public static final RegistryObject<Item> BEE_ESSENCE = SUMMONER_CLASS_ITEMS.register("bee_essence", () ->
            new EssenceItem(EntityType.BEE,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ZOMBIE_ESSENCE = SUMMONER_CLASS_ITEMS.register("zombie_essence", () ->
            new EssenceItem(EntityType.ZOMBIE,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SKELETON_ESSENCE = SUMMONER_CLASS_ITEMS.register("skeleton_essence", () ->
            new EssenceItem(EntityType.SKELETON,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SPIDER_ESSENCE = SUMMONER_CLASS_ITEMS.register("spider_essence", () ->
            new EssenceItem(EntityType.SPIDER,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> CREEPER_ESSENCE = SUMMONER_CLASS_ITEMS.register("creeper_essence", () ->
            new EssenceItem(EntityType.CREEPER,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ENDERMAN_ESSENCE = SUMMONER_CLASS_ITEMS.register("enderman_essence", () ->
            new EssenceItem(EntityType.ENDERMAN,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> IRON_GOLEM_ESSENCE = SUMMONER_CLASS_ITEMS.register("iron_golem_essence", () ->
            new EssenceItem(EntityType.IRON_GOLEM,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WOLF_ESSENCE = SUMMONER_CLASS_ITEMS.register("wolf_essence", () ->
            new EssenceItem(EntityType.WOLF,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WITHER_SKELETON_ESSENCE = SUMMONER_CLASS_ITEMS.register("wither_skeleton_essence", () ->
            new EssenceItem(EntityType.WITHER_SKELETON,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> BLAZE_ESSENCE = SUMMONER_CLASS_ITEMS.register("blaze_essence", () ->
            new EssenceItem(EntityType.BLAZE,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> PHANTOM_ESSENCE = SUMMONER_CLASS_ITEMS.register("phantom_essence", () ->
            new EssenceItem(EntityType.PHANTOM,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> GUARDIAN_ESSENCE = SUMMONER_CLASS_ITEMS.register("guardian_essence", () ->
            new EssenceItem(EntityType.GUARDIAN,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> POLAR_BEAR_ESSENCE = SUMMONER_CLASS_ITEMS.register("polar_bear_essence", () ->
            new EssenceItem(EntityType.POLAR_BEAR,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SLIME_ESSENCE = SUMMONER_CLASS_ITEMS.register("slime_essence", () ->
            new EssenceItem(EntityType.SLIME,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WITCH_ESSENCE = SUMMONER_CLASS_ITEMS.register("witch_essence", () ->
            new EssenceItem(EntityType.WITCH,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> GHAST_ESSENCE = SUMMONER_CLASS_ITEMS.register("ghast_essence", () ->
            new EssenceItem(EntityType.GHAST,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> SNOW_GOLEM_ESSENCE = SUMMONER_CLASS_ITEMS.register("snow_golem_essence", () ->
            new EssenceItem(EntityType.SNOW_GOLEM,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> ILLAGER_ESSENCE = SUMMONER_CLASS_ITEMS.register("illager_essence", () ->
            new EssenceItem(EntityType.VINDICATOR,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> PIGLIN_ESSENCE = SUMMONER_CLASS_ITEMS.register("piglin_essence", () ->
            new EssenceItem(EntityType.PIGLIN,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> WITHER_ESSENCE = SUMMONER_CLASS_ITEMS.register("wither_essence", () ->
            new EssenceItem(EntityType.WITHER,
                    new Item.Properties().tab(SummonerCoreItemGroup.SUMMONER_CLASS_ITEM_GROUP)
            )
    );
}
