package com.opencode.test.service.mapper;

import static com.opencode.test.domain.CelebAsserts.*;
import static com.opencode.test.domain.CelebTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CelebMapperTest {

    private CelebMapper celebMapper;

    @BeforeEach
    void setUp() {
        celebMapper = new CelebMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCelebSample1();
        var actual = celebMapper.toEntity(celebMapper.toDto(expected));
        assertCelebAllPropertiesEquals(expected, actual);
    }
}
