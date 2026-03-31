package com.opencode.test.service.mapper;

import static com.opencode.test.domain.CactivityAsserts.*;
import static com.opencode.test.domain.CactivityTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CactivityMapperTest {

    private CactivityMapper cactivityMapper;

    @BeforeEach
    void setUp() {
        cactivityMapper = new CactivityMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCactivitySample1();
        var actual = cactivityMapper.toEntity(cactivityMapper.toDto(expected));
        assertCactivityAllPropertiesEquals(expected, actual);
    }
}
