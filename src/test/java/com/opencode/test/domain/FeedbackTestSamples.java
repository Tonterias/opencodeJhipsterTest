package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FeedbackTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Feedback getFeedbackSample1() {
        return new Feedback().id(1L).name("name1").email("email1").feedback("feedback1");
    }

    public static Feedback getFeedbackSample2() {
        return new Feedback().id(2L).name("name2").email("email2").feedback("feedback2");
    }

    public static Feedback getFeedbackRandomSampleGenerator() {
        return new Feedback()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .feedback(UUID.randomUUID().toString());
    }
}
