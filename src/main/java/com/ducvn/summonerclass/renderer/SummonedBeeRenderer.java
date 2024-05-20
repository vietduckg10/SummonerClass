package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.entity.summonedmob.SummonedBeeEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedBeeRenderer extends BeeRenderer {
    public SummonedBeeRenderer(EntityRendererManager p_i46174_1_) {
        super(p_i46174_1_);
    }

    @Override
    protected void scale(BeeEntity beeEntity, MatrixStack p_225620_2_, float p_225620_3_) {
        if (beeEntity instanceof SummonedBeeEntity){
            if (((SummonedBeeEntity) beeEntity).getEasterEgg()){
                p_225620_2_.scale(4.0F, 4.0F, 4.0F);
            }
            else {
                super.scale(beeEntity, p_225620_2_, p_225620_3_);
            }
        }
        else {
            super.scale(beeEntity, p_225620_2_, p_225620_3_);
        }
        super.scale(beeEntity, p_225620_2_, p_225620_3_);
    }
}
