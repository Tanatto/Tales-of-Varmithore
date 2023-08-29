package app.mathnek.talesofvarmithore.entity.moth_fae_dragon;

import java.util.Arrays;
import java.util.Comparator;

public enum MothFaeVariants {
    //Overworld
    FIREBASE1(0),
    FIREBASE2(1),
    FIREBASE3(2),
    FIREBASE_SNOW(3),

    //Nether
    FIREBASE_NETHER(4),
    FIREBASE_CRIMSON_FOREST(5),
    FIREBASE_END(6),
    FIREBASE_SOULSAND_VALLEY(7),
    FIREBASE_WARPED_FOREST(8);
    private static final MothFaeVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(MothFaeVariants::getId)).toArray(MothFaeVariants[]::new);
    private final int id;

    MothFaeVariants(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static MothFaeVariants byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
