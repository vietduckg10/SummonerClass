package com.ducvn.summonerclass.screen;

import com.ducvn.summonerclass.SummonerClassMod;
import com.ducvn.summonerclass.container.EssenceExtractorContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EssenceExtractorScreen extends ContainerScreen<EssenceExtractorContainer> {
    private final ResourceLocation GUI = new ResourceLocation(SummonerClassMod.MODID,
            "textures/gui/essence_extractor_gui.png");

    public EssenceExtractorScreen(EssenceExtractorContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        this.imageHeight = 174;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.getXSize(), this.getYSize());

        int progressTracker = this.menu.getExtractProgress();
        this.blit(matrixStack,
                i + 80, j + 37, 177, 0, 17, progressTracker);
    }
}
