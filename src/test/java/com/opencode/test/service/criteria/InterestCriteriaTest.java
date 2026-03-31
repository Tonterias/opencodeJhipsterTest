package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class InterestCriteriaTest {

    @Test
    void newInterestCriteriaHasAllFiltersNullTest() {
        var interestCriteria = new InterestCriteria();
        assertThat(interestCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void interestCriteriaFluentMethodsCreatesFiltersTest() {
        var interestCriteria = new InterestCriteria();

        setAllFilters(interestCriteria);

        assertThat(interestCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void interestCriteriaCopyCreatesNullFilterTest() {
        var interestCriteria = new InterestCriteria();
        var copy = interestCriteria.copy();

        assertThat(interestCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(interestCriteria)
        );
    }

    @Test
    void interestCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var interestCriteria = new InterestCriteria();
        setAllFilters(interestCriteria);

        var copy = interestCriteria.copy();

        assertThat(interestCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(interestCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var interestCriteria = new InterestCriteria();

        assertThat(interestCriteria).hasToString("InterestCriteria{}");
    }

    private static void setAllFilters(InterestCriteria interestCriteria) {
        interestCriteria.id();
        interestCriteria.interestName();
        interestCriteria.appuserId();
        interestCriteria.distinct();
    }

    private static Condition<InterestCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInterestName()) &&
                condition.apply(criteria.getAppuserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<InterestCriteria> copyFiltersAre(InterestCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInterestName(), copy.getInterestName()) &&
                condition.apply(criteria.getAppuserId(), copy.getAppuserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
