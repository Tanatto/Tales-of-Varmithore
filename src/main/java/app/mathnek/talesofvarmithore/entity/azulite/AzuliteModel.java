package app.mathnek.talesofvarmithore.entity.azulite;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.ToVBaseGeoModel;
import app.mathnek.talesofvarmithore.entity.wilkor.WilkorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AzuliteModel extends ToVBaseGeoModel<AzuliteEntity> {

    public AzuliteModel() {
        super("azulite");
    }

    @Override
    public ResourceLocation getTextureLocation(AzuliteEntity mob) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/azulite/azulite"+mob.getVariantType()+".png");
    }
}
