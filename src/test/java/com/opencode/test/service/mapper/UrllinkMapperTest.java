package com.opencode.test.service.mapper;

import static com.opencode.test.domain.UrllinkAsserts.*;
import static com.opencode.test.domain.UrllinkTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrllinkMapperTest {

    private UrllinkMapper urllinkMapper;

    @BeforeEach
    void setUp() {
        urllinkMapper = new UrllinkMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUrllinkSample1();
        var actual = urllinkMapper.toEntity(urllinkMapper.toDto(expected));
        assertUrllinkAllPropertiesEquals(expected, actual);
    }
}
