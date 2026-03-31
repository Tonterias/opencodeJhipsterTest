package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ActivityCriteriaTest {

    @Test
    void newActivityCriteriaHasAllFiltersNullTest() {
        var activityCriteria = new ActivityCriteria();
        assertThat(activityCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void activityCriteriaFluentMethodsCreatesFiltersTest() {
        var activityCriteria = new ActivityCriteria();

        setAllFilters(activityCriteria);

        assertThat(activityCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void activityCriteriaCopyCreatesNullFilterTest() {
        var activityCriteria = new ActivityCriteria();
        var copy = activityCriteria.copy();

        assertThat(activityCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(activityCriteria)
        );
    }

    @Test
    void activityCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var activityCriteria = new ActivityCriteria();
        setAllFilters(activityCriteria);

        var copy = activityCriteria.copy();

        assertThat(activityCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(activityCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var activityCriteria = new ActivityCriteria();

        assertThat(activityCriteria).hasToString("ActivityCriteria{}");
    }

    private static void setAllFilters(ActivityCriteria activityCriteria) {
        activityCriteria.id();
        activityCriteria.activityName();
        activityCriteria.appuserId();
        activityCriteria.distinct();
    }

    private static Condition<ActivityCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getActivityName()) &&
                condition.apply(criteria.getAppuserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ActivityCriteria> copyFiltersAre(ActivityCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getActivityName(), copy.getActivityName()) &&
                condition.apply(criteria.getAppuserId(), copy.getAppuserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
