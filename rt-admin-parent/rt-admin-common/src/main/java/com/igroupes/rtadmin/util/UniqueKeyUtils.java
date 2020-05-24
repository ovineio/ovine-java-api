package com.igroupes.rtadmin.util;

import java.util.concurrent.ThreadLocalRandom;

public class UniqueKeyUtils {
    private UniqueKeyUtils() {
    }

    public static Long getKey(){
        int seed = ThreadLocalRandom.current().nextInt(100);
        long key;
        key = (TimeUtils.currentElapsedTime() << 24) >>> 8;
        key =  key | (seed <<56);
        return key;
    }
}
