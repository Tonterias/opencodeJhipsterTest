package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AppphotoCriteriaTest {

    @Test
    void newAppphotoCriteriaHasAllFiltersNullTest() {
        var appphotoCriteria = new AppphotoCriteria();
        assertThat(appphotoCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void appphotoCriteriaFluentMethodsCreatesFiltersTest() {
        var appphotoCriteria = new AppphotoCriteria();

        setAllFilters(appphotoCriteria);

        assertThat(appphotoCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void appphotoCriteriaCopyCreatesNullFilterTest() {
        var appphotoCriteria = new AppphotoCriteria();
        var copy = appphotoCriteria.copy();

        assertThat(appphotoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(appphotoCriteria)
        );
    }

    @Test
    void appphotoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var appphotoCriteria = new AppphotoCriteria();
        setAllFilters(appphotoCriteria);

        var copy = appphotoCriteria.copy();

        assertThat(appphotoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(appphotoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var appphotoCriteria = new AppphotoCriteria();

        assertThat(appphotoCriteria).hasToString("AppphotoCriteria{}");
    }

    private static void setAllFilters(AppphotoCriteria appphotoCriteria) {
        appphotoCriteria.id();
        appphotoCriteria.creationDate();
        appphotoCriteria.appuserId();
        appphotoCriteria.distinct();
    }

    private static Condition<AppphotoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCreationDate()) &&
                condition.apply(criteria.getAppuserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AppphotoCriteria> copyFiltersAre(AppphotoCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCreationDate(), copy.getCreationDate()) &&
                condition.apply(criteria.getAppuserId(), copy.getAppuserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
