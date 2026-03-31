package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CelebTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Celeb getCelebSample1() {
        return new Celeb().id(1L).celebName("celebName1");
    }

    public static Celeb getCelebSample2() {
        return new Celeb().id(2L).celebName("celebName2");
    }

    public static Celeb getCelebRandomSampleGenerator() {
        return new Celeb().id(longCount.incrementAndGet()).celebName(UUID.randomUUID().toString());
    }
}
