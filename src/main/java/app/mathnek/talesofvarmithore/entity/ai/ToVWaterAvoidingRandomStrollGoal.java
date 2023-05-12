package app.mathnek.talesofvarmithore.entity.ai;

import javax.annotation.Nullable;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class ToVWaterAvoidingRandomStrollGoal extends RandomStrollGoal {
   BaseEntityClass dragon;
   public static final float PROBABILITY = 0.001F;
   protected final float probability;

   public ToVWaterAvoidingRandomStrollGoal(BaseEntityClass dragon, double pSpeedModifier) {
      this(dragon, pSpeedModifier, 0.001F);
      this.dragon = dragon;
   }

   public ToVWaterAvoidingRandomStrollGoal(BaseEntityClass dragon, double pSpeedModifier, float pProbability) {
      super(dragon, pSpeedModifier);
      this.probability = pProbability;
      this.dragon = dragon;
   }

   public boolean canUse() {
      return this.dragon.shouldStopMovingIndependently() ? false : super.canUse();
   }

   @Nullable
   protected Vec3 getPosition() {
      if (this.mob.isInWaterOrBubble()) {
         Vec3 $$0 = LandRandomPos.getPos(this.mob, 15, 7);
         return $$0 == null ? super.getPosition() : $$0;
      } else {
         return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
      }
   }
}
