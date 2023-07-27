package app.mathnek.talesofvarmithore.entity.twintail.egg;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.DragonEggBase;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwinTailEggModel extends AnimatedGeoModel<DragonEggBase> {

    @Override
    public ResourceLocation getModelLocation(DragonEggBase entity) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/eggs/twintail_egg.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DragonEggBase entity) {
        return entity.getTextureLocation(entity);

    }

    @Override
    public ResourceLocation getAnimationFileLocation(DragonEggBase entity) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/eggs/twintail_egg.animation.json");
    }

    @Override
    public void setLivingAnimations(DragonEggBase entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
