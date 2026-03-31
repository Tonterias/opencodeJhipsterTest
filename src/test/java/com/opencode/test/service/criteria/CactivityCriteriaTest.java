package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CactivityCriteriaTest {

    @Test
    void newCactivityCriteriaHasAllFiltersNullTest() {
        var cactivityCriteria = new CactivityCriteria();
        assertThat(cactivityCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void cactivityCriteriaFluentMethodsCreatesFiltersTest() {
        var cactivityCriteria = new CactivityCriteria();

        setAllFilters(cactivityCriteria);

        assertThat(cactivityCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void cactivityCriteriaCopyCreatesNullFilterTest() {
        var cactivityCriteria = new CactivityCriteria();
        var copy = cactivityCriteria.copy();

        assertThat(cactivityCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(cactivityCriteria)
        );
    }

    @Test
    void cactivityCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var cactivityCriteria = new CactivityCriteria();
        setAllFilters(cactivityCriteria);

        var copy = cactivityCriteria.copy();

        assertThat(cactivityCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(cactivityCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var cactivityCriteria = new CactivityCriteria();

        assertThat(cactivityCriteria).hasToString("CactivityCriteria{}");
    }

    private static void setAllFilters(CactivityCriteria cactivityCriteria) {
        cactivityCriteria.id();
        cactivityCriteria.activityName();
        cactivityCriteria.communityId();
        cactivityCriteria.distinct();
    }

    private static Condition<CactivityCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getActivityName()) &&
                condition.apply(criteria.getCommunityId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CactivityCriteria> copyFiltersAre(CactivityCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getActivityName(), copy.getActivityName()) &&
                condition.apply(criteria.getCommunityId(), copy.getCommunityId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
