package app.mathnek.talesofvarmithore.entity.pupfish;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PupfishModel extends GeoModel<PupfishEntity> {

    @Override
    public ResourceLocation getModelResource(PupfishEntity object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/pupfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PupfishEntity object) {
        switch (object.getVariant()) {
            case 0:
            default:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/pupfish/pupfish_pink.png");
            case 1:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/pupfish/pupfish_orange.png");
            case 2:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/pupfish/pupfish_purple.png");
            case 3:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/pupfish/pupfish_blue.png");
            case 4:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/pupfish/pupfish_indigo.png");
            case 5:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/pupfish/pupfish_yellow.png");
            case 6:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/pupfish/pupfish_yellowgreen.png");
            case 7:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/pupfish/pupfish_strawberry.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(PupfishEntity animatable) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/pupfish.animation.json");
    }
}