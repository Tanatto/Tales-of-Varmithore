package app.mathnek.talesofvarmithore.entity.ai.wilkor;

import app.mathnek.talesofvarmithore.entity.wilkor.EntityWilkor;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class WilkorRandomLookAroundGoal extends Goal {
    private final EntityWilkor wolf;
    private double relX;
    private double relZ;
    private int lookTime;

    public WilkorRandomLookAroundGoal(EntityWilkor pMob) {
        this.wolf = pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return this.wolf.getRandom().nextFloat() < 0.02F && !this.wolf.shouldStopMovingIndependently();
    }

    public boolean canContinueToUse() {
        return this.lookTime >= 0;
    }

    public void start() {
        double d0 = 6.283185307179586 * this.wolf.getRandom().nextDouble();
        this.relX = Math.cos(d0);
        this.relZ = Math.sin(d0);
        this.lookTime = 20 + this.wolf.getRandom().nextInt(20);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        --this.lookTime;
        this.wolf.getLookControl().setLookAt(this.wolf.getX() + this.relX, this.wolf.getEyeY(), this.wolf.getZ() + this.relZ);
    }
}
