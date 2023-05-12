package app.mathnek.talesofvarmithore.entity.ai;

import java.util.EnumSet;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ToVRandomLookAroundGoal extends Goal {
   private final BaseEntityClass dragon;
   private double relX;
   private double relZ;
   private int lookTime;

   public ToVRandomLookAroundGoal(BaseEntityClass pMob) {
      this.dragon = pMob;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      return this.dragon.getRandom().nextFloat() < 0.02F && !this.dragon.shouldStopMovingIndependently();
   }

   public boolean canContinueToUse() {
      return this.lookTime >= 0;
   }

   public void start() {
      double d0 = 6.283185307179586 * this.dragon.getRandom().nextDouble();
      this.relX = Math.cos(d0);
      this.relZ = Math.sin(d0);
      this.lookTime = 20 + this.dragon.getRandom().nextInt(20);
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void tick() {
      --this.lookTime;
      this.dragon.getLookControl().setLookAt(this.dragon.getX() + this.relX, this.dragon.getEyeY(), this.dragon.getZ() + this.relZ);
   }
}
