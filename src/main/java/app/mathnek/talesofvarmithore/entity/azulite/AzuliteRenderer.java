package app.mathnek.talesofvarmithore.entity.azulite;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.wilkor.WilkorEntity;
import app.mathnek.talesofvarmithore.entity.wilkor.WilkorModel;
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

}
