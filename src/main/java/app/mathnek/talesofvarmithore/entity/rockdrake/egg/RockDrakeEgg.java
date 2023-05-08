package app.mathnek.talesofvarmithore.entity.rockdrake.egg;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.DragonEggBase;
import app.mathnek.talesofvarmithore.entity.ToVEntityTypes;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import app.mathnek.talesofvarmithore.item.DragonEggItem;
import app.mathnek.talesofvarmithore.item.ToVItems;
import app.mathnek.talesofvarmithore.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class RockDrakeEgg extends DragonEggBase {
    public RockDrakeEgg(EntityType<? extends DragonEggBase> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public ResourceLocation getTextureLocation(DragonEggBase dragonBase) {
        return new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/eggs/rockdrake_egg.png");
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected RockDrakeEntity getDragonEggResult() {
        return ToVEntityTypes.ROCKDRAKE.get().create(this.level);
    }

    @Override
    protected DragonEggItem getItemVersion() {
        return ToVItems.ROCKDRAKE_EGG.get();
    }

    @Override
    public Block getBlockParticle() {
        return Blocks.AMETHYST_BLOCK;
    }

    @Override
    protected int getHatchTime() {
        return Util.mcDaysToMinutes(2);
    }

    @Override
    public ItemStack getPickResult()
    {
        return new ItemStack(ToVItems.ROCKDRAKE_EGG.get());
    }
}
