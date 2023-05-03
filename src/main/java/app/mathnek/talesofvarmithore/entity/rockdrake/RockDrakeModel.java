package app.mathnek.talesofvarmithore.entity.rockdrake;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.ToVBaseGeoModel;
import net.minecraft.resources.ResourceLocation;

public class RockDrakeModel extends ToVBaseGeoModel<RockDrakeEntity> {

    public RockDrakeModel() {
        super("rock_drake");
    }

    @Override
    public ResourceLocation getTextureLocation(RockDrakeEntity mob) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/rock_drake/rock_drake"+mob.getVariantType()+".png");
    }
}
