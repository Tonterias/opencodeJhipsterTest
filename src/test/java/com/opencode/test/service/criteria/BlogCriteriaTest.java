package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BlogCriteriaTest {

    @Test
    void newBlogCriteriaHasAllFiltersNullTest() {
        var blogCriteria = new BlogCriteria();
        assertThat(blogCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void blogCriteriaFluentMethodsCreatesFiltersTest() {
        var blogCriteria = new BlogCriteria();

        setAllFilters(blogCriteria);

        assertThat(blogCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void blogCriteriaCopyCreatesNullFilterTest() {
        var blogCriteria = new BlogCriteria();
        var copy = blogCriteria.copy();

        assertThat(blogCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(blogCriteria)
        );
    }

    @Test
    void blogCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var blogCriteria = new BlogCriteria();
        setAllFilters(blogCriteria);

        var copy = blogCriteria.copy();

        assertThat(blogCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(blogCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var blogCriteria = new BlogCriteria();

        assertThat(blogCriteria).hasToString("BlogCriteria{}");
    }

    private static void setAllFilters(BlogCriteria blogCriteria) {
        blogCriteria.id();
        blogCriteria.creationDate();
        blogCriteria.title();
        blogCriteria.postId();
        blogCriteria.appuserId();
        blogCriteria.communityId();
        blogCriteria.distinct();
    }

    private static Condition<BlogCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCreationDate()) &&
                condition.apply(criteria.getTitle()) &&
                condition.apply(criteria.getPostId()) &&
                condition.apply(criteria.getAppuserId()) &&
                condition.apply(criteria.getCommunityId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BlogCriteria> copyFiltersAre(BlogCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCreationDate(), copy.getCreationDate()) &&
                condition.apply(criteria.getTitle(), copy.getTitle()) &&
                condition.apply(criteria.getPostId(), copy.getPostId()) &&
                condition.apply(criteria.getAppuserId(), copy.getAppuserId()) &&
                condition.apply(criteria.getCommunityId(), copy.getCommunityId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
