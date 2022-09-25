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
}
