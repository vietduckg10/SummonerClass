package com.ducvn.summonerclass.renderer;

import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedBlazeRenderer extends BlazeRenderer {
    public SummonedBlazeRenderer(EntityRendererManager p_i46174_1_) {
        super(p_i46174_1_);
    }
}
