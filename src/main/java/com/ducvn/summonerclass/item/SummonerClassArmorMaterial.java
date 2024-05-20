package com.ducvn.summonerclass.item;

import com.ducvn.summonerclass.SummonerClassMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum SummonerClassArmorMaterial implements IArmorMaterial {
    BEE("bee", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.HONEYCOMB);
            }),
    ADVANCED_BEE("advanced_bee", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.HONEYCOMB);
            }),
    ZOMBIE("zombie", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.ROTTEN_FLESH);
            }),
    ADVANCED_ZOMBIE("advanced_zombie", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.ROTTEN_FLESH);
            }),
    SKELETON("skeleton", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.BONE);
            }),
    ADVANCED_SKELETON("advanced_skeleton", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.BONE);
            }),
    CREEPER("creeper", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.GUNPOWDER);
            }),
    ADVANCED_CREEPER("advanced_creeper", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.GUNPOWDER);
            }),
    ENDERMAN("enderman", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.ENDER_PEARL);
            }),
    ADVANCED_ENDERMAN("advanced_enderman", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.ENDER_PEARL);
            }),
    WITHER_SKELETON("wither_skeleton", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.WITHER_SKELETON_SKULL);
            }),
    ADVANCED_WITHER_SKELETON("advanced_wither_skeleton", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.WITHER_SKELETON_SKULL);
            }),
    SPIDER("spider", 30, new int[]{2, 6, 7, 2}, 10,
    SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
        return Ingredient.of(Items.SPIDER_EYE);
    }),
    ADVANCED_SPIDER("advanced_spider", 35, new int[]{3, 6, 7, 2}, 15,
    SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
        return Ingredient.of(Items.SPIDER_EYE);
    }),
    BLAZE("blaze", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.BLAZE_ROD);
            }),
    ADVANCED_BLAZE("advanced_blaze", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.BLAZE_ROD);
            }),
    IRON_GOLEM("iron_golem", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.IRON_INGOT);
            }),
    ADVANCED_IRON_GOLEM("advanced_iron_golem", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.IRON_INGOT);
            }),
    WOLF("wolf", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.COOKED_BEEF);
            }),
    ADVANCED_WOLF("advanced_wolf", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.COOKED_BEEF);
            }),
    PHANTOM("phantom", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.PHANTOM_MEMBRANE);
            }),
    ADVANCED_PHANTOM("advanced_phantom", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.PHANTOM_MEMBRANE);
            }),
    GUARDIAN("guardian", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.PRISMARINE_SHARD);
            }),
    ADVANCED_GUARDIAN("advanced_guardian", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.PRISMARINE_SHARD);
            }),
    POLAR_BEAR("polar_bear", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.COOKED_COD);
            }),
    ADVANCED_POLAR_BEAR("advanced_polar_bear", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.COOKED_COD);
            }),
    SLIME("slime", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.SLIME_BLOCK);
            }),
    ADVANCED_SLIME("advanced_slime", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.SLIME_BLOCK);
            }),
    WITCH("witch", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.GLASS_BOTTLE);
            }),
    ADVANCED_WITCH("advanced_witch", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.GLASS_BOTTLE);
            }),
    GHAST("ghast", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.GHAST_TEAR);
            }),
    ADVANCED_GHAST("advanced_ghast", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.GHAST_TEAR);
            }),
    SNOW_GOLEM("snow_golem", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.SNOW_BLOCK);
            }),
    ADVANCED_SNOW_GOLEM("advanced_snow_golem", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.SNOW_BLOCK);
            }),
    ILLAGER("illager", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.LEATHER);
            }),
    ADVANCED_ILLAGER("advanced_illager", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.LEATHER);
            }),
    PIGLIN("piglin", 30, new int[]{2, 6, 7, 2}, 10,
            SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.GOLD_INGOT);
            }),
    ADVANCED_PIGLIN("advanced_piglin", 35, new int[]{3, 6, 7, 2}, 15,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0f, 0.0f,
            () -> {
                return Ingredient.of(Items.GOLD_INGOT);
            });

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairIngredient;

    private SummonerClassArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyValue<>(repairIngredient);
    }

    public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
        return HEALTH_PER_SLOT[p_200896_1_.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlotType p_200902_1_) {
        return this.slotProtections[p_200902_1_.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return SummonerClassMod.MODID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
