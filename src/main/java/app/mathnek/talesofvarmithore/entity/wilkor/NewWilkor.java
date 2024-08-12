package app.mathnek.talesofvarmithore.entity.wilkor;

import app.mathnek.talesofvarmithore.entity.EntityGroundBase;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NewWilkor extends EntityGroundBase {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public NewWilkor(EntityType animal, Level world) {
        super(animal, world);
    }

    private PlayState predicate(AnimationState event) {
        if (event.isMoving()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("direwolf.walk"));
        }
        if (event.isMoving() && this.isVehicle()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("direwolf.run"));
        }
        if (this.isEntitySitting()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("direwolf.sit"));
        }
        if (this.isEntitySleeping()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("direwolf.sleep"));
        }
        if (event.isMoving() && this.isInWater()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("direwolf.swim"));
        }

        return event.setAndContinue(RawAnimation.begin().thenLoop("direwolf.idle"));
    }

    public static AttributeSupplier.Builder setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0f)
                .add(Attributes.ATTACK_SPEED, 5.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.JUMP_STRENGTH, 2);
    }

    public boolean isNocturnal() {
        return true;
    }

    @Override
    public int getMaxAmountOfVariants() {
        return 10;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }
}
