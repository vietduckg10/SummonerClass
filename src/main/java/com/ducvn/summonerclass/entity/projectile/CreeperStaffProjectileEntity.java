package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.enchantment.SummonerClassEnchantmentsRegister;
import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedCreeperEntity;
import com.ducvn.summonerclass.item.armor.advanced.AdvancedCreeperArmor;
import com.ducvn.summonerclass.item.armor.basic.CreeperArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.Random;

public class CreeperStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public CreeperStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public CreeperStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.CREEPER_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public CreeperStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.CREEPER_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.GUNPOWDER.asItem();
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
                int creeperNumber = 3 + bonusMinion;
                for (int num = 1; num <= creeperNumber; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedCreeperEntity creeper = new SummonedCreeperEntity(SummonerClassEntitiesRegister.SUMMONED_CREEPER.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        creeper.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        creeper.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    creeper.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    creeper.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s creeper"));
                    giveMinionEnchantment(creeper);
                    if (hasFullArmorSet((PlayerEntity) this.getOwner())
                            &&  roll.nextInt(10) < ((hasAdvanceArmorSet((PlayerEntity) this.getOwner())) ? 5 : 3)){
                        creeper.setCharged();
                        if (roll.nextInt(100) < 5){
                            creeper.setEasterEgg();
                        }
                    }
                    level.addFreshEntity(creeper);
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
            if (!(stack.getItem() instanceof CreeperArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerClassEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof CreeperArmor){
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
            if (!(stack.getItem() instanceof AdvancedCreeperArmor)){
                return false;
            }
        }
        return true;
    }
}
