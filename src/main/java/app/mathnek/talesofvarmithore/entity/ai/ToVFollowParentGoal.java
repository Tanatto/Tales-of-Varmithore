package app.mathnek.talesofvarmithore.entity.ai;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.animal.Animal;

import javax.annotation.Nullable;

public class ToVFollowParentGoal extends FollowParentGoal {
    private final BaseEntityClass dragon;
    @Nullable
    private BaseEntityClass parent;
    private final double speedModifier;
    private int timeToRecalcPath;

    public ToVFollowParentGoal(BaseEntityClass pAnimal, double pSpeedModifier) {
        super(pAnimal, pSpeedModifier);
        this.dragon = pAnimal;
        this.speedModifier = pSpeedModifier;
    }

    public boolean canUse() {
        if (dragon.shouldStopMovingIndependently()) {
            return false;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.dragon.getNavigation().isDone();
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
            this.dragon.getNavigation().moveTo(this.parent, this.speedModifier);
        }
    }
}
