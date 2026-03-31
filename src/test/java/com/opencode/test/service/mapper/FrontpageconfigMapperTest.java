package com.opencode.test.service.mapper;

import static com.opencode.test.domain.FrontpageconfigAsserts.*;
import static com.opencode.test.domain.FrontpageconfigTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FrontpageconfigMapperTest {

    private FrontpageconfigMapper frontpageconfigMapper;

    @BeforeEach
    void setUp() {
        frontpageconfigMapper = new FrontpageconfigMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFrontpageconfigSample1();
        var actual = frontpageconfigMapper.toEntity(frontpageconfigMapper.toDto(expected));
        assertFrontpageconfigAllPropertiesEquals(expected, actual);
    }
}
