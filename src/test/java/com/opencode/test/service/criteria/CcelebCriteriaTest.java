package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CcelebCriteriaTest {

    @Test
    void newCcelebCriteriaHasAllFiltersNullTest() {
        var ccelebCriteria = new CcelebCriteria();
        assertThat(ccelebCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void ccelebCriteriaFluentMethodsCreatesFiltersTest() {
        var ccelebCriteria = new CcelebCriteria();

        setAllFilters(ccelebCriteria);

        assertThat(ccelebCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void ccelebCriteriaCopyCreatesNullFilterTest() {
        var ccelebCriteria = new CcelebCriteria();
        var copy = ccelebCriteria.copy();

        assertThat(ccelebCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(ccelebCriteria)
        );
    }

    @Test
    void ccelebCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var ccelebCriteria = new CcelebCriteria();
        setAllFilters(ccelebCriteria);

        var copy = ccelebCriteria.copy();

        assertThat(ccelebCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(ccelebCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var ccelebCriteria = new CcelebCriteria();

        assertThat(ccelebCriteria).hasToString("CcelebCriteria{}");
    }

    private static void setAllFilters(CcelebCriteria ccelebCriteria) {
        ccelebCriteria.id();
        ccelebCriteria.celebName();
        ccelebCriteria.communityId();
        ccelebCriteria.distinct();
    }

    private static Condition<CcelebCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCelebName()) &&
                condition.apply(criteria.getCommunityId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CcelebCriteria> copyFiltersAre(CcelebCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCelebName(), copy.getCelebName()) &&
                condition.apply(criteria.getCommunityId(), copy.getCommunityId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
