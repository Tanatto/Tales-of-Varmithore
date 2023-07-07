package app.mathnek.talesofvarmithore.gui;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.EntitySaddleBase;
import app.mathnek.talesofvarmithore.util.MathB;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
        this.passEvents = false;
        this.dragon = this.getMenu().dragon;
    }

    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, DRAGON_INVENTORY_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        EntitySaddleBase abstractchesteddragon = this.dragon;
        if (abstractchesteddragon.hasChest()) {
            this.blit(pPoseStack, i + 79, j + 17, 0, this.imageHeight, abstractchesteddragon.getInventoryColumns() * 18, 54);
        }

        if (this.dragon.isSaddleable()) {
            this.blit(pPoseStack, i + 7, j + 35 - 18, 18, this.imageHeight + 54, 18, 18);
        }

        this.blit(pPoseStack, i + 7, j + 35, 36, this.imageHeight + 54, 18, 18);
        InventoryScreen.renderEntityInInventory(i + 51, j + 68, 8, MathB.clamp((float) (i + 51) - this.xMouse, -40.0F, 40.0F), MathB.clamp((float) (j + 75 - 50) - this.yMouse, -120.0F, 5.0F), this.dragon);
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.xMouse = (float) pMouseX;
        this.yMouse = (float) pMouseY;
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
