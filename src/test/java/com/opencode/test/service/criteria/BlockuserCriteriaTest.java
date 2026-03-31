package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BlockuserCriteriaTest {

    @Test
    void newBlockuserCriteriaHasAllFiltersNullTest() {
        var blockuserCriteria = new BlockuserCriteria();
        assertThat(blockuserCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void blockuserCriteriaFluentMethodsCreatesFiltersTest() {
        var blockuserCriteria = new BlockuserCriteria();

        setAllFilters(blockuserCriteria);

        assertThat(blockuserCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void blockuserCriteriaCopyCreatesNullFilterTest() {
        var blockuserCriteria = new BlockuserCriteria();
        var copy = blockuserCriteria.copy();

        assertThat(blockuserCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(blockuserCriteria)
        );
    }

    @Test
    void blockuserCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var blockuserCriteria = new BlockuserCriteria();
        setAllFilters(blockuserCriteria);

        var copy = blockuserCriteria.copy();

        assertThat(blockuserCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(blockuserCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var blockuserCriteria = new BlockuserCriteria();

        assertThat(blockuserCriteria).hasToString("BlockuserCriteria{}");
    }

    private static void setAllFilters(BlockuserCriteria blockuserCriteria) {
        blockuserCriteria.id();
        blockuserCriteria.creationDate();
        blockuserCriteria.blockeduserId();
        blockuserCriteria.blockinguserId();
        blockuserCriteria.cblockeduserId();
        blockuserCriteria.cblockinguserId();
        blockuserCriteria.distinct();
    }

    private static Condition<BlockuserCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCreationDate()) &&
                condition.apply(criteria.getBlockeduserId()) &&
                condition.apply(criteria.getBlockinguserId()) &&
                condition.apply(criteria.getCblockeduserId()) &&
                condition.apply(criteria.getCblockinguserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BlockuserCriteria> copyFiltersAre(BlockuserCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCreationDate(), copy.getCreationDate()) &&
                condition.apply(criteria.getBlockeduserId(), copy.getBlockeduserId()) &&
                condition.apply(criteria.getBlockinguserId(), copy.getBlockinguserId()) &&
                condition.apply(criteria.getCblockeduserId(), copy.getCblockeduserId()) &&
                condition.apply(criteria.getCblockinguserId(), copy.getCblockinguserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
