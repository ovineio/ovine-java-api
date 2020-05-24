package com.igroupes.rtadmin.util;

import org.apache.commons.lang3.StringUtils;

public class PathUtils {
    private PathUtils() {
    }

    public static String normalize(String parentPath, String childPath) {
        if (StringUtils.isBlank(parentPath)) {
            return childPath;
        }
        if (StringUtils.isBlank(childPath)) {
            return parentPath;
        }
        if (!parentPath.endsWith("/")) {
            parentPath = parentPath + "/";
        }
        if (childPath.startsWith("/")) {
            return parentPath + childPath.substring(1);
        } else {
            return parentPath + childPath;
        }
    }
}
