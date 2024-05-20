package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedSnowGolemArmor;
import com.ducvn.summonerclass.item.armor.basic.SnowGolemArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.*;

public class EffectSnowballEntity extends SnowballEntity {
    private UUID master;
    private boolean fireHit = false;
    private boolean witherHit = false;
    private boolean poisonHit = false;
    private boolean slownessHit = false;
    private boolean weaknessHit = false;

    public EffectSnowballEntity(EntityType<? extends SnowballEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public EffectSnowballEntity(World p_i1774_1_, LivingEntity p_i1774_2_) {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EffectSnowballEntity(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    private static List<Integer> badEffectId = new ArrayList<>(
            Arrays.asList(
                    2, 4, 9, 15, 17, 18, 19, 20
            )
    );

    public void setfireHit(){
        fireHit = true;
    }
    public void setWitherHit(){
        witherHit = true;
    }
    public void setPoisonHit(){
        poisonHit = true;
    }
    public void setSlownessHit(){
        slownessHit = true;
    }
    public void setWeaknessHit(){
        weaknessHit = true;
    }
    public void setMaster(UUID player){
        master = player;
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult traceResult) {
        if (!level.isClientSide) {
            if (hasFullArmorSet((PlayerEntity) ((ServerWorld) level).getEntity(master))){
                if (traceResult.getEntity() instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) traceResult.getEntity();
                    if (witherHit || poisonHit || slownessHit || weaknessHit){
                        if (witherHit){
                            livingEntity.addEffect(new EffectInstance(Effects.WITHER, 200));
                        }
                        if (poisonHit){
                            livingEntity.addEffect(new EffectInstance(Effects.POISON, 200));
                        }
                        if (slownessHit){
                            livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200));
                        }
                        if (weaknessHit){
                            livingEntity.addEffect(new EffectInstance(Effects.WEAKNESS, 200));
                        }
                    }
                    else {
                        if (fireHit){
                            livingEntity.setSecondsOnFire(10);
                        }
                    }
                    if (hasAdvanceArmorSet((PlayerEntity) ((ServerWorld) level).getEntity(master))){
                        Random roll = new Random();
                        int effectId = badEffectId.get(roll.nextInt(badEffectId.size()));
                        livingEntity.addEffect(new EffectInstance(Effect.byId(effectId), 300, 1));
                    }
                }
            }
        }
        super.onHitEntity(traceResult);
    }

    private boolean hasFullArmorSet(PlayerEntity player) {
        List<ItemStack> armorList = player.inventory.armor;
        boolean isCombined = false;
        boolean hasArmor = false;

        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof ArmorItem){
                if (stack.getItem() instanceof SnowGolemArmor){
                    hasArmor = true;
                    if (EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                        isCombined = true;
                    }
                }
            }
            else {
                return false;
            }
        }

        if (hasArmor){
            if (isCombined){
                return true;
            }
            else {
                for (ItemStack stack : armorList){
                    if (!(stack.getItem() instanceof SnowGolemArmor)
                            && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                        return false;
                    }
                }
            }
        }
        else {
            return false;
        }

        return true;
    }

    private boolean hasAdvanceArmorSet(PlayerEntity player){
        List<ItemStack> armorList = player.inventory.armor;

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof AdvancedSnowGolemArmor)){
                return false;
            }
        }
        return true;
    }
}
