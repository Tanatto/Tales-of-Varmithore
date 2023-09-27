package app.mathnek.talesofvarmithore.world;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.world.structures.Oasis;
import app.mathnek.talesofvarmithore.world.structures.StoneVulcan;
import app.mathnek.talesofvarmithore.world.structures.TwinTailNest;
import app.mathnek.talesofvarmithore.world.structures.WilkorDen;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ToVStructures {
    /**
     * We are using the Deferred Registry system to register our structure as this is the preferred way on Forge.
     * This will handle registering the base structure for us at the correct time so we don't have to handle it ourselves.
     * <p>
     * HOWEVER, do note that Deferred Registries only work for anything that is a Forge Registry. This means that
     * configured structures and configured features need to be registered directly to BuiltinRegistries as there
     * is no Deferred Registry system for them.
     */
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, TalesofVarmithore.MOD_ID);

    /**
     * Registers the base structure itself and sets what its path is. In this case,
     * this base structure will have the resourcelocation of structure_tutorial:sky_structures.
     */
    public static final RegistryObject<StructureFeature<?>> WILKOR_DEN =
            STRUCTURES.register("wilkor_den", WilkorDen::new);

    public static final RegistryObject<StructureFeature<?>> TWINTAIL_NEST =
            STRUCTURES.register("twintail_nest", TwinTailNest::new);
    public static final RegistryObject<StructureFeature<?>> OASIS =
            STRUCTURES.register("oasis", Oasis::new);

    public static final RegistryObject<StructureFeature<?>> STONEVULCAN =
            STRUCTURES.register("stonevulcan", StoneVulcan::new);

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }
}
