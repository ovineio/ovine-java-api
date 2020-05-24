package com.igroupes.rtadmin.service.impl;

import com.igroupes.RtThread;
import com.igroupes.rtadmin.ExpiryQueue;
import com.igroupes.rtadmin.config.ExpiryQueueProperties;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.service.IVerifyCodeManager;
import com.igroupes.rtadmin.util.UniqueKeyUtils;
import com.igroupes.rtadmin.util.Requires;
import com.igroupes.rtadmin.util.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class DefaultVerifyCodeManager extends RtThread implements IVerifyCodeManager {
    private static final ConcurrentHashMap<Long, String> CODE_MAP = new ConcurrentHashMap<>();
    private volatile boolean running = true;
    private static final int DEFAULT_VERIFY_CODE_INTERVAL = 5 * 60 * 1000;
    private final ExpiryQueue<Long> expiryQueue;
    private final ExpiryQueueProperties expiryQueueProperties;

    @Autowired
    public DefaultVerifyCodeManager(ExpiryQueueProperties expiryQueueProperties) {
        super("verifyCodeManage");
        this.expiryQueueProperties = expiryQueueProperties;
        Integer verifyCodeInterval = expiryQueueProperties.getVerifyCodeInterval();
        if (verifyCodeInterval == null) {
            expiryQueue = new ExpiryQueue<Long>(DEFAULT_VERIFY_CODE_INTERVAL);
        } else {
            expiryQueue = new ExpiryQueue<Long>(verifyCodeInterval);
        }
        // 启动线程
        this.start();
    }

    public VerifyCode genCode(CodeType codeType) {
        switch (codeType) {
            case SIMPLE_PICTURE:
                return genPictureCode();
            default:
                throw new RtAdminException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                // 等待下一个过期时间点
                long waitTime = expiryQueue.getWaitTime();
                if (waitTime > 0) {
                    Thread.sleep(waitTime);
                    continue;
                }

                for (Long key : expiryQueue.poll()) {
                    CODE_MAP.remove(key);
                }
            }
        } catch (InterruptedException e) {
            handleException(this.getName(), e);
        }
    }

    /**
     * 简单的图片
     *
     * @return
     */
    private VerifyCode genPictureCode() {
        Object[] image = VerifyCodeUtil.createImage();
        if (image.length != 2) {
            throw new RtAdminException(ErrorCode.SYSTEM_ERROR);
        }
        VerifyCode<BufferedImage> verifyCode = new VerifyCode<>();
        String code = image[0].toString();
        log.info("verify code : {} ", code);
        Requires.requireNonNull(code, "verify code");
        verifyCode.setCode(code);
        verifyCode.setData((BufferedImage) image[1]);
        Long codeKey = null;
        while (true) {
            codeKey = UniqueKeyUtils.getKey();
            if (CODE_MAP.putIfAbsent(codeKey, code.toUpperCase()) == null) {
                verifyCode.setCodeId(UniqueKeyUtils.getKey());
                break;
            }
        }
        verifyCode.setCodeId(codeKey);
        expiryQueue.update(codeKey, expiryQueueProperties.getVerifyCodeTimeout());
        return verifyCode;
    }

    /**
     * 判断是不是正确的验证码
     *
     * @param codeKey
     * @param code
     * @return
     */
    public boolean isRightVerifyCode(Long codeKey, String code) {
        if (codeKey == null || StringUtils.isBlank(code)) {
            return false;
        }
        if (code.toUpperCase().equals(CODE_MAP.get(codeKey))) {
            return true;
        }
        return false;
    }

    public String getVerifyCode(Long codeKey) {
        if (codeKey == null) {
            return null;
        }
        return CODE_MAP.get(codeKey);
    }


    public void deleteVerifyCode(Long codeKey) {
        CODE_MAP.remove(codeKey);
    }

    /**
     * 终止线程
     */
    public void close() {
        running = false;
    }


}
