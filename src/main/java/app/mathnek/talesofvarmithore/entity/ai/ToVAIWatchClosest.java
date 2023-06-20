package app.mathnek.talesofvarmithore.entity.ai;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class ToVAIWatchClosest extends LookAtPlayerGoal {

    private final BaseEntityClass dragon;

    public ToVAIWatchClosest(PathfinderMob LivingEntityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
        super(LivingEntityIn, watchTargetClass, maxDistance);
        this.dragon = (BaseEntityClass) LivingEntityIn;
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof BaseEntityClass) {
            return false;
        }

        if(!this.dragon.shouldStopMovingIndependently()) {
            return true;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.mob instanceof BaseEntityClass && !((BaseEntityClass) this.mob).shouldStopMovingIndependently()) {
            return false;
        }
        return super.canContinueToUse();
    }
}
