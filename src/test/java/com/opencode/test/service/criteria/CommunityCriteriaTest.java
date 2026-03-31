package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CommunityCriteriaTest {

    @Test
    void newCommunityCriteriaHasAllFiltersNullTest() {
        var communityCriteria = new CommunityCriteria();
        assertThat(communityCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void communityCriteriaFluentMethodsCreatesFiltersTest() {
        var communityCriteria = new CommunityCriteria();

        setAllFilters(communityCriteria);

        assertThat(communityCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void communityCriteriaCopyCreatesNullFilterTest() {
        var communityCriteria = new CommunityCriteria();
        var copy = communityCriteria.copy();

        assertThat(communityCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(communityCriteria)
        );
    }

    @Test
    void communityCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var communityCriteria = new CommunityCriteria();
        setAllFilters(communityCriteria);

        var copy = communityCriteria.copy();

        assertThat(communityCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(communityCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var communityCriteria = new CommunityCriteria();

        assertThat(communityCriteria).hasToString("CommunityCriteria{}");
    }

    private static void setAllFilters(CommunityCriteria communityCriteria) {
        communityCriteria.id();
        communityCriteria.creationDate();
        communityCriteria.communityName();
        communityCriteria.communityDescription();
        communityCriteria.isActive();
        communityCriteria.blogId();
        communityCriteria.cfollowedId();
        communityCriteria.cfollowingId();
        communityCriteria.cblockeduserId();
        communityCriteria.cblockinguserId();
        communityCriteria.appuserId();
        communityCriteria.cinterestId();
        communityCriteria.cactivityId();
        communityCriteria.ccelebId();
        communityCriteria.distinct();
    }

    private static Condition<CommunityCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCreationDate()) &&
                condition.apply(criteria.getCommunityName()) &&
                condition.apply(criteria.getCommunityDescription()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getBlogId()) &&
                condition.apply(criteria.getCfollowedId()) &&
                condition.apply(criteria.getCfollowingId()) &&
                condition.apply(criteria.getCblockeduserId()) &&
                condition.apply(criteria.getCblockinguserId()) &&
                condition.apply(criteria.getAppuserId()) &&
                condition.apply(criteria.getCinterestId()) &&
                condition.apply(criteria.getCactivityId()) &&
                condition.apply(criteria.getCcelebId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CommunityCriteria> copyFiltersAre(CommunityCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCreationDate(), copy.getCreationDate()) &&
                condition.apply(criteria.getCommunityName(), copy.getCommunityName()) &&
                condition.apply(criteria.getCommunityDescription(), copy.getCommunityDescription()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getBlogId(), copy.getBlogId()) &&
                condition.apply(criteria.getCfollowedId(), copy.getCfollowedId()) &&
                condition.apply(criteria.getCfollowingId(), copy.getCfollowingId()) &&
                condition.apply(criteria.getCblockeduserId(), copy.getCblockeduserId()) &&
                condition.apply(criteria.getCblockinguserId(), copy.getCblockinguserId()) &&
                condition.apply(criteria.getAppuserId(), copy.getAppuserId()) &&
                condition.apply(criteria.getCinterestId(), copy.getCinterestId()) &&
                condition.apply(criteria.getCactivityId(), copy.getCactivityId()) &&
                condition.apply(criteria.getCcelebId(), copy.getCcelebId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
