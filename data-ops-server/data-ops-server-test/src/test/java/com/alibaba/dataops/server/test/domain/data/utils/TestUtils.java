package com.alibaba.dataops.server.test.domain.data.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试工具类
 *
 * @author Jiaju Zhuang
 */
public class TestUtils {

    public static final AtomicLong ATOMIC_LONG = new AtomicLong();

    /**
     * 一个全局唯一的long
     *
     * @return
     */
    public static long nextLong() {
        return ATOMIC_LONG.incrementAndGet();
    }

    /**
     * 如果默认值类似于 'DATA'
     * 则需要把'' 去掉
     *
     * @param defaultValue
     * @return
     */
    public static String unWrapperDefaultValue(String defaultValue) {
        if (defaultValue == null) {
            return null;
        }
        if (defaultValue.startsWith("'") && defaultValue.endsWith("'")) {
            if (defaultValue.length() < 2) {
                return defaultValue;
            } else if (defaultValue.length() == 2) {
                return "";
            } else {
                return defaultValue.substring(1, defaultValue.length() - 1);
            }
        }
        return defaultValue;

    }
}
