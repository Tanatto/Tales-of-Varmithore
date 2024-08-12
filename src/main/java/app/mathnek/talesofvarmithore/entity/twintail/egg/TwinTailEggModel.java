package app.mathnek.talesofvarmithore.entity.twintail.egg;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.DragonEggBase;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TwinTailEggModel extends GeoModel<DragonEggBase> {

    @Override
    public ResourceLocation getModelResource(DragonEggBase entity) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/eggs/twintail_egg.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DragonEggBase entity) {
        return entity.getTextureLocation(entity);

    }

    @Override
    public ResourceLocation getAnimationResource(DragonEggBase entity) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/eggs/twintail_egg.animation.json");
    }
}
