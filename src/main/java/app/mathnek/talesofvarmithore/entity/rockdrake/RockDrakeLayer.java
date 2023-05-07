package app.mathnek.talesofvarmithore.entity.rockdrake;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class RockDrakeLayer extends GeoLayerRenderer<RockDrakeEntity> {

    private static final ResourceLocation LAYER = new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/equipment.png");
    private static final ResourceLocation MODEL = new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/rockdrake.geo.json");


    public RockDrakeLayer(IGeoRenderer<?> entityRendererIn) {
        super((IGeoRenderer<RockDrakeEntity>) entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, RockDrakeEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType cameo = RenderType.armorCutoutNoCull(LAYER);
        matrixStackIn.pushPose();
        matrixStackIn.scale(1.0f, 1.0f, 1.0f);
        matrixStackIn.translate(0.0d, 0.0d, 0.0d);
        if (entityLivingBaseIn.isSaddled()) {
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, cameo, matrixStackIn, bufferIn,
                    bufferIn.getBuffer(cameo), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
        matrixStackIn.popPose();
    }
}
