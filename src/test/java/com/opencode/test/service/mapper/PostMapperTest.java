package com.opencode.test.service.mapper;

import static com.opencode.test.domain.PostAsserts.*;
import static com.opencode.test.domain.PostTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostMapperTest {

    private PostMapper postMapper;

    @BeforeEach
    void setUp() {
        postMapper = new PostMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPostSample1();
        var actual = postMapper.toEntity(postMapper.toDto(expected));
        assertPostAllPropertiesEquals(expected, actual);
    }
}
