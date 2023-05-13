package app.mathnek.talesofvarmithore.entity.pupfish;

import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeLayer;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PupfishRenderer extends GeoEntityRenderer<PupfishEntity> {
    public PupfishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PupfishModel());
        this.shadowRadius = 1f;
    }

    @Override
    public RenderType getRenderType(PupfishEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        if(animatable.isBaby()) {
            stack.scale(0.4F, 0.4F, 0.4F);
        } else {
            //stack.scale(1F, 1F, 1F);
        }
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
