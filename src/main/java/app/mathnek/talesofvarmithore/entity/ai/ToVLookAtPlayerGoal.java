package app.mathnek.talesofvarmithore.entity.ai;

import java.util.EnumSet;
import javax.annotation.Nullable;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class ToVLookAtPlayerGoal extends Goal {
   public static final float DEFAULT_PROBABILITY = 0.02F;
   protected final BaseEntityClass dragon;
   @Nullable
   protected Entity lookAt;
   protected final float lookDistance;
   private int lookTime;
   protected final float probability;
   private final boolean onlyHorizontal;
   protected final Class lookAtType;
   protected final TargetingConditions lookAtContext;

   public ToVLookAtPlayerGoal(BaseEntityClass pMob, Class pLookAtType, float pLookDistance) {
      this(pMob, pLookAtType, pLookDistance, 0.02F);
   }

   public ToVLookAtPlayerGoal(BaseEntityClass pMob, Class pLookAtType, float pLookDistance, float pProbability) {
      this(pMob, pLookAtType, pLookDistance, pProbability, false);
   }

   public ToVLookAtPlayerGoal(BaseEntityClass pMob, Class pLookAtType, float pLookDistance, float pProbability, boolean pOnlyHorizontal) {
      this.dragon = pMob;
      this.lookAtType = pLookAtType;
      this.lookDistance = pLookDistance;
      this.probability = pProbability;
      this.onlyHorizontal = pOnlyHorizontal;
      this.setFlags(EnumSet.of(Flag.LOOK));
      if (pLookAtType == Player.class) {
         this.lookAtContext = TargetingConditions.forNonCombat().range((double)pLookDistance).selector((p_25531_) -> {
            return EntitySelector.notRiding(pMob).test(p_25531_);
         });
      } else {
         this.lookAtContext = TargetingConditions.forNonCombat().range((double)pLookDistance);
      }

   }

   public boolean canUse() {
      if (this.dragon.getRandom().nextFloat() >= this.probability) {
         return false;
      } else {
         if (this.dragon.getTarget() != null) {
            this.lookAt = this.dragon.getTarget();
         }

         if (this.lookAtType == Player.class) {
            this.lookAt = this.dragon.level.getNearestPlayer(this.lookAtContext, this.dragon, this.dragon.getX(), this.dragon.getEyeY(), this.dragon.getZ());
         } else {
            this.lookAt = this.dragon.level.getNearestEntity(this.dragon.level.getEntitiesOfClass(this.lookAtType, this.dragon.getBoundingBox().inflate((double)this.lookDistance, 3.0, (double)this.lookDistance), (p_148124_) -> {
               return true;
            }), this.lookAtContext, this.dragon, this.dragon.getX(), this.dragon.getEyeY(), this.dragon.getZ());
         }

         return this.lookAt != null && !this.dragon.shouldStopMovingIndependently();
      }
   }

   public boolean canContinueToUse() {
      if (!this.lookAt.isAlive()) {
         return false;
      } else if (this.dragon.distanceToSqr(this.lookAt) > (double)(this.lookDistance * this.lookDistance)) {
         return false;
      } else {
         return this.lookTime > 0;
      }
   }

   public void start() {
      this.lookTime = this.adjustedTickDelay(40 + this.dragon.getRandom().nextInt(40));
   }

   public void stop() {
      this.lookAt = null;
   }

   public void tick() {
      if (this.lookAt.isAlive()) {
         double d0 = this.onlyHorizontal ? this.dragon.getEyeY() : this.lookAt.getEyeY();
         this.dragon.getLookControl().setLookAt(this.lookAt.getX(), d0, this.lookAt.getZ());
         --this.lookTime;
      }

   }
}
