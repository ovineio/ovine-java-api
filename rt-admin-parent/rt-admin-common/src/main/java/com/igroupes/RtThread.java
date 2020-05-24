package com.igroupes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RtThread extends Thread {

    private static final Logger LOG = LoggerFactory
            .getLogger(RtThread.class);

    private UncaughtExceptionHandler uncaughtExceptionalHandler = new UncaughtExceptionHandler() {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            handleException(t.getName(), e);
        }
    };

    public RtThread(String threadName) {
        super(threadName);
        setUncaughtExceptionHandler(uncaughtExceptionalHandler);
    }

    /**
     * 默认的不可捕获异常处理
     * @param thName
     * @param e
     */
    protected void handleException(String thName, Throwable e) {
        LOG.warn("Exception occurred from thread {}", thName, e);
    }
}
