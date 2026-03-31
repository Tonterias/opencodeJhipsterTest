package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CinterestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Cinterest getCinterestSample1() {
        return new Cinterest().id(1L).interestName("interestName1");
    }

    public static Cinterest getCinterestSample2() {
        return new Cinterest().id(2L).interestName("interestName2");
    }

    public static Cinterest getCinterestRandomSampleGenerator() {
        return new Cinterest().id(longCount.incrementAndGet()).interestName(UUID.randomUUID().toString());
    }
}
