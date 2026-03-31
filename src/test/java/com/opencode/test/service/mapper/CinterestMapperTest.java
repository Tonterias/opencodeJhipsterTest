package com.opencode.test.service.mapper;

import static com.opencode.test.domain.CinterestAsserts.*;
import static com.opencode.test.domain.CinterestTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CinterestMapperTest {

    private CinterestMapper cinterestMapper;

    @BeforeEach
    void setUp() {
        cinterestMapper = new CinterestMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCinterestSample1();
        var actual = cinterestMapper.toEntity(cinterestMapper.toDto(expected));
        assertCinterestAllPropertiesEquals(expected, actual);
    }
}
