package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CommunityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Community getCommunitySample1() {
        return new Community().id(1L).communityName("communityName1").communityDescription("communityDescription1");
    }

    public static Community getCommunitySample2() {
        return new Community().id(2L).communityName("communityName2").communityDescription("communityDescription2");
    }

    public static Community getCommunityRandomSampleGenerator() {
        return new Community()
            .id(longCount.incrementAndGet())
            .communityName(UUID.randomUUID().toString())
            .communityDescription(UUID.randomUUID().toString());
    }
}
