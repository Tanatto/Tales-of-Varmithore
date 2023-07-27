package app.mathnek.talesofvarmithore.entity.ai.wilkor;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import app.mathnek.talesofvarmithore.entity.wilkor.EntityWilkor;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;

import javax.annotation.Nullable;

public class WilkorFollowParentGoal extends FollowParentGoal {
    private final EntityWilkor wolf;
    @Nullable
    private EntityWilkor parent;
    private final double speedModifier;
    private int timeToRecalcPath;

    public WilkorFollowParentGoal(EntityWilkor pAnimal, double pSpeedModifier) {
        super(pAnimal, pSpeedModifier);
        this.wolf = pAnimal;
        this.speedModifier = pSpeedModifier;
    }

    public boolean canUse() {
        if (wolf.shouldStopMovingIndependently()) {
            return false;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.wolf.getNavigation().isDone();
    }

    public void start() {
        this.timeToRecalcPath = 0;
    }

    public void stop() {
        this.parent = null;
    }

    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.wolf.getNavigation().moveTo(this.parent, this.speedModifier);
        }
    }
}
