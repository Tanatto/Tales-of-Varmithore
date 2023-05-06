package app.mathnek.talesofvarmithore.entity.rockdrake;

import java.util.Arrays;
import java.util.Comparator;

public enum RockDrakeVariants {

        NORMAL(1),
        BLUE(2),
        GREEN(3),
        BLACK(4),
        GREY(5),
        BLUEGREEN(6),
        YELLOW(7),
        PURPLE(8),
        RED(9),
        BROWN(10),
        WHOLEBLACK(11),
        WHOLERED(12),
        REDBLUE(13);

        private static final RockDrakeVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
                comparingInt(RockDrakeVariants::getId)).toArray(RockDrakeVariants[]::new);
        private final int id;

        RockDrakeVariants(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static RockDrakeVariants byId(int id) {
            return BY_ID[id % BY_ID.length];
        }


}
