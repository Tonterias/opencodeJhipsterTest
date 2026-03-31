package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CelebCriteriaTest {

    @Test
    void newCelebCriteriaHasAllFiltersNullTest() {
        var celebCriteria = new CelebCriteria();
        assertThat(celebCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void celebCriteriaFluentMethodsCreatesFiltersTest() {
        var celebCriteria = new CelebCriteria();

        setAllFilters(celebCriteria);

        assertThat(celebCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void celebCriteriaCopyCreatesNullFilterTest() {
        var celebCriteria = new CelebCriteria();
        var copy = celebCriteria.copy();

        assertThat(celebCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(celebCriteria)
        );
    }

    @Test
    void celebCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var celebCriteria = new CelebCriteria();
        setAllFilters(celebCriteria);

        var copy = celebCriteria.copy();

        assertThat(celebCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(celebCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var celebCriteria = new CelebCriteria();

        assertThat(celebCriteria).hasToString("CelebCriteria{}");
    }

    private static void setAllFilters(CelebCriteria celebCriteria) {
        celebCriteria.id();
        celebCriteria.celebName();
        celebCriteria.appuserId();
        celebCriteria.distinct();
    }

    private static Condition<CelebCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCelebName()) &&
                condition.apply(criteria.getAppuserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CelebCriteria> copyFiltersAre(CelebCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCelebName(), copy.getCelebName()) &&
                condition.apply(criteria.getAppuserId(), copy.getAppuserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
