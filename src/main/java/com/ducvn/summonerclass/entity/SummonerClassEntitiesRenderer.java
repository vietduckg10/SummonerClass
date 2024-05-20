package com.ducvn.summonerclass.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class SummonerClassEntitiesRenderer {
    public static void registerEntityRenders(){
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.BEE_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.ZOMBIE_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SKELETON_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.CREEPER_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.ENDERMAN_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.WITHER_SKELETON_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SPIDER_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.BLAZE_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.IRON_GOLEM_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.WOLF_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.PHANTOM_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.GUARDIAN_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.POLAR_BEAR_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SLIME_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.WITCH_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.GHAST_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.SNOW_GOLEM_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.ILLAGER_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.PIGLIN_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(SummonerClassEntitiesRegister.WITHER_STAFF_PROJECTILE.get(), (rendererManager) ->
                new SpriteRenderer<>(rendererManager, renderer));
    }

}
