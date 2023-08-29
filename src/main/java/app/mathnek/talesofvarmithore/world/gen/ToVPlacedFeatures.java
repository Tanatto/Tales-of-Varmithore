package app.mathnek.talesofvarmithore.world.gen;

import app.mathnek.talesofvarmithore.world.gen.ores.ToVOrePlacement;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class ToVPlacedFeatures {
    public static final Holder<PlacedFeature> PERSILA_PLACED = PlacementUtils.register("persila_placed",
            ToVConfiguredFeatures.PERSILA, RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> UNCIA_PLACED = PlacementUtils.register("uncia_placed",
            ToVConfiguredFeatures.UNCIA, RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
}
