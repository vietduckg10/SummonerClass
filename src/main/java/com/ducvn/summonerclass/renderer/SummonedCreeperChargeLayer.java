package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.entity.summonedmob.SummonedCreeperEntity;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class SummonedCreeperChargeLayer extends EnergyLayer<SummonedCreeperEntity, CreeperModel<SummonedCreeperEntity>> {
    private static final ResourceLocation POWER_LOCATION = new ResourceLocation("minecraft:textures/entity/creeper/creeper_armor.png");
    private final CreeperModel<SummonedCreeperEntity> model = new CreeperModel<>(2.0F);
    private static final Map<DyeColor, float[]> COLORARRAY_BY_COLOR = Maps.newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((DyeColor p_200204_0_) -> {
        return p_200204_0_;
    }, SummonedCreeperChargeLayer::createColor)));
    protected static final RenderState.TransparencyState SUMMONED_TRANSPARENCY = new RenderState.TransparencyState("summoned_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.DST_COLOR, GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    protected static final RenderState.AlphaState DEFAULT_ALPHA = new RenderState.AlphaState(0.003921569F);
    protected static final RenderState.LightmapState LIGHTMAP = new RenderState.LightmapState(true);
    protected static final RenderState.OverlayState OVERLAY = new RenderState.OverlayState(true);
    protected static final RenderState.DiffuseLightingState DIFFUSE_LIGHTING = new RenderState.DiffuseLightingState(true);
    protected static final RenderState.CullState NO_CULL = new RenderState.CullState(false);
    protected static final RenderState.FogState BLACK_FOG = new RenderState.FogState("black_fog", () -> {
        RenderSystem.fog(2918, 0.0F, 0.0F, 0.0F, 1.0F);
        RenderSystem.enableFog();
    }, () -> {
        FogRenderer.levelFogColor();
        RenderSystem.disableFog();
    });

    public SummonedCreeperChargeLayer(IEntityRenderer<SummonedCreeperEntity, CreeperModel<SummonedCreeperEntity>> p_i50947_1_) {
        super(p_i50947_1_);
    }

    protected float xOffset(float p_225634_1_) {
        return p_225634_1_ * 0.01F;
    }

    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    protected EntityModel<SummonedCreeperEntity> model() {
        return this.model;
    }

    private static float[] createColor(DyeColor p_192020_0_) {
        float[] afloat = p_192020_0_.getTextureDiffuseColors();
        return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, SummonedCreeperEntity creeper, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (!creeper.isInvisible() && creeper.getEasterEgg()) {
            float f;
            float f1;
            float f2;
            int i = creeper.tickCount / 25 + creeper.getId();
            int j = DyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f3 = ((float)(creeper.tickCount % 25) + p_225628_7_) / 25.0F;
            float[] afloat1 = COLORARRAY_BY_COLOR.get(DyeColor.byId(k));
            float[] afloat2 = COLORARRAY_BY_COLOR.get(DyeColor.byId(l));
            f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
            f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
            f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;

            float i2 = (float)creeper.tickCount + p_225628_7_;
            EntityModel<SummonedCreeperEntity> entitymodel = this.model();
            entitymodel.prepareMobModel(creeper, p_225628_5_, p_225628_6_, p_225628_7_);
            this.getParentModel().copyPropertiesTo(entitymodel);
            IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(
                    RenderType.create("energy_swirl", DefaultVertexFormats.NEW_ENTITY,
                            7, 256, false, true,
                            RenderType.State.builder().setTextureState(
                                    new RenderState.TextureState(POWER_LOCATION, false, false))
                                    .setTexturingState(new RenderState.OffsetTexturingState(this.xOffset(i2), i2 * 0.01F))
                                    .setFogState(BLACK_FOG)
                                    .setTransparencyState(SUMMONED_TRANSPARENCY)
                                    .setDiffuseLightingState(DIFFUSE_LIGHTING)
                                    .setAlphaState(DEFAULT_ALPHA)
                                    .setCullState(NO_CULL).setLightmapState(LIGHTMAP)
                                    .setOverlayState(OVERLAY)
                                    .createCompositeState(false)));
            entitymodel.setupAnim(creeper, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
            entitymodel.renderToBuffer(p_225628_1_, ivertexbuilder, p_225628_3_, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
        }
        else {
            super.render(p_225628_1_, p_225628_2_, p_225628_3_, creeper,
                    p_225628_5_,  p_225628_6_,  p_225628_7_,
                    p_225628_8_,  p_225628_9_,  p_225628_10_);
        }
    }
}
