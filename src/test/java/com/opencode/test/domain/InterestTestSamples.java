package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InterestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Interest getInterestSample1() {
        return new Interest().id(1L).interestName("interestName1");
    }

    public static Interest getInterestSample2() {
        return new Interest().id(2L).interestName("interestName2");
    }

    public static Interest getInterestRandomSampleGenerator() {
        return new Interest().id(longCount.incrementAndGet()).interestName(UUID.randomUUID().toString());
    }
}
