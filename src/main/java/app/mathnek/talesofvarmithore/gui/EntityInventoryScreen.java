package app.mathnek.talesofvarmithore.gui;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.EntitySaddleBase;
import app.mathnek.talesofvarmithore.util.MathB;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EntityInventoryScreen extends AbstractContainerScreen<EntityContainerMenu> {
    public static final ResourceLocation DRAGON_INVENTORY_LOCATION = new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/gui/guicrteature.png");
    public EntitySaddleBase dragon;
    public float xMouse;
    public float yMouse;

    public EntityInventoryScreen(EntityContainerMenu pMenu, Inventory pPlayerInventory, Component title) {
        super(pMenu, pPlayerInventory, title);
        this.dragon = this.getMenu().dragon;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, DRAGON_INVENTORY_LOCATION);
        int i2 = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(DRAGON_INVENTORY_LOCATION, i2, j, 0, 0, this.imageWidth, this.imageHeight);
        EntitySaddleBase abstractchesteddragon = this.dragon;
        if (abstractchesteddragon.hasChest()) {
            guiGraphics.blit(DRAGON_INVENTORY_LOCATION, i2 + 79, j + 17, 0, this.imageHeight, abstractchesteddragon.getInventoryColumns() * 18, 54);
        }

        if (this.dragon.isSaddleable()) {
            guiGraphics.blit(DRAGON_INVENTORY_LOCATION, i2 + 7, j + 35 - 18, 18, this.imageHeight + 54, 18, 18);
        }

        guiGraphics.blit(DRAGON_INVENTORY_LOCATION, i2 + 7, j + 35, 36, this.imageHeight + 54, 18, 18);
        //TODO: Uncomment and change Type
        //InventoryScreen.renderEntityInInventory(guiGraphics,i + 51, j + 68, 8, MathB.clamp((float) (i + 51) - this.xMouse, -40.0F, 40.0F), MathB.clamp((float) (j + 75 - 50) - this.yMouse, -120.0F, 5.0F), this.dragon);

    }


    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics);
        this.xMouse = (float) pMouseX;
        this.yMouse = (float) pMouseY;
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(guiGraphics, pMouseX, pMouseY);
    }
}
