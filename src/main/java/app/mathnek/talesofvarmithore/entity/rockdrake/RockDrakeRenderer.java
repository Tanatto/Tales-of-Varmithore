package app.mathnek.talesofvarmithore.entity.rockdrake;

import app.mathnek.talesofvarmithore.entity.DragonEggBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class RockDrakeRenderer extends GeoEntityRenderer<RockDrakeEntity> {
    public RockDrakeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RockDrakeModel());
        this.addLayer(new RockDrakeLayer(this));
        this.shadowRadius = 1f;
    }

    @Override
    public RenderType getRenderType(RockDrakeEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        if(animatable.isBaby()) {
            stack.scale(0.4F, 0.4F, 0.4F);
        } else {
            //stack.scale(1F, 1F, 1F);
        }
        //return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
        return RenderType.entityCutoutNoCull(textureLocation);
    }
}
