package app.mathnek.talesofvarmithore.item;

import app.mathnek.talesofvarmithore.entity.DragonEggBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DragonEggItem extends Item {

    Supplier<? extends EntityType<? extends LivingEntity>> eggSpecies;

    public DragonEggItem(Properties pProperties, Supplier<? extends EntityType<? extends LivingEntity>> eggSpecies) {
        super(pProperties);
        this.eggSpecies = eggSpecies;
    }

    /*public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String s = "item.egg.tame.hatchling";
        MutableComponent mutablecomponent = new TranslatableComponent(s);
        ChatFormatting[] achatformatting = new ChatFormatting[]{ChatFormatting.BLUE};

        mutablecomponent.withStyle(achatformatting);
        pTooltipComponents.add(mutablecomponent);
    }*/

    /**
     * Called when this item is used when targetting a Block
     * if placed, the player will be set as owner in the egg entity and the recurring hatching, used on obtaining speed stinger.
     */
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        ItemStack playerHeldItem = pContext.getItemInHand();
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        DragonEggBase eggEntity = (DragonEggBase) eggSpecies.get().create(level);
        // set owner
        assert eggEntity != null;
        eggEntity.moveTo(pContext.getClickLocation());
        if (!level.isClientSide()) {
            level.addFreshEntity(eggEntity);
            eggEntity.setCustomName(playerHeldItem.getHoverName());
        }
        playerHeldItem.shrink(1);
        return InteractionResult.SUCCESS;
    }
}

