package app.mathnek.talesofvarmithore.entity.twintail;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwinTailModel extends AnimatedGeoModel<TwinTailEntity> {

    @Override
    public ResourceLocation getModelLocation(TwinTailEntity object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/twintail.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TwinTailEntity object) {
        switch (object.getVariant()) {
            case 0:
            default:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake.png");
            case 1:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_blue.png");
            case 2:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_black.png");
            case 3:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_bluegreen.png");
            case 4:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_brown.png");
            case 5:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_green.png");
            case 6:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_grey.png");
            case 7:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_purple.png");
            case 8:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_red.png");
            case 9:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_yellow.png");
            case 11:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/depredblue.png");
            case 12:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/wholeblack.png");
            case 13:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/wholered.png");
            case 14:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_ice.png");
            case 15:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/twintail/drake_bluedude.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TwinTailEntity animatable) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/twintail.animation.json");
    }
}