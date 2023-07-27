package app.mathnek.talesofvarmithore.entity.twintail;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TwinTailRenderer extends GeoEntityRenderer<TwinTailEntity> {
    public TwinTailRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TwinTailModel());
        this.addLayer(new TwinTailLayer(this));
        this.shadowRadius = 1f;
    }

    @Override
    public RenderType getRenderType(TwinTailEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        if (animatable.isBaby()) {
            stack.scale(0.4F, 0.4F, 0.4F);
        } else {
            //stack.scale(1F, 1F, 1F);
        }
        //return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
