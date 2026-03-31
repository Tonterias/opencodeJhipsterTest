package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TopicCriteriaTest {

    @Test
    void newTopicCriteriaHasAllFiltersNullTest() {
        var topicCriteria = new TopicCriteria();
        assertThat(topicCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void topicCriteriaFluentMethodsCreatesFiltersTest() {
        var topicCriteria = new TopicCriteria();

        setAllFilters(topicCriteria);

        assertThat(topicCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void topicCriteriaCopyCreatesNullFilterTest() {
        var topicCriteria = new TopicCriteria();
        var copy = topicCriteria.copy();

        assertThat(topicCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(topicCriteria)
        );
    }

    @Test
    void topicCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var topicCriteria = new TopicCriteria();
        setAllFilters(topicCriteria);

        var copy = topicCriteria.copy();

        assertThat(topicCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(topicCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var topicCriteria = new TopicCriteria();

        assertThat(topicCriteria).hasToString("TopicCriteria{}");
    }

    private static void setAllFilters(TopicCriteria topicCriteria) {
        topicCriteria.id();
        topicCriteria.topicName();
        topicCriteria.postId();
        topicCriteria.distinct();
    }

    private static Condition<TopicCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTopicName()) &&
                condition.apply(criteria.getPostId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TopicCriteria> copyFiltersAre(TopicCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTopicName(), copy.getTopicName()) &&
                condition.apply(criteria.getPostId(), copy.getPostId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
