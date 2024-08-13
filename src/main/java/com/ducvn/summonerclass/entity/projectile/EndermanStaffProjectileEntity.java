package com.ducvn.summonerclass.entity.projectile;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedEndermanEntity;
import com.ducvn.summonerclass.item.armor.basic.EndermanArmor;
import com.ducvn.summonercoremod.enchantment.SummonerCoreEnchantmentsRegister;
import com.ducvn.summonercoremod.entity.projectile.IStaffProjectile;
import com.ducvn.summonercoremod.entity.projectile.StaffProjectileEntity;
import net.minecraft.block.Blocks;
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

public class EndermanStaffProjectileEntity extends StaffProjectileEntity implements IStaffProjectile {
    public EndermanStaffProjectileEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public EndermanStaffProjectileEntity(double x, double y, double z, World world) {
        super(SummonerClassEntitiesRegister.ENDERMAN_STAFF_PROJECTILE.get(), x, y, z, world);
    }

    public EndermanStaffProjectileEntity(LivingEntity entity, World world) {
        super(SummonerClassEntitiesRegister.ENDERMAN_STAFF_PROJECTILE.get(), entity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.ENDER_EYE.asItem();
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
                int numEnderman = roll.nextInt(2) + 1 + bonusMinion;
                for (int num = 1; num <= numEnderman; num++){
                    int xAdjust = roll.nextInt(3) - 1;
                    int yAdjust = roll.nextInt(3);
                    int zAdjust = roll.nextInt(3) - 1;
                    SummonedEndermanEntity enderman = new SummonedEndermanEntity(SummonerClassEntitiesRegister.SUMMONED_ENDERMAN.get(), level);
                    if (xAdjust == 0 && zAdjust == 0){
                        enderman.setPos(this.getOwner().position().x,
                                this.getOwner().position().y + 3D,
                                this.getOwner().position().z);
                    }
                    else {
                        enderman.setPos(this.getOwner().position().x + xAdjust,
                                this.getOwner().position().y + yAdjust,
                                this.getOwner().position().z + zAdjust);
                    }
                    enderman.setAttackTarget(livingEntity, (LivingEntity) this.getOwner());
                    enderman.setCustomName(new StringTextComponent(this.getOwner().getScoreboardName() + "'s enderman"));
                    giveMinionEnchantment(enderman);
                    if (hasFullArmorSet((PlayerEntity) this.getOwner())){
                        enderman.setImmuneToWater();
                    }
                    if (roll.nextInt(100) < 3){
                        if (roll.nextBoolean()){
                            enderman.setCarriedBlock(Blocks.END_PORTAL_FRAME.defaultBlockState());
                        }
                        else {
                            enderman.setCarriedBlock(Blocks.BEDROCK.defaultBlockState());
                        }
                    }
                    level.addFreshEntity(enderman);
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
            if (!(stack.getItem() instanceof EndermanArmor)
                    && !EnchantmentHelper.getEnchantments(stack).containsKey(SummonerCoreEnchantmentsRegister.MINION_COMBINE.get())){
                isCombined = false;
            }
        }
        for (ItemStack stack : armorList){
            if (stack.getItem() instanceof EndermanArmor){
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
}
