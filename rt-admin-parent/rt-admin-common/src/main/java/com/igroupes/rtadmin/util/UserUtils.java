package com.igroupes.rtadmin.util;

import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Slf4j
public class UserUtils {

    private UserUtils() {
    }

    public static String genToken() {
        return UUID.randomUUID().toString().toLowerCase().replace("-", "");
    }

    /**
     *
     * @param password 数据库中的密码
     * @param salt 加盐
     * @param checkPassword 准备校验的密码
     */
    public static void checkPassword(String password,String salt, String checkPassword) {
        checkPasswordLength(checkPassword);
        if (!genPassword(checkPassword, salt).equals(password)) {
            throw new RtAdminException(ErrorCode.PASSWORD_ERROR);
        }
    }

    public static String genPassword(String password) {
        return genPassword(password,genSalt());
    }

    public static String genPassword(String password, String salt) {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(salt)) {
            throw new IllegalArgumentException("password or salt is blank");
        }
        return Md5Utils.computeMd5(password + salt);
    }


    public static void checkPasswordLength(String password) {
        if (password.length() > 20 || password.length() < 6) {
            log.error("密码长度在[6-20]之间");
            throw new RtAdminException(ErrorCode.PASSWORD_FORMAT_ERROR);
        }
    }

    public static String genSalt() {
        return RandomStringUtils.randomAlphanumeric(8);
    }


    public static void main(String[] args) {
        String salt = genSalt();
        System.out.println(salt);
        System.out.println(genPassword("admin12345",salt));
    }
}
