package app.mathnek.talesofvarmithore.entity;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import app.mathnek.talesofvarmithore.entity.azulite.AzuliteEntity;
import app.mathnek.talesofvarmithore.entity.moth_fae_dragon.MothFaeDragon;
import app.mathnek.talesofvarmithore.entity.pupfish.PupfishEntity;
import app.mathnek.talesofvarmithore.entity.twintail.TwinTailEntity;
import app.mathnek.talesofvarmithore.entity.twintail.egg.TwinTailEgg;
import app.mathnek.talesofvarmithore.entity.wilkor.EntityWilkor;
import app.mathnek.talesofvarmithore.entity.wilkor.NewWilkor;
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

    private static final float PIXEL = 0.0625F;

    public static final RegistryObject<EntityType<NewWilkor>> WILKOR =
            ENTITY_TYPES.register("wilkor",
                    () -> EntityType.Builder.of(NewWilkor::new, MobCategory.CREATURE)
                            .sized(1.3f, 2.4f)
                            .build(new ResourceLocation(TalesofVarmithore.MOD_ID, "wilkor").toString()));

    public static final RegistryObject<EntityType<AzuliteEntity>> AZULITE =
            ENTITY_TYPES.register("azulite",
                    () -> EntityType.Builder.of(AzuliteEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 1.0f)
                            .build(new ResourceLocation(TalesofVarmithore.MOD_ID, "azulite").toString()));

    public static final RegistryObject<EntityType<TwinTailEntity>> TWINTAIL =
            ENTITY_TYPES.register("twintail",
                    () -> EntityType.Builder.of(TwinTailEntity::new, MobCategory.CREATURE)
                            .sized(1.3f, 2.4f)
                            .build(new ResourceLocation(TalesofVarmithore.MOD_ID, "twintail").toString()));

    public static final RegistryObject<EntityType<PupfishEntity>> PUPFISH =
            ENTITY_TYPES.register("pupfish",
                    () -> EntityType.Builder.of(PupfishEntity::new, MobCategory.CREATURE)
                            .sized(0.7f, 0.7f)
                            .build(new ResourceLocation(TalesofVarmithore.MOD_ID, "pupfish").toString()));

    public static final RegistryObject<EntityType<MothFaeDragon>> MOTH_FAE_DRAGON =
            ENTITY_TYPES.register("moth_fae_dragon",
                    () -> EntityType.Builder.of(MothFaeDragon::new, MobCategory.AMBIENT)
                            .sized(0.8f, 1.0f)
                            .build(new ResourceLocation(TalesofVarmithore.MOD_ID, "moth_fae_dragon").toString()));

    public static final RegistryObject<EntityType<TwinTailEgg>> TWINTAIL_EGG = ENTITY_TYPES.register("twintail_egg",
            () -> EntityType.Builder.of(TwinTailEgg::new, MobCategory.MISC).sized(PIXEL * 7, PIXEL * 9)
                    .build(new ResourceLocation(TalesofVarmithore.MOD_ID, "twintail_egg").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
