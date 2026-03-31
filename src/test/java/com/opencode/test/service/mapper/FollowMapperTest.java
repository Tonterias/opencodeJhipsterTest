package com.opencode.test.service.mapper;

import static com.opencode.test.domain.FollowAsserts.*;
import static com.opencode.test.domain.FollowTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FollowMapperTest {

    private FollowMapper followMapper;

    @BeforeEach
    void setUp() {
        followMapper = new FollowMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFollowSample1();
        var actual = followMapper.toEntity(followMapper.toDto(expected));
        assertFollowAllPropertiesEquals(expected, actual);
    }
}
