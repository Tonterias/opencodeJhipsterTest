package com.opencode.test.domain;

import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.CommentTestSamples.*;
import static com.opencode.test.domain.PostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
        Comment comment1 = getCommentSample1();
        Comment comment2 = new Comment();
        assertThat(comment1).isNotEqualTo(comment2);

        comment2.setId(comment1.getId());
        assertThat(comment1).isEqualTo(comment2);

        comment2 = getCommentSample2();
        assertThat(comment1).isNotEqualTo(comment2);
    }

    @Test
    void appuserTest() {
        Comment comment = getCommentRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        comment.setAppuser(appuserBack);
        assertThat(comment.getAppuser()).isEqualTo(appuserBack);

        comment.appuser(null);
        assertThat(comment.getAppuser()).isNull();
    }

    @Test
    void postTest() {
        Comment comment = getCommentRandomSampleGenerator();
        Post postBack = getPostRandomSampleGenerator();

        comment.setPost(postBack);
        assertThat(comment.getPost()).isEqualTo(postBack);

        comment.post(null);
        assertThat(comment.getPost()).isNull();
    }
}
