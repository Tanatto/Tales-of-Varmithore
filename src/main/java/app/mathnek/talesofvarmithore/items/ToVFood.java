package app.mathnek.talesofvarmithore.items;

import net.minecraft.world.food.FoodProperties;

public class ToVFood {
    public static final FoodProperties ROCKDRAKE_MEAT =
            (new FoodProperties.Builder())
                    .fast()
                    .nutrition(3)
                    .saturationMod(0.2F)
                    .build();

    public static final FoodProperties ROCKDRAKE_MEAT_COOKED =
            (new FoodProperties.Builder())
                    .fast()
                    .nutrition(11)
                    .saturationMod(15.8F)
                    .build();
}