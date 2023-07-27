package app.mathnek.talesofvarmithore.entity.ai.wilkor;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import app.mathnek.talesofvarmithore.entity.wilkor.EntityWilkor;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class WilkorAIWander extends Goal {
    private final EntityWilkor wolf;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private final double speed;
    private int executionChance;
    private boolean mustUpdate;

    public WilkorAIWander(EntityWilkor creatureIn, double speedIn) {
        this(creatureIn, speedIn, 20);
    }

    public WilkorAIWander(EntityWilkor creatureIn, double speedIn, int chance) {
        this.wolf = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setFlags(EnumSet.of(Flag.MOVE));

    }

    @Override
    public boolean canUse() {
        if (wolf.shouldStopMovingIndependently()) {
            return false;
        }
        if (!this.mustUpdate) {
            if (this.wolf.getRandom().nextInt(executionChance) != 0) {
                return false;
            }
        }
        Vec3 Vector3d = DefaultRandomPos.getPos(this.wolf, 10, 7);
        if (Vector3d == null) {
            return false;
        } else {
            this.xPosition = Vector3d.x;
            this.yPosition = Vector3d.y;
            this.zPosition = Vector3d.z;
            this.mustUpdate = false;

            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.wolf.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.wolf.getNavigation().moveTo(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    public void makeUpdate() {
        this.mustUpdate = true;
    }

    public void setExecutionChance(int newchance) {
        this.executionChance = newchance;
    }
}