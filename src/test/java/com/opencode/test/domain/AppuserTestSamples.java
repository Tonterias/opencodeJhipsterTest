package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AppuserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Appuser getAppuserSample1() {
        return new Appuser().id(1L).bio("bio1").facebook("facebook1").twitter("twitter1").linkedin("linkedin1").instagram("instagram1");
    }

    public static Appuser getAppuserSample2() {
        return new Appuser().id(2L).bio("bio2").facebook("facebook2").twitter("twitter2").linkedin("linkedin2").instagram("instagram2");
    }

    public static Appuser getAppuserRandomSampleGenerator() {
        return new Appuser()
            .id(longCount.incrementAndGet())
            .bio(UUID.randomUUID().toString())
            .facebook(UUID.randomUUID().toString())
            .twitter(UUID.randomUUID().toString())
            .linkedin(UUID.randomUUID().toString())
            .instagram(UUID.randomUUID().toString());
    }
}
