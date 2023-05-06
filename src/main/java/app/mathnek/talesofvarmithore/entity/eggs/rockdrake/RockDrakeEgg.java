package app.mathnek.talesofvarmithore.entity.eggs.rockdrake;

import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.eggs.DragonEggBase;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import app.mathnek.talesofvarmithore.item.DragonEggItem;
import app.mathnek.talesofvarmithore.item.ToVItems;
import app.mathnek.talesofvarmithore.util.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib3.core.IAnimatable;

public class RockDrakeEgg extends DragonEggBase implements IAnimatable {

    public RockDrakeEgg(EntityType pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    protected RockDrakeEntity getDragonEggResult() {
        return (RockDrakeEntity)((EntityType) ToVEntityTypes.ROCKDRAKE_EGG.get()).create(this.level);
    }

    protected DragonEggItem getItemVersion() {
        return (DragonEggItem) ToVItems.ROCKDRAKE_EGG.get();
    }

    public Block getBlockParticle() {
        return Blocks.GREEN_CONCRETE;
    }

    protected int getHatchTimeMinecraftDays() {
        return Util.mcDaysToMinutes(2);
    }
}