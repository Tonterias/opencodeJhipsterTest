package com.opencode.test.service.mapper;

import static com.opencode.test.domain.TopicAsserts.*;
import static com.opencode.test.domain.TopicTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TopicMapperTest {

    private TopicMapper topicMapper;

    @BeforeEach
    void setUp() {
        topicMapper = new TopicMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTopicSample1();
        var actual = topicMapper.toEntity(topicMapper.toDto(expected));
        assertTopicAllPropertiesEquals(expected, actual);
    }
}
