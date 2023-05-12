package app.mathnek.talesofvarmithore.entity.ai;

import java.util.EnumSet;

import app.mathnek.talesofvarmithore.entity.BaseEntityClass;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public class ToVMeleeAttackGoal extends Goal {
   protected final BaseEntityClass dragon;
   private final double speedModifier;
   private final boolean followingTargetEvenIfNotSeen;
   private Path path;
   private double pathedTargetX;
   private double pathedTargetY;
   private double pathedTargetZ;
   private int ticksUntilNextPathRecalculation;
   private int ticksUntilNextAttack;
   private final int attackInterval = 20;
   private long lastCanUseCheck;
   private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
   private int failedPathFindingPenalty = 0;
   private boolean canPenalize = false;

   public ToVMeleeAttackGoal(BaseEntityClass pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
      this.dragon = pMob;
      this.speedModifier = pSpeedModifier;
      this.followingTargetEvenIfNotSeen = pFollowingTargetEvenIfNotSeen;
      this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean canUse() {
      long i = this.dragon.level.getGameTime();
      if (i - this.lastCanUseCheck < 20L) {
         return false;
      } else {
         this.lastCanUseCheck = i;
         LivingEntity livingentity = this.dragon.getTarget();
         if (livingentity == null) {
            return false;
         } else if (!livingentity.isAlive()) {
            return false;
         } else if (this.canPenalize) {
            if (--this.ticksUntilNextPathRecalculation <= 0) {
               this.path = this.dragon.getNavigation().createPath(livingentity, 0);
               this.ticksUntilNextPathRecalculation = 4 + this.dragon.getRandom().nextInt(7);
               return this.path != null;
            } else {
               return true;
            }
         } else {
            this.path = this.dragon.getNavigation().createPath(livingentity, 0);
            if (this.path != null) {
               return true;
            } else {
               return !this.dragon.shouldStopMovingIndependently() && this.getAttackReachSqr(livingentity) >= this.dragon.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            }
         }
      }
   }

   public boolean canContinueToUse() {
      LivingEntity livingentity = this.dragon.getTarget();
      if (livingentity == null) {
         return false;
      } else if (livingentity.distanceTo(this.dragon) > 8.0F) {
         return false;
      } else if (!livingentity.isAlive()) {
         return false;
      } else if (!this.followingTargetEvenIfNotSeen) {
         return !this.dragon.getNavigation().isDone();
      } else if (!this.dragon.isWithinRestriction(livingentity.blockPosition())) {
         return false;
      } else {
         return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative() && !this.dragon.shouldStopMovingIndependently();
      }
   }

   public void start() {
      this.dragon.getNavigation().moveTo(this.path, this.speedModifier);
      this.dragon.setAggressive(true);
      this.ticksUntilNextPathRecalculation = 0;
      this.ticksUntilNextAttack = 0;
   }

   public void stop() {
      LivingEntity livingentity = this.dragon.getTarget();
      if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
         this.dragon.setTarget((LivingEntity)null);
      }

      this.dragon.setAggressive(false);
      this.dragon.getNavigation().stop();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public void tick() {
      LivingEntity livingentity = this.dragon.getTarget();
      if (livingentity != null) {
         this.dragon.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
         double d0 = this.dragon.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
         this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
         if ((this.followingTargetEvenIfNotSeen || this.dragon.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.dragon.getRandom().nextFloat() < 0.05F)) {
            this.pathedTargetX = livingentity.getX();
            this.pathedTargetY = livingentity.getY();
            this.pathedTargetZ = livingentity.getZ();
            this.ticksUntilNextPathRecalculation = 4 + this.dragon.getRandom().nextInt(7);
            if (this.canPenalize) {
               this.ticksUntilNextPathRecalculation += this.failedPathFindingPenalty;
               if (this.dragon.getNavigation().getPath() != null) {
                  Node finalPathPoint = this.dragon.getNavigation().getPath().getEndNode();
                  if (finalPathPoint != null && livingentity.distanceToSqr((double)finalPathPoint.x, (double)finalPathPoint.y, (double)finalPathPoint.z) < 1.0) {
                     this.failedPathFindingPenalty = 0;
                  } else {
                     this.failedPathFindingPenalty += 10;
                  }
               } else {
                  this.failedPathFindingPenalty += 10;
               }
            }

            if (d0 > 1024.0) {
               this.ticksUntilNextPathRecalculation += 10;
            } else if (d0 > 256.0) {
               this.ticksUntilNextPathRecalculation += 5;
            }

            if (!this.dragon.getNavigation().moveTo(livingentity, this.speedModifier)) {
               this.ticksUntilNextPathRecalculation += 15;
            }

            this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
         }

         this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
         this.checkAndPerformAttack(livingentity, d0);
      }

   }

   protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
      double d0 = this.getAttackReachSqr(pEnemy);
      if (pDistToEnemySqr <= d0 && this.ticksUntilNextAttack <= 0) {
         this.resetAttackCooldown();
         this.dragon.swing(InteractionHand.MAIN_HAND);
         this.dragon.doHurtTarget(pEnemy);
      }

   }

   protected void resetAttackCooldown() {
      this.ticksUntilNextAttack = this.adjustedTickDelay(20);
   }

   protected boolean isTimeToAttack() {
      return this.ticksUntilNextAttack <= 0;
   }

   protected int getTicksUntilNextAttack() {
      return this.ticksUntilNextAttack;
   }

   protected int getAttackInterval() {
      return this.adjustedTickDelay(20);
   }

   protected double getAttackReachSqr(LivingEntity pAttackTarget) {
      return (double)(this.dragon.getBbWidth() * 2.0F * this.dragon.getBbWidth() * 2.0F + pAttackTarget.getBbWidth());
   }
}
