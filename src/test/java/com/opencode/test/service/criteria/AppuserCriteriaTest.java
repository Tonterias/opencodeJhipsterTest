package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AppuserCriteriaTest {

    @Test
    void newAppuserCriteriaHasAllFiltersNullTest() {
        var appuserCriteria = new AppuserCriteria();
        assertThat(appuserCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void appuserCriteriaFluentMethodsCreatesFiltersTest() {
        var appuserCriteria = new AppuserCriteria();

        setAllFilters(appuserCriteria);

        assertThat(appuserCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void appuserCriteriaCopyCreatesNullFilterTest() {
        var appuserCriteria = new AppuserCriteria();
        var copy = appuserCriteria.copy();

        assertThat(appuserCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(appuserCriteria)
        );
    }

    @Test
    void appuserCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var appuserCriteria = new AppuserCriteria();
        setAllFilters(appuserCriteria);

        var copy = appuserCriteria.copy();

        assertThat(appuserCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(appuserCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var appuserCriteria = new AppuserCriteria();

        assertThat(appuserCriteria).hasToString("AppuserCriteria{}");
    }

    private static void setAllFilters(AppuserCriteria appuserCriteria) {
        appuserCriteria.id();
        appuserCriteria.creationDate();
        appuserCriteria.bio();
        appuserCriteria.facebook();
        appuserCriteria.twitter();
        appuserCriteria.linkedin();
        appuserCriteria.instagram();
        appuserCriteria.birthdate();
        appuserCriteria.userId();
        appuserCriteria.blogId();
        appuserCriteria.communityId();
        appuserCriteria.notificationId();
        appuserCriteria.commentId();
        appuserCriteria.postId();
        appuserCriteria.followedId();
        appuserCriteria.followingId();
        appuserCriteria.blockeduserId();
        appuserCriteria.blockinguserId();
        appuserCriteria.appphotoId();
        appuserCriteria.interestId();
        appuserCriteria.activityId();
        appuserCriteria.celebId();
        appuserCriteria.distinct();
    }

    private static Condition<AppuserCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCreationDate()) &&
                condition.apply(criteria.getBio()) &&
                condition.apply(criteria.getFacebook()) &&
                condition.apply(criteria.getTwitter()) &&
                condition.apply(criteria.getLinkedin()) &&
                condition.apply(criteria.getInstagram()) &&
                condition.apply(criteria.getBirthdate()) &&
                condition.apply(criteria.getUserId()) &&
                condition.apply(criteria.getBlogId()) &&
                condition.apply(criteria.getCommunityId()) &&
                condition.apply(criteria.getNotificationId()) &&
                condition.apply(criteria.getCommentId()) &&
                condition.apply(criteria.getPostId()) &&
                condition.apply(criteria.getFollowedId()) &&
                condition.apply(criteria.getFollowingId()) &&
                condition.apply(criteria.getBlockeduserId()) &&
                condition.apply(criteria.getBlockinguserId()) &&
                condition.apply(criteria.getAppphotoId()) &&
                condition.apply(criteria.getInterestId()) &&
                condition.apply(criteria.getActivityId()) &&
                condition.apply(criteria.getCelebId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AppuserCriteria> copyFiltersAre(AppuserCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCreationDate(), copy.getCreationDate()) &&
                condition.apply(criteria.getBio(), copy.getBio()) &&
                condition.apply(criteria.getFacebook(), copy.getFacebook()) &&
                condition.apply(criteria.getTwitter(), copy.getTwitter()) &&
                condition.apply(criteria.getLinkedin(), copy.getLinkedin()) &&
                condition.apply(criteria.getInstagram(), copy.getInstagram()) &&
                condition.apply(criteria.getBirthdate(), copy.getBirthdate()) &&
                condition.apply(criteria.getUserId(), copy.getUserId()) &&
                condition.apply(criteria.getBlogId(), copy.getBlogId()) &&
                condition.apply(criteria.getCommunityId(), copy.getCommunityId()) &&
                condition.apply(criteria.getNotificationId(), copy.getNotificationId()) &&
                condition.apply(criteria.getCommentId(), copy.getCommentId()) &&
                condition.apply(criteria.getPostId(), copy.getPostId()) &&
                condition.apply(criteria.getFollowedId(), copy.getFollowedId()) &&
                condition.apply(criteria.getFollowingId(), copy.getFollowingId()) &&
                condition.apply(criteria.getBlockeduserId(), copy.getBlockeduserId()) &&
                condition.apply(criteria.getBlockinguserId(), copy.getBlockinguserId()) &&
                condition.apply(criteria.getAppphotoId(), copy.getAppphotoId()) &&
                condition.apply(criteria.getInterestId(), copy.getInterestId()) &&
                condition.apply(criteria.getActivityId(), copy.getActivityId()) &&
                condition.apply(criteria.getCelebId(), copy.getCelebId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
