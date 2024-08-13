package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.item.armor.basic.BlazeArmor;
import com.ducvn.summonercoremod.config.SummonerCoreConfig;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.UUID;

public class SmallEffectFireballEntity extends SmallFireballEntity {
    private UUID master;
    private boolean witherHit = false;
    private boolean poisonHit = false;
    private boolean slownessHit = false;
    private boolean weaknessHit = false;
    public SmallEffectFireballEntity(EntityType<? extends SmallEffectFireballEntity> p_i50160_1_, World p_i50160_2_) {
        super(p_i50160_1_, p_i50160_2_);
    }

    public SmallEffectFireballEntity(World p_i1771_1_, LivingEntity p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_) {
        super(p_i1771_1_, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
    }

    public SmallEffectFireballEntity(World p_i1772_1_, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_) {
        super(p_i1772_1_, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
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
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        if (!level.isClientSide){
            if (hasFullArmorSet((PlayerEntity) ((ServerWorld) level).getEntity(master))){
                if (witherHit || poisonHit || slownessHit || weaknessHit){
                    AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
                    areaeffectcloudentity.setRadius(2.5F);
                    areaeffectcloudentity.setRadiusOnUse(-0.5F);
                    areaeffectcloudentity.setWaitTime(10);
                    areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
                    areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
                    if (witherHit){
                        areaeffectcloudentity.addEffect(new EffectInstance(
                                Effects.WITHER,
                                SummonerCoreConfig.minion_wither_duration.get(),
                                SummonerCoreConfig.minion_wither_amplifier.get()));
                    }
                    if (poisonHit){
                        areaeffectcloudentity.addEffect(new EffectInstance(
                                Effects.POISON,
                                SummonerCoreConfig.minion_poison_duration.get(),
                                SummonerCoreConfig.minion_poison_amplifier.get()));
                    }
                    if (slownessHit){
                        areaeffectcloudentity.addEffect(new EffectInstance(
                                Effects.MOVEMENT_SLOWDOWN,
                                SummonerCoreConfig.minion_slowness_duration.get(),
                                SummonerCoreConfig.minion_slowness_amplifier.get()));
                    }
                    if (weaknessHit){
                        areaeffectcloudentity.addEffect(new EffectInstance(
                                Effects.WEAKNESS,
                                SummonerCoreConfig.minion_weakness_duration.get(),
                                SummonerCoreConfig.minion_weakness_amplifier.get()));
                    }
                    this.level.addFreshEntity(areaeffectcloudentity);
                }
                else {
                    level.explode(null, this.getX(), this.getY(), this.getZ(), 3, Explosion.Mode.NONE);
                }
            }
        }
        super.onHitBlock(p_230299_1_);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult traceResult) {
        if (!level.isClientSide) {
            if (hasFullArmorSet((PlayerEntity) ((ServerWorld) level).getEntity(master))){
                if (witherHit || poisonHit || slownessHit || weaknessHit){
                    AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
                    areaeffectcloudentity.setRadius(2.5F);
                    areaeffectcloudentity.setRadiusOnUse(-0.5F);
                    areaeffectcloudentity.setWaitTime(10);
                    areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
                    areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
                    if (witherHit){
                        areaeffectcloudentity.addEffect(new EffectInstance(Effects.WITHER, 200));
                    }
                    if (poisonHit){
                        areaeffectcloudentity.addEffect(new EffectInstance(Effects.POISON, 200));
                    }
                    if (slownessHit){
                        areaeffectcloudentity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200));
                    }
                    if (weaknessHit){
                        areaeffectcloudentity.addEffect(new EffectInstance(Effects.WEAKNESS, 200));
                    }
                    this.level.addFreshEntity(areaeffectcloudentity);
                }
                else {
                    level.explode(null, this.getX(), this.getY(), this.getZ(), 3, Explosion.Mode.NONE);
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
                if (stack.getItem() instanceof BlazeArmor){
                    hasArmor = true;
                    if (EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
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
                    if (!(stack.getItem() instanceof BlazeArmor)
                            && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
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
}
