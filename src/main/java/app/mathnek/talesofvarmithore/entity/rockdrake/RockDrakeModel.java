package app.mathnek.talesofvarmithore.entity.rockdrake;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RockDrakeModel extends AnimatedGeoModel<RockDrakeEntity> {

    @Override
    public ResourceLocation getModelLocation(RockDrakeEntity object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/rockdrake.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RockDrakeEntity object) {
        switch (object.getVariant()) {
            case 0:
            default:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake.png");
            case 1:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_blue.png");
            case 2:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_black.png");
            case 3:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_bluegreen.png");
            case 4:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_brown.png");
            case 5:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_green.png");
            case 6:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_grey.png");
            case 7:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_purple.png");
            case 8:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_red.png");
            case 9:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_yellow.png");
            case 11:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/depredblue.png");
            case 12:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/wholeblack.png");
            case 13:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/wholered.png");
            case 14:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rockdrake/drake_bluedude.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RockDrakeEntity animatable) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/rockdrake.animation.json");
    }
}