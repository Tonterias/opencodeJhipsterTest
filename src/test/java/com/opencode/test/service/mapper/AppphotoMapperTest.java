package com.opencode.test.service.mapper;

import static com.opencode.test.domain.AppphotoAsserts.*;
import static com.opencode.test.domain.AppphotoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppphotoMapperTest {

    private AppphotoMapper appphotoMapper;

    @BeforeEach
    void setUp() {
        appphotoMapper = new AppphotoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAppphotoSample1();
        var actual = appphotoMapper.toEntity(appphotoMapper.toDto(expected));
        assertAppphotoAllPropertiesEquals(expected, actual);
    }
}
