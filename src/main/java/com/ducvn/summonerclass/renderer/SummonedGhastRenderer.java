package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.entity.summonedmob.SummonedGhastEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedGhastRenderer extends GhastRenderer {
    public SummonedGhastRenderer(EntityRendererManager p_i46174_1_) {
        super(p_i46174_1_);
    }

    @Override
    protected void scale(GhastEntity ghastEntity, MatrixStack p_225620_2_, float p_225620_3_) {
        if (ghastEntity instanceof SummonedGhastEntity){
            if (((SummonedGhastEntity) ghastEntity).getEasterEgg()){
                p_225620_2_.scale(1.0F, 1.0F, 1.0F);
            }
            else {
                super.scale(ghastEntity, p_225620_2_, p_225620_3_);
            }
        }
        else {
            super.scale(ghastEntity, p_225620_2_, p_225620_3_);
        }
    }
}
