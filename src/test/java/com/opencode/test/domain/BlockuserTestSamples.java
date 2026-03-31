package com.opencode.test.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BlockuserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Blockuser getBlockuserSample1() {
        return new Blockuser().id(1L);
    }

    public static Blockuser getBlockuserSample2() {
        return new Blockuser().id(2L);
    }

    public static Blockuser getBlockuserRandomSampleGenerator() {
        return new Blockuser().id(longCount.incrementAndGet());
    }
}
