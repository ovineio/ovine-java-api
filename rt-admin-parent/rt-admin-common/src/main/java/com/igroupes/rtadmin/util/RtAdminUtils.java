package com.igroupes.rtadmin.util;

public class RtAdminUtils {
    private RtAdminUtils() {
    }

    public static String parentChain(Long curParentId, String curParentChain) {
        Requires.requireNonNull(curParentId, "user id");
        Requires.requireNonBlank(curParentChain, "current parent chain");
        return curParentChain + "-" + curParentId;
    }

}
