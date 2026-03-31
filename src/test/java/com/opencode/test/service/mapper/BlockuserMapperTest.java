package com.opencode.test.service.mapper;

import static com.opencode.test.domain.BlockuserAsserts.*;
import static com.opencode.test.domain.BlockuserTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlockuserMapperTest {

    private BlockuserMapper blockuserMapper;

    @BeforeEach
    void setUp() {
        blockuserMapper = new BlockuserMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBlockuserSample1();
        var actual = blockuserMapper.toEntity(blockuserMapper.toDto(expected));
        assertBlockuserAllPropertiesEquals(expected, actual);
    }
}
