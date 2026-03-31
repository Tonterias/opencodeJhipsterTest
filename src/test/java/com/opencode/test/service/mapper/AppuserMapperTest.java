package com.opencode.test.service.mapper;

import static com.opencode.test.domain.AppuserAsserts.*;
import static com.opencode.test.domain.AppuserTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppuserMapperTest {

    private AppuserMapper appuserMapper;

    @BeforeEach
    void setUp() {
        appuserMapper = new AppuserMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAppuserSample1();
        var actual = appuserMapper.toEntity(appuserMapper.toDto(expected));
        assertAppuserAllPropertiesEquals(expected, actual);
    }
}
