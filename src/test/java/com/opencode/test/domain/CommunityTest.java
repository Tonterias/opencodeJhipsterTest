package com.opencode.test.domain;

import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.BlockuserTestSamples.*;
import static com.opencode.test.domain.BlogTestSamples.*;
import static com.opencode.test.domain.CactivityTestSamples.*;
import static com.opencode.test.domain.CcelebTestSamples.*;
import static com.opencode.test.domain.CinterestTestSamples.*;
import static com.opencode.test.domain.CommunityTestSamples.*;
import static com.opencode.test.domain.FollowTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CommunityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Community.class);
        Community community1 = getCommunitySample1();
        Community community2 = new Community();
        assertThat(community1).isNotEqualTo(community2);

        community2.setId(community1.getId());
        assertThat(community1).isEqualTo(community2);

        community2 = getCommunitySample2();
        assertThat(community1).isNotEqualTo(community2);
    }

    @Test
    void blogTest() {
        Community community = getCommunityRandomSampleGenerator();
        Blog blogBack = getBlogRandomSampleGenerator();

        community.addBlog(blogBack);
        assertThat(community.getBlogs()).containsOnly(blogBack);
        assertThat(blogBack.getCommunity()).isEqualTo(community);

        community.removeBlog(blogBack);
        assertThat(community.getBlogs()).doesNotContain(blogBack);
        assertThat(blogBack.getCommunity()).isNull();

        community.blogs(new HashSet<>(Set.of(blogBack)));
        assertThat(community.getBlogs()).containsOnly(blogBack);
        assertThat(blogBack.getCommunity()).isEqualTo(community);

        community.setBlogs(new HashSet<>());
        assertThat(community.getBlogs()).doesNotContain(blogBack);
        assertThat(blogBack.getCommunity()).isNull();
    }

    @Test
    void cfollowedTest() {
        Community community = getCommunityRandomSampleGenerator();
        Follow followBack = getFollowRandomSampleGenerator();

        community.addCfollowed(followBack);
        assertThat(community.getCfolloweds()).containsOnly(followBack);
        assertThat(followBack.getCfollowed()).isEqualTo(community);

        community.removeCfollowed(followBack);
        assertThat(community.getCfolloweds()).doesNotContain(followBack);
        assertThat(followBack.getCfollowed()).isNull();

        community.cfolloweds(new HashSet<>(Set.of(followBack)));
        assertThat(community.getCfolloweds()).containsOnly(followBack);
        assertThat(followBack.getCfollowed()).isEqualTo(community);

        community.setCfolloweds(new HashSet<>());
        assertThat(community.getCfolloweds()).doesNotContain(followBack);
        assertThat(followBack.getCfollowed()).isNull();
    }

    @Test
    void cfollowingTest() {
        Community community = getCommunityRandomSampleGenerator();
        Follow followBack = getFollowRandomSampleGenerator();

        community.addCfollowing(followBack);
        assertThat(community.getCfollowings()).containsOnly(followBack);
        assertThat(followBack.getCfollowing()).isEqualTo(community);

        community.removeCfollowing(followBack);
        assertThat(community.getCfollowings()).doesNotContain(followBack);
        assertThat(followBack.getCfollowing()).isNull();

        community.cfollowings(new HashSet<>(Set.of(followBack)));
        assertThat(community.getCfollowings()).containsOnly(followBack);
        assertThat(followBack.getCfollowing()).isEqualTo(community);

        community.setCfollowings(new HashSet<>());
        assertThat(community.getCfollowings()).doesNotContain(followBack);
        assertThat(followBack.getCfollowing()).isNull();
    }

    @Test
    void cblockeduserTest() {
        Community community = getCommunityRandomSampleGenerator();
        Blockuser blockuserBack = getBlockuserRandomSampleGenerator();

        community.addCblockeduser(blockuserBack);
        assertThat(community.getCblockedusers()).containsOnly(blockuserBack);
        assertThat(blockuserBack.getCblockeduser()).isEqualTo(community);

        community.removeCblockeduser(blockuserBack);
        assertThat(community.getCblockedusers()).doesNotContain(blockuserBack);
        assertThat(blockuserBack.getCblockeduser()).isNull();

        community.cblockedusers(new HashSet<>(Set.of(blockuserBack)));
        assertThat(community.getCblockedusers()).containsOnly(blockuserBack);
        assertThat(blockuserBack.getCblockeduser()).isEqualTo(community);

        community.setCblockedusers(new HashSet<>());
        assertThat(community.getCblockedusers()).doesNotContain(blockuserBack);
        assertThat(blockuserBack.getCblockeduser()).isNull();
    }

    @Test
    void cblockinguserTest() {
        Community community = getCommunityRandomSampleGenerator();
        Blockuser blockuserBack = getBlockuserRandomSampleGenerator();

        community.addCblockinguser(blockuserBack);
        assertThat(community.getCblockingusers()).containsOnly(blockuserBack);
        assertThat(blockuserBack.getCblockinguser()).isEqualTo(community);

        community.removeCblockinguser(blockuserBack);
        assertThat(community.getCblockingusers()).doesNotContain(blockuserBack);
        assertThat(blockuserBack.getCblockinguser()).isNull();

        community.cblockingusers(new HashSet<>(Set.of(blockuserBack)));
        assertThat(community.getCblockingusers()).containsOnly(blockuserBack);
        assertThat(blockuserBack.getCblockinguser()).isEqualTo(community);

        community.setCblockingusers(new HashSet<>());
        assertThat(community.getCblockingusers()).doesNotContain(blockuserBack);
        assertThat(blockuserBack.getCblockinguser()).isNull();
    }

    @Test
    void appuserTest() {
        Community community = getCommunityRandomSampleGenerator();
        Appuser appuserBack = getAppuserRandomSampleGenerator();

        community.setAppuser(appuserBack);
        assertThat(community.getAppuser()).isEqualTo(appuserBack);

        community.appuser(null);
        assertThat(community.getAppuser()).isNull();
    }

    @Test
    void cinterestTest() {
        Community community = getCommunityRandomSampleGenerator();
        Cinterest cinterestBack = getCinterestRandomSampleGenerator();

        community.addCinterest(cinterestBack);
        assertThat(community.getCinterests()).containsOnly(cinterestBack);
        assertThat(cinterestBack.getCommunities()).containsOnly(community);

        community.removeCinterest(cinterestBack);
        assertThat(community.getCinterests()).doesNotContain(cinterestBack);
        assertThat(cinterestBack.getCommunities()).doesNotContain(community);

        community.cinterests(new HashSet<>(Set.of(cinterestBack)));
        assertThat(community.getCinterests()).containsOnly(cinterestBack);
        assertThat(cinterestBack.getCommunities()).containsOnly(community);

        community.setCinterests(new HashSet<>());
        assertThat(community.getCinterests()).doesNotContain(cinterestBack);
        assertThat(cinterestBack.getCommunities()).doesNotContain(community);
    }

    @Test
    void cactivityTest() {
        Community community = getCommunityRandomSampleGenerator();
        Cactivity cactivityBack = getCactivityRandomSampleGenerator();

        community.addCactivity(cactivityBack);
        assertThat(community.getCactivities()).containsOnly(cactivityBack);
        assertThat(cactivityBack.getCommunities()).containsOnly(community);

        community.removeCactivity(cactivityBack);
        assertThat(community.getCactivities()).doesNotContain(cactivityBack);
        assertThat(cactivityBack.getCommunities()).doesNotContain(community);

        community.cactivities(new HashSet<>(Set.of(cactivityBack)));
        assertThat(community.getCactivities()).containsOnly(cactivityBack);
        assertThat(cactivityBack.getCommunities()).containsOnly(community);

        community.setCactivities(new HashSet<>());
        assertThat(community.getCactivities()).doesNotContain(cactivityBack);
        assertThat(cactivityBack.getCommunities()).doesNotContain(community);
    }

    @Test
    void ccelebTest() {
        Community community = getCommunityRandomSampleGenerator();
        Cceleb ccelebBack = getCcelebRandomSampleGenerator();

        community.addCceleb(ccelebBack);
        assertThat(community.getCcelebs()).containsOnly(ccelebBack);
        assertThat(ccelebBack.getCommunities()).containsOnly(community);

        community.removeCceleb(ccelebBack);
        assertThat(community.getCcelebs()).doesNotContain(ccelebBack);
        assertThat(ccelebBack.getCommunities()).doesNotContain(community);

        community.ccelebs(new HashSet<>(Set.of(ccelebBack)));
        assertThat(community.getCcelebs()).containsOnly(ccelebBack);
        assertThat(ccelebBack.getCommunities()).containsOnly(community);

        community.setCcelebs(new HashSet<>());
        assertThat(community.getCcelebs()).doesNotContain(ccelebBack);
        assertThat(ccelebBack.getCommunities()).doesNotContain(community);
    }
}
