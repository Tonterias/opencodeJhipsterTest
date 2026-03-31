package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CcelebTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Cceleb getCcelebSample1() {
        return new Cceleb().id(1L).celebName("celebName1");
    }

    public static Cceleb getCcelebSample2() {
        return new Cceleb().id(2L).celebName("celebName2");
    }

    public static Cceleb getCcelebRandomSampleGenerator() {
        return new Cceleb().id(longCount.incrementAndGet()).celebName(UUID.randomUUID().toString());
    }
}
