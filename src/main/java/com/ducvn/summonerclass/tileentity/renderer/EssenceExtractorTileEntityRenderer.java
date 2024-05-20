package com.ducvn.summonerclass.tileentity.renderer;

import com.ducvn.summonerclass.tileentity.EssenceExtractorTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class EssenceExtractorTileEntityRenderer extends TileEntityRenderer<EssenceExtractorTileEntity> {
    private Minecraft minecraft = Minecraft.getInstance();
    private float degrees;
    public EssenceExtractorTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
        degrees = 0.0f;
    }

    @Override
    public void render(EssenceExtractorTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (tileEntity.getItemForDisplay().equals(ItemStack.EMPTY) || tileEntity.getItemForDisplay().getItem().equals(Items.AIR)){
            return;
        }
        int lightLevel = getLightLevel(tileEntity.getLevel(), tileEntity.getBlockPos().above());

        renderItem(tileEntity.getItemForDisplay(), new double[] {0.5D, 1.3D, 0.5D}, Vector3f.YP.rotationDegrees(degrees++),
                matrixStack, buffer, partialTicks, combinedOverlay, lightLevel, 1.0F);
    }

    private void renderItem(ItemStack stack, double[] translation, Quaternion rotation, MatrixStack matrixStack, IRenderTypeBuffer buffer,
                            float partialTicks, int combinedOverlay, int lightLevel, float scale){
        matrixStack.pushPose();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.mulPose(rotation);
        matrixStack.scale(scale, scale, scale);

        IBakedModel model = minecraft.getItemRenderer().getModel(stack, null, null);
        minecraft.getItemRenderer().render(stack, ItemCameraTransforms.TransformType.GROUND, true, matrixStack, buffer, lightLevel, combinedOverlay, model);
        matrixStack.popPose();
    }

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getBrightness(LightType.BLOCK, pos);
        int sLight = world.getBrightness(LightType.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
