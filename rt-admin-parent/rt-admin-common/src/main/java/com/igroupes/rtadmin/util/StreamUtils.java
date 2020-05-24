package com.igroupes.rtadmin.util;


import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
    private StreamUtils() {
    }

    /**
     * 将流转为支持mark的流，可以重用
     *
     * @param is
     * @return
     */
    public static InputStream supportMarkInputStream(InputStream is) throws IOException {
        if (is.markSupported()) {
            return is;
        }
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[10 * 1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            bytes = baos.toByteArray();
        }finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(baos);
            is = null;
            baos = null;
        }
        return new ByteArrayInputStream(bytes);
    }
}
