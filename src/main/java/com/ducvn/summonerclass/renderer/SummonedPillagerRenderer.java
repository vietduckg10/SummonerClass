package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.entity.summonedmob.SummonedPillagerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedPillagerRenderer extends PillagerRenderer {
    public SummonedPillagerRenderer(EntityRendererManager p_i46174_1_) {
        super(p_i46174_1_);
    }

    @Override
    protected void scale(PillagerEntity pillager, MatrixStack p_225620_2_, float p_225620_3_) {
        if (pillager instanceof SummonedPillagerEntity){
            float f = 0.9375F;
            if (pillager.isBaby()) {
                f = (float)((double)f * 0.5D);
                this.shadowRadius = 0.25F;
                this.model.young = true;
            } else {
                this.shadowRadius = 0.5F;
            }
            p_225620_2_.scale(f, f, f);
        }
        else {
            super.scale(pillager, p_225620_2_, p_225620_3_);
        }
    }
}
