package com.igroupes.rtadmin.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    private Md5Utils() {
    }


    private static ThreadLocal<MessageDigest> md5Local = new ThreadLocal<>();

    public static String computeMd5(String k) {
        return computeMd5(k.getBytes(StandardCharsets.UTF_8));
    }

    public static String computeMd5(byte[] b) {
        MessageDigest md5 = md5Local.get();
        if (md5 == null) {
            try {
                md5 = MessageDigest.getInstance("MD5");
                md5Local.set(md5);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("MD5 not supported", e);
            }
        }
        md5.reset();
        md5.update(b);
        return Base64Utils.encodeToString(md5.digest());
    }

}
