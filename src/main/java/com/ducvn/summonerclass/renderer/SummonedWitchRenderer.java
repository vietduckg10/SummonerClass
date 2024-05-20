package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.entity.summonedmob.SummonedWitchEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitchRenderer;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedWitchRenderer extends WitchRenderer {
    public SummonedWitchRenderer(EntityRendererManager p_i46174_1_) {
        super(p_i46174_1_);
    }

    @Override
    public void render(WitchEntity witch, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        if (witch instanceof SummonedWitchEntity){
            if (((SummonedWitchEntity) witch).getEasterEgg()){
                matrixStack.translate(0.0D, 0.55D, 0.0D);
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(43.0F));
            }
        }
        super.render(witch, p_225623_2_, p_225623_3_, matrixStack, p_225623_5_, p_225623_6_);
    }
}
