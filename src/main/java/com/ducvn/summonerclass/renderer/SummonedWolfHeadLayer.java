package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.entity.summonedmob.SummonedWolfEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedWolfHeadLayer extends LayerRenderer<WolfEntity, WolfModel<WolfEntity>> {
    public SummonedWolfHeadLayer(IEntityRenderer<WolfEntity, WolfModel<WolfEntity>> p_i50926_1_) {
        super(p_i50926_1_);
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, WolfEntity wolf, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (wolf instanceof SummonedWolfEntity) {
            if (((SummonedWolfEntity) wolf).getEasterEgg()){
                p_225628_1_.pushPose();
                this.getParentModel().head.translateAndRotate(p_225628_1_);
                p_225628_1_.translate(0.09D, -0.1D, -0.45D);
                p_225628_1_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                p_225628_1_.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                p_225628_1_.scale(0.5F, 0.5F, 0.5F);
                ItemStack itemstack = new ItemStack(Items.BONE);
                Minecraft.getInstance().getItemRenderer().renderStatic(wolf, itemstack, ItemCameraTransforms.TransformType.HEAD, false, p_225628_1_, p_225628_2_, wolf.level, p_225628_3_, LivingRenderer.getOverlayCoords(wolf, 0.0F));
                p_225628_1_.popPose();
            }

        }
    }
}
