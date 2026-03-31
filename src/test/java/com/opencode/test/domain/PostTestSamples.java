package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PostTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Post getPostSample1() {
        return new Post()
            .id(1L)
            .headline("headline1")
            .leadtext("leadtext1")
            .bodytext("bodytext1")
            .quote("quote1")
            .conclusion("conclusion1")
            .linkText("linkText1")
            .linkURL("linkURL1");
    }

    public static Post getPostSample2() {
        return new Post()
            .id(2L)
            .headline("headline2")
            .leadtext("leadtext2")
            .bodytext("bodytext2")
            .quote("quote2")
            .conclusion("conclusion2")
            .linkText("linkText2")
            .linkURL("linkURL2");
    }

    public static Post getPostRandomSampleGenerator() {
        return new Post()
            .id(longCount.incrementAndGet())
            .headline(UUID.randomUUID().toString())
            .leadtext(UUID.randomUUID().toString())
            .bodytext(UUID.randomUUID().toString())
            .quote(UUID.randomUUID().toString())
            .conclusion(UUID.randomUUID().toString())
            .linkText(UUID.randomUUID().toString())
            .linkURL(UUID.randomUUID().toString());
    }
}
