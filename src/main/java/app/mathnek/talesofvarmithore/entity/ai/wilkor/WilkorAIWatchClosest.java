package app.mathnek.talesofvarmithore.entity.ai.wilkor;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import app.mathnek.talesofvarmithore.entity.wilkor.EntityWilkor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class WilkorAIWatchClosest extends LookAtPlayerGoal {

    private final EntityWilkor dragon;

    public WilkorAIWatchClosest(PathfinderMob LivingEntityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
        super(LivingEntityIn, watchTargetClass, maxDistance);
        this.dragon = (EntityWilkor) LivingEntityIn;
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof EntityWilkor) {
            return false;
        }

        if (!this.dragon.shouldStopMovingIndependently()) {
            return true;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.mob instanceof EntityWilkor && !((EntityWilkor) this.mob).shouldStopMovingIndependently()) {
            return false;
        }
        return super.canContinueToUse();
    }
}
