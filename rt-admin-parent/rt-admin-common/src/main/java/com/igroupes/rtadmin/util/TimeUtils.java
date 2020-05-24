package com.igroupes.rtadmin.util;

import java.util.Date;

public class TimeUtils {
    private TimeUtils(){}
    /**
     * Returns time in milliseconds as does System.currentTimeMillis(),
     * but uses elapsed time from an arbitrary epoch more like System.nanoTime().
     * The difference is that if somebody changes the system clock,
     * Time.currentElapsedTime will change but nanoTime won't. On the other hand,
     * all of ZK assumes that time is measured in milliseconds.
     *
     * @return  The time in milliseconds from some arbitrary point in time.
     */
    public static long currentElapsedTime() {
        return System.nanoTime() / 1000000;
    }

    /**
     * Explicitly returns system dependent current wall time.
     * @return Current time in msec.
     */
    public static long currentWallTime() {
        return System.currentTimeMillis();
    }

    /**
     * This is to convert the elapsedTime to a Date.
     * @return A date object indicated by the elapsedTime.
     */
    public static Date elapsedTimeToDate(long elapsedTime) {
        long wallTime = currentWallTime() + elapsedTime - currentElapsedTime();
        return new Date(wallTime);
    }
}
