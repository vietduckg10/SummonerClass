package com.ducvn.summonerclass.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SummonerClassConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> minion_wither_duration;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_wither_amplifier;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_poison_duration;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_poison_amplifier;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_fire_duration;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_slowness_duration;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_slowness_amplifier;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_weakness_duration;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_weakness_amplifier;
    public static final ForgeConfigSpec.ConfigValue<Double> minion_thorn_damage;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_increase;
    public static final ForgeConfigSpec.ConfigValue<Double> minion_damage;
    public static final ForgeConfigSpec.ConfigValue<Double> minion_health;
    public static final ForgeConfigSpec.ConfigValue<Double> minion_buff_chance;
    public static final ForgeConfigSpec.ConfigValue<Double> minion_speed;
    public static final ForgeConfigSpec.ConfigValue<Double> minion_resistance;
    public static final ForgeConfigSpec.ConfigValue<Double> minion_explode_range;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_invisible_duration;
    public static final ForgeConfigSpec.ConfigValue<Integer> minion_magnetic_range;

    static{
        BUILDER.push("Summoner Class Config");
        //Staff Enchantments
        minion_wither_duration = BUILDER.define("Minion Wither Duration (ticks)", 200);
        minion_wither_amplifier = BUILDER.define("Minion Wither Amplifier (0 = level 1)", 0);
        minion_poison_duration = BUILDER.define("Minion Poison Duration (ticks)", 200);
        minion_poison_amplifier = BUILDER.define("Minion Poison Amplifier (0 = level 1)", 0);
        minion_fire_duration = BUILDER.define("Minion Poison Duration (second)", 10);
        minion_slowness_duration = BUILDER.define("Minion Slowness Duration (ticks)", 200);
        minion_slowness_amplifier = BUILDER.define("Minion Slowness Amplifier (0 = level 1)", 0);
        minion_weakness_duration = BUILDER.define("Minion Weakness Duration (ticks)", 200);
        minion_weakness_amplifier = BUILDER.define("Minion Weakness Amplifier (0 = level 1)", 0);
        minion_thorn_damage = BUILDER.define("Minion Thorn Damage", 2.0);
        //Armor Enchantments
        minion_increase = BUILDER.define("Minion Increase Bonus", 1);
        minion_damage = BUILDER.define("Minion Damage Bonus (stat = base * (bonus + 1))", 0.5);
        minion_health = BUILDER.define("Minion Health Bonus (stat = base * (bonus + 1))", 0.5);
        minion_buff_chance = BUILDER.define("Minion Buff Chance", 0.5);
        minion_speed = BUILDER.define("Minion Speed Bonus (stat = base * (bonus + 1))", 0.5);
        minion_resistance = BUILDER.define("Minion Knockback Resistance Bonus", 0.5);
        minion_explode_range = BUILDER.define("Minion Explode Range (blocks)", 2.0);
        minion_invisible_duration = BUILDER.define("Minion Invisible Duration (ticks)", 12000);
        minion_magnetic_range = BUILDER.define("Minion Magnetic Range (blocks)", 2);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
