package app.mathnek.talesofvarmithore.entity.wilkor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RenderWilkor extends GeoEntityRenderer<NewWilkor> {
    public RenderWilkor(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelWilkor());
        this.shadowRadius = 1f;
    }
}
