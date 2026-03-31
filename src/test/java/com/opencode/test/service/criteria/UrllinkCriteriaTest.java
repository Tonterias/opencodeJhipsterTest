package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class UrllinkCriteriaTest {

    @Test
    void newUrllinkCriteriaHasAllFiltersNullTest() {
        var urllinkCriteria = new UrllinkCriteria();
        assertThat(urllinkCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void urllinkCriteriaFluentMethodsCreatesFiltersTest() {
        var urllinkCriteria = new UrllinkCriteria();

        setAllFilters(urllinkCriteria);

        assertThat(urllinkCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void urllinkCriteriaCopyCreatesNullFilterTest() {
        var urllinkCriteria = new UrllinkCriteria();
        var copy = urllinkCriteria.copy();

        assertThat(urllinkCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(urllinkCriteria)
        );
    }

    @Test
    void urllinkCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var urllinkCriteria = new UrllinkCriteria();
        setAllFilters(urllinkCriteria);

        var copy = urllinkCriteria.copy();

        assertThat(urllinkCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(urllinkCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var urllinkCriteria = new UrllinkCriteria();

        assertThat(urllinkCriteria).hasToString("UrllinkCriteria{}");
    }

    private static void setAllFilters(UrllinkCriteria urllinkCriteria) {
        urllinkCriteria.id();
        urllinkCriteria.linkText();
        urllinkCriteria.linkURL();
        urllinkCriteria.distinct();
    }

    private static Condition<UrllinkCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLinkText()) &&
                condition.apply(criteria.getLinkURL()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<UrllinkCriteria> copyFiltersAre(UrllinkCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLinkText(), copy.getLinkText()) &&
                condition.apply(criteria.getLinkURL(), copy.getLinkURL()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
