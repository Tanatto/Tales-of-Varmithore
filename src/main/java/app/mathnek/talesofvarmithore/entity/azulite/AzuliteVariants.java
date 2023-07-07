package app.mathnek.talesofvarmithore.entity.azulite;

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
