package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.entity.summonedmob.SummonedIronGolemEntity;
import com.ducvn.summonerclass.entity.summonedmob.SummonedSlimeEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedSlimeHeadLayer extends LayerRenderer<SlimeEntity, SlimeModel<SlimeEntity>> {
    public SummonedSlimeHeadLayer(IEntityRenderer<SlimeEntity, SlimeModel<SlimeEntity>> p_i50926_1_) {
        super(p_i50926_1_);
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, SlimeEntity slime, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (!slime.isInvisible() && slime instanceof SummonedSlimeEntity) {
            if (((SummonedSlimeEntity) slime).getEasterEgg()){
                p_225628_1_.pushPose();
                this.getParentModel().cube.translateAndRotate(p_225628_1_);
                float f = 0.625F;
                p_225628_1_.translate(0.1D, 0.9D, 0.05D);
                p_225628_1_.mulPose(Vector3f.YP.rotationDegrees(20.0F));
                p_225628_1_.mulPose(Vector3f.XP.rotationDegrees(-35.0F));
                p_225628_1_.mulPose(Vector3f.ZP.rotationDegrees(30.0F));
                p_225628_1_.scale(0.5F, -0.5F, -0.5F);
                ItemStack itemstack = new ItemStack(Blocks.PLAYER_HEAD);
                Minecraft.getInstance().getItemRenderer().renderStatic(slime, itemstack, ItemCameraTransforms.TransformType.HEAD, false, p_225628_1_, p_225628_2_, slime.level, p_225628_3_, LivingRenderer.getOverlayCoords(slime, 0.0F));
                p_225628_1_.popPose();
            }

        }
    }
}
