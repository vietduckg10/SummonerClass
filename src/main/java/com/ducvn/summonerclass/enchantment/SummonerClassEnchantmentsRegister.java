package com.ducvn.summonerclass.enchantment;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.enchantment.armor.ArmorEnchantment;
import com.ducvn.summonerclass.enchantment.staff.*;
import com.ducvn.summonerclass.item.staff.StaffItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonerClassEnchantmentsRegister {
    public static final DeferredRegister<Enchantment> SUMMONER_ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, SummonerClassMod.MODID);
    public static void init(IEventBus bus) {
        SUMMONER_ENCHANTMENTS.register(bus);
    }

    public static final EnchantmentType SUMMONER_STAFF = EnchantmentType.create("summoner_staff", (item) ->{
        return item instanceof StaffItem;
    });
    //staff
    public static RegistryObject<Enchantment> MINION_WITHER =
            SUMMONER_ENCHANTMENTS.register("minion_wither",
                    () -> new StaffEnchantment(Enchantment.Rarity.UNCOMMON,
                            SUMMONER_STAFF, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND));
    public static RegistryObject<Enchantment> MINION_POISON =
            SUMMONER_ENCHANTMENTS.register("minion_poison",
                    () -> new StaffEnchantment(Enchantment.Rarity.UNCOMMON,
                            SUMMONER_STAFF, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND));
    public static RegistryObject<Enchantment> MINION_FIRE =
            SUMMONER_ENCHANTMENTS.register("minion_fire",
                    () -> new StaffEnchantment(Enchantment.Rarity.UNCOMMON,
                            SUMMONER_STAFF, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND));
    public static RegistryObject<Enchantment> MINION_SLOWNESS =
            SUMMONER_ENCHANTMENTS.register("minion_slowness",
                    () -> new StaffEnchantment(Enchantment.Rarity.UNCOMMON,
                            SUMMONER_STAFF, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND));
    public static RegistryObject<Enchantment> MINION_WEAKNESS =
            SUMMONER_ENCHANTMENTS.register("minion_weakness",
                    () -> new StaffEnchantment(Enchantment.Rarity.UNCOMMON,
                            SUMMONER_STAFF, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND));
    public static RegistryObject<Enchantment> MINION_THORN =
            SUMMONER_ENCHANTMENTS.register("minion_thorn",
                    () -> new StaffEnchantment(Enchantment.Rarity.UNCOMMON,
                            SUMMONER_STAFF, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND));
    public static RegistryObject<Enchantment> MINION_SUPREME =
            SUMMONER_ENCHANTMENTS.register("minion_supreme",
                    () -> new StaffEnchantment(Enchantment.Rarity.RARE,
                            SUMMONER_STAFF, EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND));
    //armor
    public static RegistryObject<Enchantment> MINION_INCREASE = SUMMONER_ENCHANTMENTS.register("minion_increase",
            () -> new ArmorEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentType.ARMOR,
                    EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET));
    public static RegistryObject<Enchantment> MINION_COMBINE = SUMMONER_ENCHANTMENTS.register("minion_combine",
            () -> new ArmorEnchantment(Enchantment.Rarity.RARE, EnchantmentType.ARMOR,
                    EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET));
    public static RegistryObject<Enchantment> MINION_DAMAGE = SUMMONER_ENCHANTMENTS.register("minion_damage",
            () -> new ArmorEnchantment(Enchantment.Rarity.UNCOMMON,
                    EnchantmentType.ARMOR_HEAD, EquipmentSlotType.HEAD));
    public static RegistryObject<Enchantment> MINION_HEALTH = SUMMONER_ENCHANTMENTS.register("minion_health",
            () -> new ArmorEnchantment(Enchantment.Rarity.UNCOMMON,
                    EnchantmentType.ARMOR_HEAD, EquipmentSlotType.HEAD));
    public static RegistryObject<Enchantment> MINION_SELF_DESTRUCT = SUMMONER_ENCHANTMENTS.register("minion_self_destruct",
            () -> new ArmorEnchantment(Enchantment.Rarity.UNCOMMON,
                    EnchantmentType.ARMOR_CHEST, EquipmentSlotType.CHEST));
    public static RegistryObject<Enchantment> MINION_MASTER_BUFF = SUMMONER_ENCHANTMENTS.register("minion_master_buff",
            () -> new ArmorEnchantment(Enchantment.Rarity.UNCOMMON,
                    EnchantmentType.ARMOR_CHEST, EquipmentSlotType.CHEST));
    public static RegistryObject<Enchantment> MINION_SPEED = SUMMONER_ENCHANTMENTS.register("minion_speed",
            () -> new ArmorEnchantment(Enchantment.Rarity.UNCOMMON,
                    EnchantmentType.ARMOR_LEGS, EquipmentSlotType.LEGS));
    public static RegistryObject<Enchantment> MINION_KNOCKBACK_RESISTANCE = SUMMONER_ENCHANTMENTS.register("minion_knockback_resistance",
            () -> new ArmorEnchantment(Enchantment.Rarity.UNCOMMON,
                    EnchantmentType.ARMOR_LEGS, EquipmentSlotType.LEGS));
    public static RegistryObject<Enchantment> MINION_INVISIBLE = SUMMONER_ENCHANTMENTS.register("minion_invisible",
            () -> new ArmorEnchantment(Enchantment.Rarity.UNCOMMON,
                    EnchantmentType.ARMOR_FEET, EquipmentSlotType.FEET));
    public static RegistryObject<Enchantment> MINION_MAGNETIC = SUMMONER_ENCHANTMENTS.register("minion_magnetic",
            () -> new ArmorEnchantment(Enchantment.Rarity.UNCOMMON,
                    EnchantmentType.ARMOR_FEET, EquipmentSlotType.FEET));
}
