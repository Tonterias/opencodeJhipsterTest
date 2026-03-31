package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CactivityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Cactivity getCactivitySample1() {
        return new Cactivity().id(1L).activityName("activityName1");
    }

    public static Cactivity getCactivitySample2() {
        return new Cactivity().id(2L).activityName("activityName2");
    }

    public static Cactivity getCactivityRandomSampleGenerator() {
        return new Cactivity().id(longCount.incrementAndGet()).activityName(UUID.randomUUID().toString());
    }
}
