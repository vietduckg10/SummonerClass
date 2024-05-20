package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.entity.summonedmob.SummonedWitherEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitherRenderer;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedWitherRenderer extends WitherRenderer {
    private static final ResourceLocation WITHER_INVULNERABLE_LOCATION = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation WITHER_LOCATION = new ResourceLocation("minecraft:textures/entity/wither/wither.png");
    private static final ResourceLocation PINKY_LOCATION = new ResourceLocation(SummonerClassMod.MODID + ":textures/entity/pink_wither.png");
    public SummonedWitherRenderer(EntityRendererManager p_i46174_1_) {
        super(p_i46174_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(WitherEntity wither) {
        int i = wither.getInvulnerableTicks();
        ResourceLocation defaultTexture = WITHER_LOCATION;
        if (wither instanceof SummonedWitherEntity){
            if (((SummonedWitherEntity) wither).getEasterEgg()){
                defaultTexture = PINKY_LOCATION;
            }
        }
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? WITHER_INVULNERABLE_LOCATION : defaultTexture;
    }
}
