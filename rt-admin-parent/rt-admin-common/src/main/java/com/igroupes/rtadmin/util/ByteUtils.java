package com.igroupes.rtadmin.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteUtils {
    private ByteUtils() {
    }


    public static byte[] merge(byte[] b1, byte[] b2) {
        if (b1 == null || b1.length == 0) {
            return b2;
        }
        if (b2 == null || b2.length == 0) {
            return b1;
        }
        byte[] b3 = new byte[b1.length + b2.length];
        System.arraycopy(b1, 0, b3, 0, b1.length);
        System.arraycopy(b2, 0, b3, b1.length, b2.length);
        return b3;
    }


    public static byte[] getByStream(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int maxCount = 10 * 1024;
        byte[] buffer = new byte[maxCount];
        int len = 0;
        while ( (len =is.read(buffer)) != -1) {
            bos.write(buffer, 0 ,len);
        }
        return bos.toByteArray();
    }

    public static boolean isSame(byte[] b1, byte[] b2) {
        if (b1 == null) {
            return b2 == null;
        }
        if (b2 == null) {
            return false;
        }
        if (b1.length != b2.length) {
            return false;
        }
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }
        return true;
    }
}
