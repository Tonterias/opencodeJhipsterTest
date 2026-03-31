package com.opencode.test.service.mapper;

import static com.opencode.test.domain.ConfigVariablesAsserts.*;
import static com.opencode.test.domain.ConfigVariablesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigVariablesMapperTest {

    private ConfigVariablesMapper configVariablesMapper;

    @BeforeEach
    void setUp() {
        configVariablesMapper = new ConfigVariablesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getConfigVariablesSample1();
        var actual = configVariablesMapper.toEntity(configVariablesMapper.toDto(expected));
        assertConfigVariablesAllPropertiesEquals(expected, actual);
    }
}
