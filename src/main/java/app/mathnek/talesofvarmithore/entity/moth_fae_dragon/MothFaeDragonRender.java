package app.mathnek.talesofvarmithore.entity.moth_fae_dragon;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class MothFaeDragonRender extends GeoEntityRenderer<MothFaeDragon> {
    public MothFaeDragonRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MothFaeDragonModel());
        this.shadowRadius = 1f;
    }

    public static final Map<MothFaeVariants, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MothFaeVariants.class), (p_114874_) -> {
                //Overworld
                p_114874_.put(MothFaeVariants.FIREBASE1,
                        new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/overworld/firefae_base_1.png"));
                p_114874_.put(MothFaeVariants.FIREBASE2,
                        new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/overworld/firefae_base_2.png"));
                p_114874_.put(MothFaeVariants.FIREBASE3,
                        new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/overworld/firefae_base_3.png"));
                p_114874_.put(MothFaeVariants.FIREBASE_SNOW,
                        new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/overworld/firefae_base_snowbiomes.png"));
                //Nether
                p_114874_.put(MothFaeVariants.FIREBASE_NETHER,
                        new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_basenether.png"));
                p_114874_.put(MothFaeVariants.FIREBASE_CRIMSON_FOREST,
                        new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_crimsonforest.png"));
                p_114874_.put(MothFaeVariants.FIREBASE_END,
                        new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_end.png"));
                p_114874_.put(MothFaeVariants.FIREBASE_SOULSAND_VALLEY,
                        new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_soulsandvalley.png"));
                p_114874_.put(MothFaeVariants.FIREBASE_WARPED_FOREST,
                        new ResourceLocation(TalesofVarmithore.MOD_ID, "textures/entity/moth_fae_dragon/nether/firefae_warpedforest.png"));
            });

    @Override
    public RenderType getRenderType(MothFaeDragon animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public ResourceLocation getTextureLocation(MothFaeDragon instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }
}
