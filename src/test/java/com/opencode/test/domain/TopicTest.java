package com.opencode.test.domain;

import static com.opencode.test.domain.PostTestSamples.*;
import static com.opencode.test.domain.TopicTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TopicTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Topic.class);
        Topic topic1 = getTopicSample1();
        Topic topic2 = new Topic();
        assertThat(topic1).isNotEqualTo(topic2);

        topic2.setId(topic1.getId());
        assertThat(topic1).isEqualTo(topic2);

        topic2 = getTopicSample2();
        assertThat(topic1).isNotEqualTo(topic2);
    }

    @Test
    void postTest() {
        Topic topic = getTopicRandomSampleGenerator();
        Post postBack = getPostRandomSampleGenerator();

        topic.addPost(postBack);
        assertThat(topic.getPosts()).containsOnly(postBack);

        topic.removePost(postBack);
        assertThat(topic.getPosts()).doesNotContain(postBack);

        topic.posts(new HashSet<>(Set.of(postBack)));
        assertThat(topic.getPosts()).containsOnly(postBack);

        topic.setPosts(new HashSet<>());
        assertThat(topic.getPosts()).doesNotContain(postBack);
    }
}
