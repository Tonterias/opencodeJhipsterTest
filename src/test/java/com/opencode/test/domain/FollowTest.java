package com.opencode.test.domain;

import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.CommunityTestSamples.*;
import static com.opencode.test.domain.FollowTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FollowTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Follow.class);
        Follow follow1 = getFollowSample1();
        Follow follow2 = new Follow();
        assertThat(follow1).isNotEqualTo(follow2);

        follow2.setId(follow1.getId());
        assertThat(follow1).isEqualTo(follow2);

        follow2 = getFollowSample2();
        assertThat(follow1).isNotEqualTo(follow2);
    }

    @Test
    void followedTest() {
        Follow follow = getFollowRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        follow.setFollowed(appuserBack);
        assertThat(follow.getFollowed()).isEqualTo(appuserBack);

        follow.followed(null);
        assertThat(follow.getFollowed()).isNull();
    }

    @Test
    void followingTest() {
        Follow follow = getFollowRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        follow.setFollowing(appuserBack);
        assertThat(follow.getFollowing()).isEqualTo(appuserBack);

        follow.following(null);
        assertThat(follow.getFollowing()).isNull();
    }

    @Test
    void cfollowedTest() {
        Follow follow = getFollowRandomSampleGenerator();
        Community communityBack = getCommunityRandomSampleGenerator();

        follow.setCfollowed(communityBack);
        assertThat(follow.getCfollowed()).isEqualTo(communityBack);

        follow.cfollowed(null);
        assertThat(follow.getCfollowed()).isNull();
    }

    @Test
    void cfollowingTest() {
        Follow follow = getFollowRandomSampleGenerator();
        Community communityBack = getCommunityRandomSampleGenerator();

        follow.setCfollowing(communityBack);
        assertThat(follow.getCfollowing()).isEqualTo(communityBack);

        follow.cfollowing(null);
        assertThat(follow.getCfollowing()).isNull();
    }
}
