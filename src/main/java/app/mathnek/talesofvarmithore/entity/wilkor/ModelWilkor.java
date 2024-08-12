package app.mathnek.talesofvarmithore.entity.wilkor;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ModelWilkor extends GeoModel<NewWilkor> {
    @Override
    public ResourceLocation getModelResource(NewWilkor object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/wilkor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NewWilkor object) {
        switch (object.getVariant()) {
            case 0:
            default:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor.png");
            case 1:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_brown.png");
            case 2:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_dark.png");
            case 3:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_grey.png");
            case 4:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_redsand.png");
            case 5:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_sand.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(NewWilkor animatable) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/wilkor.animation.json");
    }
}
