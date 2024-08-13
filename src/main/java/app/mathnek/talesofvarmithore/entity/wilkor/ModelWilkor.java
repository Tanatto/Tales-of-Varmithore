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
        return switch (object.getVariant()) {
            default -> new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor.png");
            case 1 -> new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_brown.png");
            case 2 -> new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_dark.png");
            case 3 -> new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_grey.png");
            case 4 -> new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_redsand.png");
            case 5 -> new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor_sand.png");
        };
    }

    @Override
    public ResourceLocation getAnimationResource(NewWilkor animatable) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/wilkor.animation.json");
    }
}
