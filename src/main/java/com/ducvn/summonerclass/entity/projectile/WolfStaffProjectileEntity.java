package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.config.SummonerClassConfig;
import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedWolfEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedWolfArmor;
import com.ducvn.summonerclass.item.armor.basic.WolfArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.Random;

public class WolfStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public WolfStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public WolfStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.WOLF_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public WolfStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.WOLF_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.COOKED_BEEF.asItem();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    protected void onHitEntity(EntityRayTraceResult traceResult) {
        if (!level.isClientSide){
            if (traceResult.getEntity() instanceof LivingEntity && this.getOwner() != null
                    && this.getOwner() instanceof PlayerEntity){
                LivingEntity livingEntity = (LivingEntity) traceResult.getEntity();
                DamageSource damageSource = DamageSource.playerAttack((PlayerEntity) this.getOwner());
                livingEntity.hurt(damageSource, 1.0F);
                Random roll = new Random();
                int wolfNumber = 4 + bonusMinion;
                for (int num = 1; num <= wolfNumber; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedWolfEntity wolf = new SummonedWolfEntity(SummonerClassEntitiesRegister.SUMMONED_WOLF.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        wolf.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        wolf.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    wolf.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    wolf.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s wolf"));
                    if (witherHit){
                        wolf.setWitherHit();
                    }
                    if (poisonHit){
                        wolf.setPoisonHit();
                    }
                    if (fireHit){
                        wolf.setFireHit();
                    }
                    if (slownessHit){
                        wolf.setSlownessHit();
                    }
                    if (weaknessHit){
                        wolf.setWeaknessHit();
                    }
                    if (hasThorn){
                        wolf.setThorn();
                    }
                    if (isSupreme){
                        wolf.setSupreme();
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(), SummonerClassEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                            || isSupreme){
                        double bonusAttack = SummonerClassConfig.minion_damage.get();
                        if (hasAdvanceArmorSet((PlayerEntity) this.getOwner())){
                            bonusAttack = bonusAttack + 0.5D;
                        }
                        wolf = (SummonedWolfEntity) addBonusAttack(wolf, wolf.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * bonusAttack);
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_HEALTH.get(), 3)
                            || isSupreme){
                        wolf = (SummonedWolfEntity) addBonusHealth(wolf, wolf.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerClassConfig.minion_health.get());
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(), SummonerClassEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                            || isSupreme){
                        wolf.setExplode();
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                            || isSupreme){
                        wolf.setBuffMaster();
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_SPEED.get(), 1)
                            || isSupreme || hasFullArmorSet((PlayerEntity) this.getOwner())){
                        wolf = (SummonedWolfEntity) addBonusSpeed(wolf, wolf.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * SummonerClassConfig.minion_speed.get());
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                            || isSupreme){
                        wolf = (SummonedWolfEntity) addBonusKnockbackRes(wolf, SummonerClassConfig.minion_resistance.get());
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                            || isSupreme){
                        wolf.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerClassConfig.minion_invisible_duration.get()));
                    }
                    if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerClassEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                            || isSupreme){
                        wolf.setMagnetize();
                    }
                    if (roll.nextInt(100) < 3){
                        wolf.setEasterEgg();
                    }
                    level.addFreshEntity(wolf);
                }
            }
            this.remove();
        }
    }

    private boolean hasFullArmorSet(PlayerEntity player) {
        List<ItemStack> armorList = getArmorSet(player);
        boolean isCombined = true;
        boolean haveAtLeastOne = false;

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof ArmorItem)){
                return false;
            }
            if (!(stack.getItem() instanceof WolfArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof WolfArmor){
                haveAtLeastOne = true;
                if (EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                    return true;
                }
            }
        }
        if (isCombined && haveAtLeastOne){
            return true;
        }
        else {
            return false;
        }
    }
    private boolean hasAdvanceArmorSet(PlayerEntity player){
        List<ItemStack> armorList = getArmorSet(player);

        for (ItemStack stack : armorList){
            if (!(stack.getItem() instanceof AdvancedWolfArmor)){
                return false;
            }
        }
        return true;
    }
}
