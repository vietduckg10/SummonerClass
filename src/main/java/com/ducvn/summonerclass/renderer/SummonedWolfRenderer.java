package com.ducvn.summonerclass.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedWolfRenderer extends WolfRenderer {
    public SummonedWolfRenderer(EntityRendererManager p_i46174_1_) {
        super(p_i46174_1_);
        this.addLayer(new SummonedWolfHeadLayer(this));
    }

}
