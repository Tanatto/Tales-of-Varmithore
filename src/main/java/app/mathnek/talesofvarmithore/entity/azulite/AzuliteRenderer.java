package app.mathnek.talesofvarmithore.entity.azulite;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AzuliteRenderer extends GeoEntityRenderer<AzuliteEntity> {
    public AzuliteRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AzuliteModel());
        this.shadowRadius = 1f;
    }

    @Override
    public RenderType getRenderType(AzuliteEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

}
