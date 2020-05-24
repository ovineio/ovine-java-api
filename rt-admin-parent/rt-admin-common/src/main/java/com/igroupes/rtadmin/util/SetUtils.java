package com.igroupes.rtadmin.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetUtils {
    private SetUtils() {
    }

    /**
     * 交集
     *
     * @return
     */
    public static <T> Set<T> intersect(Set<T> set1, Set<T> set2) {
        if (set1 == null || set2 == null || set1.isEmpty() || set2.isEmpty()) {
            return Collections.EMPTY_SET;
        }
        Set<T> ret = new HashSet<>();
        for (T t : set1) {
            if (set2.contains(t)) {
                ret.add(t);
            }
        }
        return ret;
    }

    /**
     * 并集
     *
     * @return
     */
    public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
        if (set1 == null || set1.isEmpty()) {
            if (set2 == null || set2.isEmpty()) {
                return Collections.EMPTY_SET;
            } else {
                return set2;
            }
        }
        if (set2 == null || set2.isEmpty()) {
            if (set1 == null || set1.isEmpty()) {
                return Collections.EMPTY_SET;
            } else {
                return set1;
            }
        }

        Set<T> ret = set1;
        for (T t : set2) {
            if (!set1.contains(t)) {
                ret.add(t);
            }
        }
        return ret;
    }

    /**
     * 差集
     * set1-set2 , 元素在set1但是不在set2
     *
     * @return
     */
    public static <T> Set<T> diff(Set<T> set1, Set<T> set2) {
        if (set1 == null || set1.isEmpty()) {
            return Collections.EMPTY_SET;
        }
        if (set2 == null || set2.isEmpty()) {
            if (set1 == null || set1.isEmpty()) {
                return Collections.EMPTY_SET;
            } else {
                return set1;
            }
        }

        Set<T> ret = new HashSet<>();
        for (T t : set1) {
            if (!set2.contains(t)) {
                ret.add(t);
            }
        }
        return ret;
    }



}
