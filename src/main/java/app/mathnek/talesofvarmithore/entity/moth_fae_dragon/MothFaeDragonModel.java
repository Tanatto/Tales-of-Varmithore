package app.mathnek.talesofvarmithore.entity.moth_fae_dragon;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.azulite.AzuliteEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MothFaeDragonModel extends AnimatedGeoModel<MothFaeDragon> {

    @Override
    public ResourceLocation getModelLocation(MothFaeDragon object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/moth_fae_dragon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MothFaeDragon object) {
        /*switch (object.getVariant()) {
            case 0:
            default:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/overworld/firefae_base_1.png");
            case 1:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/overworld/firefae_base_2.png");
            case 2:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/overworld/firefae_base_3.png");
            case 3:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/overworld/firefae_base_snowbiomes.png");
            case 4:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_basenether.png");
            case 5:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_crimsonforest.png");
            case 6:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_end.png");
            case 7:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_soulsandvalley.png");
            case 8:
                return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_warpedforest.png");
        }*/
        return MothFaeDragonRender.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MothFaeDragon animatable) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/moth_fae_dragon.animation.json");
    }
}
