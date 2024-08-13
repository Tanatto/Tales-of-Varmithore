package app.mathnek.talesofvarmithore.entity.wilkor;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RenderWilkor extends GeoEntityRenderer<NewWilkor> {
    public RenderWilkor(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelWilkor());
        this.shadowRadius = 1f;
    }
}
