package com.ducvn.summonerclass.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedIronGolemRenderer extends IronGolemRenderer {
    public SummonedIronGolemRenderer(EntityRendererManager p_i46174_1_) {
        super(p_i46174_1_);
        this.addLayer(new SummonedIronGolemHeadLayer(this));
    }
}
