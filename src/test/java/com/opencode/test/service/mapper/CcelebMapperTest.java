package com.opencode.test.service.mapper;

import static com.opencode.test.domain.CcelebAsserts.*;
import static com.opencode.test.domain.CcelebTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CcelebMapperTest {

    private CcelebMapper ccelebMapper;

    @BeforeEach
    void setUp() {
        ccelebMapper = new CcelebMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCcelebSample1();
        var actual = ccelebMapper.toEntity(ccelebMapper.toDto(expected));
        assertCcelebAllPropertiesEquals(expected, actual);
    }
}
