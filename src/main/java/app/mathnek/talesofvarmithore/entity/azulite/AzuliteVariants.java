package app.mathnek.talesofvarmithore.entity.azulite;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Arrays;
import java.util.Comparator;

public enum AzuliteVariants {

        SAND(8),
        SNOW(7),
        JUNGLE(5),
        END(2),
        NETHER(4),
        OCEAN(6),
        SAVANNA(3),
        HILLS(1);


        public static final TagKey<Biome> IS_DEEP_OCEAN = create("is_deep_ocean");
        public static final TagKey<Biome> IS_OCEAN = create("is_ocean");
        public static final TagKey<Biome> IS_BEACH = create("is_beach");
        public static final TagKey<Biome> IS_RIVER = create("is_river");
        public static final TagKey<Biome> IS_MOUNTAIN = create("is_mountain");
        public static final TagKey<Biome> IS_BADLANDS = create("is_badlands");
        public static final TagKey<Biome> IS_HILL = create("is_hill");
        public static final TagKey<Biome> IS_TAIGA = create("is_taiga");
        public static final TagKey<Biome> IS_JUNGLE = create("is_jungle");
        public static final TagKey<Biome> IS_FOREST = create("is_forest");
        public static final TagKey<Biome> IS_NETHER = create("is_nether");



        private static TagKey<Biome> create(String pName) {
                return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(pName));
        }

        private static final AzuliteVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
                comparingInt(AzuliteVariants::getId)).toArray(AzuliteVariants[]::new);
        private final int id;

        AzuliteVariants(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static AzuliteVariants byId(int id) {
            return BY_ID[id % BY_ID.length];
        }


}
