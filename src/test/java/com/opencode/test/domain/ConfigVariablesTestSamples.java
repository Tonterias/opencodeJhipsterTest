package com.opencode.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ConfigVariablesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static ConfigVariables getConfigVariablesSample1() {
        return new ConfigVariables()
            .id(1L)
            .configVarLong1(1L)
            .configVarLong2(1L)
            .configVarLong3(1L)
            .configVarLong4(1L)
            .configVarLong5(1L)
            .configVarLong6(1L)
            .configVarLong7(1L)
            .configVarLong8(1L)
            .configVarLong9(1L)
            .configVarLong10(1L)
            .configVarLong11(1L)
            .configVarLong12(1L)
            .configVarLong13(1L)
            .configVarLong14(1L)
            .configVarLong15(1L)
            .configVarString19("configVarString191")
            .configVarString20("configVarString201");
    }

    public static ConfigVariables getConfigVariablesSample2() {
        return new ConfigVariables()
            .id(2L)
            .configVarLong1(2L)
            .configVarLong2(2L)
            .configVarLong3(2L)
            .configVarLong4(2L)
            .configVarLong5(2L)
            .configVarLong6(2L)
            .configVarLong7(2L)
            .configVarLong8(2L)
            .configVarLong9(2L)
            .configVarLong10(2L)
            .configVarLong11(2L)
            .configVarLong12(2L)
            .configVarLong13(2L)
            .configVarLong14(2L)
            .configVarLong15(2L)
            .configVarString19("configVarString192")
            .configVarString20("configVarString202");
    }

    public static ConfigVariables getConfigVariablesRandomSampleGenerator() {
        return new ConfigVariables()
            .id(longCount.incrementAndGet())
            .configVarLong1(longCount.incrementAndGet())
            .configVarLong2(longCount.incrementAndGet())
            .configVarLong3(longCount.incrementAndGet())
            .configVarLong4(longCount.incrementAndGet())
            .configVarLong5(longCount.incrementAndGet())
            .configVarLong6(longCount.incrementAndGet())
            .configVarLong7(longCount.incrementAndGet())
            .configVarLong8(longCount.incrementAndGet())
            .configVarLong9(longCount.incrementAndGet())
            .configVarLong10(longCount.incrementAndGet())
            .configVarLong11(longCount.incrementAndGet())
            .configVarLong12(longCount.incrementAndGet())
            .configVarLong13(longCount.incrementAndGet())
            .configVarLong14(longCount.incrementAndGet())
            .configVarLong15(longCount.incrementAndGet())
            .configVarString19(UUID.randomUUID().toString())
            .configVarString20(UUID.randomUUID().toString());
    }
}
