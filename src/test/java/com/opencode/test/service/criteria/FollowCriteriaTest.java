package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FollowCriteriaTest {

    @Test
    void newFollowCriteriaHasAllFiltersNullTest() {
        var followCriteria = new FollowCriteria();
        assertThat(followCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void followCriteriaFluentMethodsCreatesFiltersTest() {
        var followCriteria = new FollowCriteria();

        setAllFilters(followCriteria);

        assertThat(followCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void followCriteriaCopyCreatesNullFilterTest() {
        var followCriteria = new FollowCriteria();
        var copy = followCriteria.copy();

        assertThat(followCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(followCriteria)
        );
    }

    @Test
    void followCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var followCriteria = new FollowCriteria();
        setAllFilters(followCriteria);

        var copy = followCriteria.copy();

        assertThat(followCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(followCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var followCriteria = new FollowCriteria();

        assertThat(followCriteria).hasToString("FollowCriteria{}");
    }

    private static void setAllFilters(FollowCriteria followCriteria) {
        followCriteria.id();
        followCriteria.creationDate();
        followCriteria.followedId();
        followCriteria.followingId();
        followCriteria.cfollowedId();
        followCriteria.cfollowingId();
        followCriteria.distinct();
    }

    private static Condition<FollowCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCreationDate()) &&
                condition.apply(criteria.getFollowedId()) &&
                condition.apply(criteria.getFollowingId()) &&
                condition.apply(criteria.getCfollowedId()) &&
                condition.apply(criteria.getCfollowingId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FollowCriteria> copyFiltersAre(FollowCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCreationDate(), copy.getCreationDate()) &&
                condition.apply(criteria.getFollowedId(), copy.getFollowedId()) &&
                condition.apply(criteria.getFollowingId(), copy.getFollowingId()) &&
                condition.apply(criteria.getCfollowedId(), copy.getCfollowedId()) &&
                condition.apply(criteria.getCfollowingId(), copy.getCfollowingId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
