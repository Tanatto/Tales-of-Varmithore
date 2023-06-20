package app.mathnek.talesofvarmithore.world.gen;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ToVPlacesFeatures {
    public static final Holder<PlacedFeature> PERSILA_PLACED = PlacementUtils.register("persila_placed",
            ToVConfiguredFeatures.PERSILA, RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> UNCIA_PLACED = PlacementUtils.register("uncia_placed",
            ToVConfiguredFeatures.UNCIA, RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
}
