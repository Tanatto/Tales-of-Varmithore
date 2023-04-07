package app.mathnek.talesofvarmithore.entity;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.azulite.AzuliteEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ToVBaseGeoModel<T extends IAnimatable> extends AnimatedGeoModel<T> {

    String path;
    protected ToVBaseGeoModel(String name) {
        path=name;
    }


    @Override
    public ResourceLocation getModelLocation(T object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "geo/"+path+".geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(T object) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/"+path+"/"+path+".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "animations/"+path+".animation.json");
    }



}
