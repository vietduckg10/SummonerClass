package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.entity.SummonerClassEntitiesRegister;
import com.ducvn.summonerclass.entity.summonedmob.SummonedGhastEntity;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SummonedPiglinRenderer extends PiglinRenderer {
    private static final Map<EntityType<?>, ResourceLocation> resourceLocations = ImmutableMap.of(
            SummonerClassEntitiesRegister.SUMMONED_PIGLIN.get(), new ResourceLocation("minecraft:textures/entity/piglin/piglin.png"),
            SummonerClassEntitiesRegister.SUMMONED_PIGLIN_BRUTE.get(), new ResourceLocation("minecraft:textures/entity/piglin/piglin_brute.png"));

    public SummonedPiglinRenderer(EntityRendererManager p_i232472_1_, boolean p_i232472_2_) {
        super(p_i232472_1_, p_i232472_2_);
    }

    public SummonedPiglinRenderer(EntityRendererManager p_i232472_1_) {
        super(p_i232472_1_, false);
    }

    @Override
    public ResourceLocation getTextureLocation(MobEntity p_110775_1_) {
        ResourceLocation resourcelocation = resourceLocations.get(p_110775_1_.getType());
        if (resourcelocation == null) {
            throw new IllegalArgumentException("I don't know what texture to use for " + p_110775_1_.getType());
        } else {
            return resourcelocation;
        }
    }
}
