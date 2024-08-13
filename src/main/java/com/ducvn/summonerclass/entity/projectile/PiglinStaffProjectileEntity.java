package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.*;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedPiglinArmor;
import com.ducvn.summonerclass.item.armor.basic.PiglinArmor;
import com.ducvn.summonercoremod.config.SummonerCoreConfig;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import com.ducvn.summonercoremod.entity.projectile.IStaffProjectile;
import com.ducvn.summonercoremod.entity.projectile.StaffProjectileEntity;
import com.ducvn.summonercoremod.potion.SummonerCorePotionsRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.Random;

public class PiglinStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public PiglinStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public PiglinStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.PIGLIN_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public PiglinStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.PIGLIN_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.GOLD_INGOT.asItem();
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
                int piglinNumber = roll.nextInt(4) + 1 + bonusMinion;
                for (int num = 1; num <= piglinNumber; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    if (hasFullArmorSet((PlayerEntity) this.getOwner())
                            && roll.nextInt(100) < (hasAdvanceArmorSet((PlayerEntity) this.getOwner()) ? 10 : 5)){
                        SummonedPiglinBruteEntity piglin = new SummonedPiglinBruteEntity(SummonerClassEntitiesRegister.SUMMONED_PIGLIN_BRUTE.get(), level);
                        if (xAdjust == 0 && zAdjust == 0){
                            piglin.setPos(this.getOwner().position().x,
                                    this.getOwner().position().y + 3D,
                                    this.getOwner().position().z);
                        }
                        else {
                            piglin.setPos(this.getOwner().position().x + xAdjust,
                                    this.getOwner().position().y + yAdjust,
                                    this.getOwner().position().z + zAdjust);
                        }
                        piglin.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                        piglin.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s piglin"));
                        giveMinionEnchantment(piglin);
                        piglin.setItemInHand(Hand.MAIN_HAND, Items.GOLDEN_AXE.getDefaultInstance());
                        piglin.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                        level.addFreshEntity(piglin);
                    }
                    else {
                        SummonedPiglinEntity piglin = new SummonedPiglinEntity(SummonerClassEntitiesRegister.SUMMONED_PIGLIN.get(), level);
                        if (xAdjust == 0 && zAdjust == 0){
                            piglin.setPos(this.getOwner().position().x,
                                    this.getOwner().position().y + 3D,
                                    this.getOwner().position().z);
                        }
                        else {
                            piglin.setPos(this.getOwner().position().x + xAdjust,
                                    this.getOwner().position().y + yAdjust,
                                    this.getOwner().position().z + zAdjust);
                        }
                        piglin.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                        piglin.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s piglin"));
                        if (hasFullArmorSet((PlayerEntity) this.getOwner())){
                            ItemStack piglinCrossBow = Items.CROSSBOW.getDefaultInstance();
                            if (witherHit){
                                ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                                arrow.setCount(64);
                                piglin.setItemInHand(Hand.OFF_HAND,
                                        PotionUtils.setPotion(
                                                arrow,
                                                SummonerCorePotionsRegister.WITHER_POTION.get())
                                );
                                piglin.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                            }
                            if (poisonHit){
                                ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                                arrow.setCount(64);
                                piglin.setItemInHand(Hand.OFF_HAND,
                                        PotionUtils.setPotion(
                                                arrow,
                                                Potions.POISON)
                                );
                                piglin.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                            }
                            if (fireHit){
                                piglinCrossBow.enchant(Enchantments.MULTISHOT, 1);
                            }
                            if (slownessHit){
                                ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                                arrow.setCount(64);
                                piglin.setItemInHand(Hand.OFF_HAND,
                                        PotionUtils.setPotion(
                                                arrow,
                                                Potions.SLOWNESS)
                                );
                                piglin.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                            }
                            if (weaknessHit){
                                ItemStack arrow = Items.TIPPED_ARROW.getDefaultInstance();
                                arrow.setCount(64);
                                piglin.setItemInHand(Hand.OFF_HAND,
                                        PotionUtils.setPotion(
                                                arrow,
                                                Potions.WEAKNESS)
                                );
                                piglin.setDropChance(EquipmentSlotType.OFFHAND, 0.0f);
                            }
                            if (hasThorn){
                                piglin.setThorn();
                            }
                            if (isSupreme){
                                piglin.setSupreme();
                            }
                            if (hasMinionArmorEffect((PlayerEntity) this.getOwner(), SummonerCoreEnchantmentsRegister.MINION_DAMAGE.get(), 3)
                                    || isSupreme){
                                piglinCrossBow.enchant(Enchantments.QUICK_CHARGE, 1);
                            }
                            if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerCoreEnchantmentsRegister.MINION_HEALTH.get(), 3)
                                    || isSupreme){
                                piglin = (SummonedPiglinEntity) addBonusHealth(piglin, piglin.getAttribute(Attributes.MAX_HEALTH).getValue() * SummonerCoreConfig.minion_health.get());
                            }
                            if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerCoreEnchantmentsRegister.MINION_SELF_DESTRUCT.get(), 2)
                                    || isSupreme){
                                piglin.setExplode();
                            }
                            if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerCoreEnchantmentsRegister.MINION_MASTER_BUFF.get(), 2)
                                    || isSupreme){
                                piglin.setBuffMaster();
                            }
                            if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerCoreEnchantmentsRegister.MINION_SPEED.get(), 1)
                                    || isSupreme){
                                piglin = (SummonedPiglinEntity) addBonusSpeed(piglin, piglin.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * SummonerCoreConfig.minion_speed.get());
                            }
                            if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerCoreEnchantmentsRegister.MINION_KNOCKBACK_RESISTANCE.get(), 1)
                                    || isSupreme){
                                piglin = (SummonedPiglinEntity) addBonusKnockbackRes(piglin, SummonerCoreConfig.minion_resistance.get());
                            }
                            if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerCoreEnchantmentsRegister.MINION_INVISIBLE.get(), 0)
                                    || isSupreme){
                                piglin.addEffect(new EffectInstance(Effects.INVISIBILITY, SummonerCoreConfig.minion_invisible_duration.get()));
                            }
                            if (hasMinionArmorEffect((PlayerEntity) this.getOwner(),SummonerCoreEnchantmentsRegister.MINION_MAGNETIC.get(), 0)
                                    || isSupreme){
                                piglin.setMagnetize();
                            }
                            piglin.setItemInHand(Hand.MAIN_HAND, piglinCrossBow);
                        }
                        else {
                            piglin.setItemInHand(Hand.MAIN_HAND, Items.GOLDEN_AXE.getDefaultInstance());
                            giveMinionEnchantment(piglin);
                        }
                        piglin.setDropChance(EquipmentSlotType.MAINHAND, 0.0f);
                        level.addFreshEntity(piglin);
                    }
                }
                if (roll.nextInt(100) < 3){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedHoglinEntity hoglin = new SummonedHoglinEntity(SummonerClassEntitiesRegister.SUMMONED_HOGLIN.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        hoglin.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        hoglin.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    hoglin.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    hoglin.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s hoglin"));
                    giveMinionEnchantment(hoglin);
                    hoglin.setBaby(true);
                    this.getOwner().level.addFreshEntity(hoglin);
                }
                if (hasAdvanceArmorSet((PlayerEntity) this.getOwner()) && roll.nextInt(10) < 2){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedHoglinEntity hoglin = new SummonedHoglinEntity(SummonerClassEntitiesRegister.SUMMONED_HOGLIN.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        hoglin.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        hoglin.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    hoglin.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    hoglin.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s hoglin"));
                    giveMinionEnchantment(hoglin);
                    hoglin.isAdult();
                    level.addFreshEntity(hoglin);
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
            if (!(stack.getItem() instanceof PiglinArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof PiglinArmor){
                haveAtLeastOne = true;
                if (EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
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
            if (!(stack.getItem() instanceof AdvancedPiglinArmor)){
                return false;
            }
        }
        return true;
    }
}
