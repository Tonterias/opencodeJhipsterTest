package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CinterestCriteriaTest {

    @Test
    void newCinterestCriteriaHasAllFiltersNullTest() {
        var cinterestCriteria = new CinterestCriteria();
        assertThat(cinterestCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void cinterestCriteriaFluentMethodsCreatesFiltersTest() {
        var cinterestCriteria = new CinterestCriteria();

        setAllFilters(cinterestCriteria);

        assertThat(cinterestCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void cinterestCriteriaCopyCreatesNullFilterTest() {
        var cinterestCriteria = new CinterestCriteria();
        var copy = cinterestCriteria.copy();

        assertThat(cinterestCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(cinterestCriteria)
        );
    }

    @Test
    void cinterestCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var cinterestCriteria = new CinterestCriteria();
        setAllFilters(cinterestCriteria);

        var copy = cinterestCriteria.copy();

        assertThat(cinterestCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(cinterestCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var cinterestCriteria = new CinterestCriteria();

        assertThat(cinterestCriteria).hasToString("CinterestCriteria{}");
    }

    private static void setAllFilters(CinterestCriteria cinterestCriteria) {
        cinterestCriteria.id();
        cinterestCriteria.interestName();
        cinterestCriteria.communityId();
        cinterestCriteria.distinct();
    }

    private static Condition<CinterestCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInterestName()) &&
                condition.apply(criteria.getCommunityId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CinterestCriteria> copyFiltersAre(CinterestCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInterestName(), copy.getInterestName()) &&
                condition.apply(criteria.getCommunityId(), copy.getCommunityId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
