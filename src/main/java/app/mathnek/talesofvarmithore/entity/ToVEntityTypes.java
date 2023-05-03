package app.mathnek.talesofvarmithore.entity;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.azulite.AzuliteEntity;
import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import app.mathnek.talesofvarmithore.entity.wilkor.WilkorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ToVEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, TalesofVarmithore.MOD_ID);

    public static final RegistryObject<EntityType<WilkorEntity>> WILKOR =
            ENTITY_TYPES.register("wilkor",
            () -> EntityType.Builder.of(WilkorEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 2.4f)
                    .build(new ResourceLocation(TalesofVarmithore.MOD_ID, "wilkor").toString()));

    public static final RegistryObject<EntityType<RockDrakeEntity>> ROCKDRAKE =
            ENTITY_TYPES.register("rock_drake",
                    () -> EntityType.Builder.of(RockDrakeEntity::new, MobCategory.CREATURE)
                            .sized(2.0f, 2.4f)
                            .build(new ResourceLocation(TalesofVarmithore.MOD_ID, "rock_drake").toString()));

    public static final RegistryObject<EntityType<AzuliteEntity>> AZULITE =
            ENTITY_TYPES.register("azulite",
                    () -> EntityType.Builder.of(AzuliteEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.0f)
                            .build(new ResourceLocation(TalesofVarmithore.MOD_ID, "azulite").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
