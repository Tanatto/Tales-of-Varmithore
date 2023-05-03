package app.mathnek.talesofvarmithore.entity.rockdrake;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class RockDrakeRenderer extends GeoEntityRenderer<RockDrakeEntity> {
    public RockDrakeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RockDrakeModel());
        this.addLayer(new EquipmentLayer(this));
        this.shadowRadius = 1f;
    }


    @SuppressWarnings("rawtypes")
    public class EquipmentLayer extends GeoLayerRenderer<RockDrakeEntity> {
        private ResourceLocation LAYER=new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rock_drake/rock_drake_equipment.png");
        private static final ResourceLocation MODEL = new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/rock_drake.geo.json");


        public EquipmentLayer(IGeoRenderer<RockDrakeEntity> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, RockDrakeEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if(entityLivingBaseIn.isEquipped())
            {
                RenderType equipment = RenderType.armorCutoutNoCull(LAYER);
                this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, equipment, matrixStackIn, bufferIn,
                        bufferIn.getBuffer(equipment), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            }
        }
    }
}
