package com.opencode.test.domain;

import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.BlogTestSamples.*;
import static com.opencode.test.domain.CommunityTestSamples.*;
import static com.opencode.test.domain.PostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BlogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Blog.class);
        Blog blog1 = getBlogSample1();
        Blog blog2 = new Blog();
        assertThat(blog1).isNotEqualTo(blog2);

        blog2.setId(blog1.getId());
        assertThat(blog1).isEqualTo(blog2);

        blog2 = getBlogSample2();
        assertThat(blog1).isNotEqualTo(blog2);
    }

    @Test
    void postTest() {
        Blog blog = getBlogRandomSampleGenerator();
        Post postBack = getPostRandomSampleGenerator();

        blog.addPost(postBack);
        assertThat(blog.getPosts()).containsOnly(postBack);
        assertThat(postBack.getBlog()).isEqualTo(blog);

        blog.removePost(postBack);
        assertThat(blog.getPosts()).doesNotContain(postBack);
        assertThat(postBack.getBlog()).isNull();

        blog.posts(new HashSet<>(Set.of(postBack)));
        assertThat(blog.getPosts()).containsOnly(postBack);
        assertThat(postBack.getBlog()).isEqualTo(blog);

        blog.setPosts(new HashSet<>());
        assertThat(blog.getPosts()).doesNotContain(postBack);
        assertThat(postBack.getBlog()).isNull();
    }

    @Test
    void appuserTest() {
        Blog blog = getBlogRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        blog.setAppuser(appuserBack);
        assertThat(blog.getAppuser()).isEqualTo(appuserBack);

        blog.appuser(null);
        assertThat(blog.getAppuser()).isNull();
    }

    @Test
    void communityTest() {
        Blog blog = getBlogRandomSampleGenerator();
        Community communityBack = getCommunityRandomSampleGenerator();

        blog.setCommunity(communityBack);
        assertThat(blog.getCommunity()).isEqualTo(communityBack);

        blog.community(null);
        assertThat(blog.getCommunity()).isNull();
    }
}
