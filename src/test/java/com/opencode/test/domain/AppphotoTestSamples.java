package com.opencode.test.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AppphotoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Appphoto getAppphotoSample1() {
        return new Appphoto().id(1L);
    }

    public static Appphoto getAppphotoSample2() {
        return new Appphoto().id(2L);
    }

    public static Appphoto getAppphotoRandomSampleGenerator() {
        return new Appphoto().id(longCount.incrementAndGet());
    }
}
