package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UrllinkTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Urllink getUrllinkSample1() {
        return new Urllink().id(1L).linkText("linkText1").linkURL("linkURL1");
    }

    public static Urllink getUrllinkSample2() {
        return new Urllink().id(2L).linkText("linkText2").linkURL("linkURL2");
    }

    public static Urllink getUrllinkRandomSampleGenerator() {
        return new Urllink().id(longCount.incrementAndGet()).linkText(UUID.randomUUID().toString()).linkURL(UUID.randomUUID().toString());
    }
}
