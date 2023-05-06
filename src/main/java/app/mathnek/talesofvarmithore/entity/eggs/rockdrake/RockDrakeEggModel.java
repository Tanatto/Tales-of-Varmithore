package app.mathnek.talesofvarmithore.entity.eggs.rockdrake;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RockDrakeEggModel extends AnimatedGeoModel<RockDrakeEgg> {

    @Override
    public ResourceLocation getModelLocation(RockDrakeEgg entity) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/eggs/rockdrake_egg.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RockDrakeEgg entity) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/eggs/rockdrake_egg.png");

    }

    @Override
    public ResourceLocation getAnimationFileLocation(RockDrakeEgg entity) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/eggs/rockdrake_egg.animation.json");
    }

    @Override
    public void setLivingAnimations(RockDrakeEgg entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}