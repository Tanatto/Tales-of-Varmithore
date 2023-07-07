package app.mathnek.talesofvarmithore.entity.azulite;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AzuliteModel extends AnimatedGeoModel<AzuliteEntity> {

    @Override
    public ResourceLocation getModelLocation(AzuliteEntity object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/azulite.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AzuliteEntity object) {
        switch (object.getVariant()) {
            case 0:
            default:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/azulite/azulite1.png");
            case 1:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/azulite/azulite2.png");
            case 2:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/azulite/azulite3.png");
            case 3:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/azulite/azulite4.png");
            case 4:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/azulite/azulite5.png");
            case 5:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/azulite/azulite6.png");
            case 6:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/azulite/azulite7.png");
            case 7:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/azulite/azulite8.png");
        }
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AzuliteEntity animatable) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/azulite.animation.json");
    }
}
