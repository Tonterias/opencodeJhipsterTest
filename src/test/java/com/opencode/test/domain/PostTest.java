package com.opencode.test.domain;

import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.BlogTestSamples.*;
import static com.opencode.test.domain.CommentTestSamples.*;
import static com.opencode.test.domain.PostTestSamples.*;
import static com.opencode.test.domain.TagTestSamples.*;
import static com.opencode.test.domain.TopicTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = getPostSample1();
        Post post2 = new Post();
        assertThat(post1).isNotEqualTo(post2);

        post2.setId(post1.getId());
        assertThat(post1).isEqualTo(post2);

        post2 = getPostSample2();
        assertThat(post1).isNotEqualTo(post2);
    }

    @Test
    void commentTest() {
        Post post = getPostRandomSampleGenerator();
        Comment commentBack = getCommentRandomSampleGenerator();

        post.addComment(commentBack);
        assertThat(post.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getPost()).isEqualTo(post);

        post.removeComment(commentBack);
        assertThat(post.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getPost()).isNull();

        post.comments(new HashSet<>(Set.of(commentBack)));
        assertThat(post.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getPost()).isEqualTo(post);

        post.setComments(new HashSet<>());
        assertThat(post.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getPost()).isNull();
    }

    @Test
    void appuserTest() {
        Post post = getPostRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        post.setAppuser(appuserBack);
        assertThat(post.getAppuser()).isEqualTo(appuserBack);

        post.appuser(null);
        assertThat(post.getAppuser()).isNull();
    }

    @Test
    void blogTest() {
        Post post = getPostRandomSampleGenerator();
        Blog blogBack = getBlogRandomSampleGenerator();

        post.setBlog(blogBack);
        assertThat(post.getBlog()).isEqualTo(blogBack);

        post.blog(null);
        assertThat(post.getBlog()).isNull();
    }

    @Test
    void tagTest() {
        Post post = getPostRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        post.addTag(tagBack);
        assertThat(post.getTags()).containsOnly(tagBack);
        assertThat(tagBack.getPosts()).containsOnly(post);

        post.removeTag(tagBack);
        assertThat(post.getTags()).doesNotContain(tagBack);
        assertThat(tagBack.getPosts()).doesNotContain(post);

        post.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(post.getTags()).containsOnly(tagBack);
        assertThat(tagBack.getPosts()).containsOnly(post);

        post.setTags(new HashSet<>());
        assertThat(post.getTags()).doesNotContain(tagBack);
        assertThat(tagBack.getPosts()).doesNotContain(post);
    }

    @Test
    void topicTest() {
        Post post = getPostRandomSampleGenerator();
        Topic topicBack = getTopicRandomSampleGenerator();

        post.addTopic(topicBack);
        assertThat(post.getTopics()).containsOnly(topicBack);
        assertThat(topicBack.getPosts()).containsOnly(post);

        post.removeTopic(topicBack);
        assertThat(post.getTopics()).doesNotContain(topicBack);
        assertThat(topicBack.getPosts()).doesNotContain(post);

        post.topics(new HashSet<>(Set.of(topicBack)));
        assertThat(post.getTopics()).containsOnly(topicBack);
        assertThat(topicBack.getPosts()).containsOnly(post);

        post.setTopics(new HashSet<>());
        assertThat(post.getTopics()).doesNotContain(topicBack);
        assertThat(topicBack.getPosts()).doesNotContain(post);
    }
}
