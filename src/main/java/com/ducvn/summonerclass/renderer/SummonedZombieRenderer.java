package com.ducvn.summonerclass.renderer;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.entity.summonedmob.SummonedZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SummonedZombieRenderer extends ZombieRenderer {
    private static final ResourceLocation MATTY_ZOMBIE_LOCATION = new ResourceLocation(SummonerClassMod.MODID + ":textures/entity/matty_zombie.png");
    public SummonedZombieRenderer(EntityRendererManager p_i46127_1_) {
        super(p_i46127_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(ZombieEntity zombie) {
        if (zombie instanceof SummonedZombieEntity){
            if (((SummonedZombieEntity) zombie).getEasterEgg()){
                return MATTY_ZOMBIE_LOCATION;
            }
        }
        return super.getTextureLocation(zombie);
    }
}
