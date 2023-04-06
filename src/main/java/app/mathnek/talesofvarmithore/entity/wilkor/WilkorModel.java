package app.mathnek.talesofvarmithore.entity.wilkor;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WilkorModel extends AnimatedGeoModel<WilkorEntity> {
    @Override
    public ResourceLocation getModelLocation(WilkorEntity object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/wilkor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WilkorEntity object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/wilkor/wilkor.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WilkorEntity animatable) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/wilkor.animation.json");
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(WilkorEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
