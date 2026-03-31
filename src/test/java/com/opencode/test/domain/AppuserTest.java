package com.opencode.test.domain;

import static com.opencode.test.domain.ActivityTestSamples.*;
import static com.opencode.test.domain.AppphotoTestSamples.*;
import static com.opencode.test.domain.AppuserTestSamples.*;
import static com.opencode.test.domain.BlockuserTestSamples.*;
import static com.opencode.test.domain.BlogTestSamples.*;
import static com.opencode.test.domain.CelebTestSamples.*;
import static com.opencode.test.domain.CommentTestSamples.*;
import static com.opencode.test.domain.CommunityTestSamples.*;
import static com.opencode.test.domain.FollowTestSamples.*;
import static com.opencode.test.domain.InterestTestSamples.*;
import static com.opencode.test.domain.NotificationTestSamples.*;
import static com.opencode.test.domain.PostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.opencode.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AppuserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appuser.class);
        Appuser appuser1 = getAppuserSample1();
        Appuser appuser2 = new Appuser();
        assertThat(appuser1).isNotEqualTo(appuser2);

        appuser2.setId(appuser1.getId());
        assertThat(appuser1).isEqualTo(appuser2);

        appuser2 = getAppuserSample2();
        assertThat(appuser1).isNotEqualTo(appuser2);
    }

    @Test
    void blogTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Blog blogBack = getBlogRandomSampleGenerator();

        appuser.addBlog(blogBack);
        assertThat(appuser.getBlogs()).containsOnly(blogBack);
        assertThat(blogBack.getAppuser()).isEqualTo(appuser);

        appuser.removeBlog(blogBack);
        assertThat(appuser.getBlogs()).doesNotContain(blogBack);
        assertThat(blogBack.getAppuser()).isNull();

        appuser.blogs(new HashSet<>(Set.of(blogBack)));
        assertThat(appuser.getBlogs()).containsOnly(blogBack);
        assertThat(blogBack.getAppuser()).isEqualTo(appuser);

        appuser.setBlogs(new HashSet<>());
        assertThat(appuser.getBlogs()).doesNotContain(blogBack);
        assertThat(blogBack.getAppuser()).isNull();
    }

    @Test
    void communityTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Community communityBack = getCommunityRandomSampleGenerator();

        appuser.addCommunity(communityBack);
        assertThat(appuser.getCommunities()).containsOnly(communityBack);
        assertThat(communityBack.getAppuser()).isEqualTo(appuser);

        appuser.removeCommunity(communityBack);
        assertThat(appuser.getCommunities()).doesNotContain(communityBack);
        assertThat(communityBack.getAppuser()).isNull();

        appuser.communities(new HashSet<>(Set.of(communityBack)));
        assertThat(appuser.getCommunities()).containsOnly(communityBack);
        assertThat(communityBack.getAppuser()).isEqualTo(appuser);

        appuser.setCommunities(new HashSet<>());
        assertThat(appuser.getCommunities()).doesNotContain(communityBack);
        assertThat(communityBack.getAppuser()).isNull();
    }

    @Test
    void notificationTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Notification notificationBack = getNotificationRandomSampleGenerator();

        appuser.addNotification(notificationBack);
        assertThat(appuser.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getAppuser()).isEqualTo(appuser);

        appuser.removeNotification(notificationBack);
        assertThat(appuser.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getAppuser()).isNull();

        appuser.notifications(new HashSet<>(Set.of(notificationBack)));
        assertThat(appuser.getNotifications()).containsOnly(notificationBack);
        assertThat(notificationBack.getAppuser()).isEqualTo(appuser);

        appuser.setNotifications(new HashSet<>());
        assertThat(appuser.getNotifications()).doesNotContain(notificationBack);
        assertThat(notificationBack.getAppuser()).isNull();
    }

    @Test
    void commentTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Comment commentBack = getCommentRandomSampleGenerator();

        appuser.addComment(commentBack);
        assertThat(appuser.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getAppuser()).isEqualTo(appuser);

        appuser.removeComment(commentBack);
        assertThat(appuser.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getAppuser()).isNull();

        appuser.comments(new HashSet<>(Set.of(commentBack)));
        assertThat(appuser.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getAppuser()).isEqualTo(appuser);

        appuser.setComments(new HashSet<>());
        assertThat(appuser.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getAppuser()).isNull();
    }

    @Test
    void postTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Post postBack = getPostRandomSampleGenerator();

        appuser.addPost(postBack);
        assertThat(appuser.getPosts()).containsOnly(postBack);
        assertThat(postBack.getAppuser()).isEqualTo(appuser);

        appuser.removePost(postBack);
        assertThat(appuser.getPosts()).doesNotContain(postBack);
        assertThat(postBack.getAppuser()).isNull();

        appuser.posts(new HashSet<>(Set.of(postBack)));
        assertThat(appuser.getPosts()).containsOnly(postBack);
        assertThat(postBack.getAppuser()).isEqualTo(appuser);

        appuser.setPosts(new HashSet<>());
        assertThat(appuser.getPosts()).doesNotContain(postBack);
        assertThat(postBack.getAppuser()).isNull();
    }

    @Test
    void followedTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Follow followBack = getFollowRandomSampleGenerator();

        appuser.addFollowed(followBack);
        assertThat(appuser.getFolloweds()).containsOnly(followBack);
        assertThat(followBack.getFollowed()).isEqualTo(appuser);

        appuser.removeFollowed(followBack);
        assertThat(appuser.getFolloweds()).doesNotContain(followBack);
        assertThat(followBack.getFollowed()).isNull();

        appuser.followeds(new HashSet<>(Set.of(followBack)));
        assertThat(appuser.getFolloweds()).containsOnly(followBack);
        assertThat(followBack.getFollowed()).isEqualTo(appuser);

        appuser.setFolloweds(new HashSet<>());
        assertThat(appuser.getFolloweds()).doesNotContain(followBack);
        assertThat(followBack.getFollowed()).isNull();
    }

    @Test
    void followingTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Follow followBack = getFollowRandomSampleGenerator();

        appuser.addFollowing(followBack);
        assertThat(appuser.getFollowings()).containsOnly(followBack);
        assertThat(followBack.getFollowing()).isEqualTo(appuser);

        appuser.removeFollowing(followBack);
        assertThat(appuser.getFollowings()).doesNotContain(followBack);
        assertThat(followBack.getFollowing()).isNull();

        appuser.followings(new HashSet<>(Set.of(followBack)));
        assertThat(appuser.getFollowings()).containsOnly(followBack);
        assertThat(followBack.getFollowing()).isEqualTo(appuser);

        appuser.setFollowings(new HashSet<>());
        assertThat(appuser.getFollowings()).doesNotContain(followBack);
        assertThat(followBack.getFollowing()).isNull();
    }

    @Test
    void blockeduserTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Blockuser blockuserBack = getBlockuserRandomSampleGenerator();

        appuser.addBlockeduser(blockuserBack);
        assertThat(appuser.getBlockedusers()).containsOnly(blockuserBack);
        assertThat(blockuserBack.getBlockeduser()).isEqualTo(appuser);

        appuser.removeBlockeduser(blockuserBack);
        assertThat(appuser.getBlockedusers()).doesNotContain(blockuserBack);
        assertThat(blockuserBack.getBlockeduser()).isNull();

        appuser.blockedusers(new HashSet<>(Set.of(blockuserBack)));
        assertThat(appuser.getBlockedusers()).containsOnly(blockuserBack);
        assertThat(blockuserBack.getBlockeduser()).isEqualTo(appuser);

        appuser.setBlockedusers(new HashSet<>());
        assertThat(appuser.getBlockedusers()).doesNotContain(blockuserBack);
        assertThat(blockuserBack.getBlockeduser()).isNull();
    }

    @Test
    void blockinguserTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Blockuser blockuserBack = getBlockuserRandomSampleGenerator();

        appuser.addBlockinguser(blockuserBack);
        assertThat(appuser.getBlockingusers()).containsOnly(blockuserBack);
        assertThat(blockuserBack.getBlockinguser()).isEqualTo(appuser);

        appuser.removeBlockinguser(blockuserBack);
        assertThat(appuser.getBlockingusers()).doesNotContain(blockuserBack);
        assertThat(blockuserBack.getBlockinguser()).isNull();

        appuser.blockingusers(new HashSet<>(Set.of(blockuserBack)));
        assertThat(appuser.getBlockingusers()).containsOnly(blockuserBack);
        assertThat(blockuserBack.getBlockinguser()).isEqualTo(appuser);

        appuser.setBlockingusers(new HashSet<>());
        assertThat(appuser.getBlockingusers()).doesNotContain(blockuserBack);
        assertThat(blockuserBack.getBlockinguser()).isNull();
    }

    @Test
    void appphotoTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Appphoto appphotoBack = getAppphotoRandomSampleGenerator();

        appuser.setAppphoto(appphotoBack);
        assertThat(appuser.getAppphoto()).isEqualTo(appphotoBack);
        assertThat(appphotoBack.getAppuser()).isEqualTo(appuser);

        appuser.appphoto(null);
        assertThat(appuser.getAppphoto()).isNull();
        assertThat(appphotoBack.getAppuser()).isNull();
    }

    @Test
    void interestTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Interest interestBack = getInterestRandomSampleGenerator();

        appuser.addInterest(interestBack);
        assertThat(appuser.getInterests()).containsOnly(interestBack);
        assertThat(interestBack.getAppusers()).containsOnly(appuser);

        appuser.removeInterest(interestBack);
        assertThat(appuser.getInterests()).doesNotContain(interestBack);
        assertThat(interestBack.getAppusers()).doesNotContain(appuser);

        appuser.interests(new HashSet<>(Set.of(interestBack)));
        assertThat(appuser.getInterests()).containsOnly(interestBack);
        assertThat(interestBack.getAppusers()).containsOnly(appuser);

        appuser.setInterests(new HashSet<>());
        assertThat(appuser.getInterests()).doesNotContain(interestBack);
        assertThat(interestBack.getAppusers()).doesNotContain(appuser);
    }

    @Test
    void activityTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Activity activityBack = getActivityRandomSampleGenerator();

        appuser.addActivity(activityBack);
        assertThat(appuser.getActivities()).containsOnly(activityBack);
        assertThat(activityBack.getAppusers()).containsOnly(appuser);

        appuser.removeActivity(activityBack);
        assertThat(appuser.getActivities()).doesNotContain(activityBack);
        assertThat(activityBack.getAppusers()).doesNotContain(appuser);

        appuser.activities(new HashSet<>(Set.of(activityBack)));
        assertThat(appuser.getActivities()).containsOnly(activityBack);
        assertThat(activityBack.getAppusers()).containsOnly(appuser);

        appuser.setActivities(new HashSet<>());
        assertThat(appuser.getActivities()).doesNotContain(activityBack);
        assertThat(activityBack.getAppusers()).doesNotContain(appuser);
    }

    @Test
    void celebTest() {
        Appuser appuser = getAppuserRandomSampleGenerator();
        Celeb celebBack = getCelebRandomSampleGenerator();

        appuser.addCeleb(celebBack);
        assertThat(appuser.getCelebs()).containsOnly(celebBack);
        assertThat(celebBack.getAppusers()).containsOnly(appuser);

        appuser.removeCeleb(celebBack);
        assertThat(appuser.getCelebs()).doesNotContain(celebBack);
        assertThat(celebBack.getAppusers()).doesNotContain(appuser);

        appuser.celebs(new HashSet<>(Set.of(celebBack)));
        assertThat(appuser.getCelebs()).containsOnly(celebBack);
        assertThat(celebBack.getAppusers()).containsOnly(appuser);

        appuser.setCelebs(new HashSet<>());
        assertThat(appuser.getCelebs()).doesNotContain(celebBack);
        assertThat(celebBack.getAppusers()).doesNotContain(appuser);
    }
}
