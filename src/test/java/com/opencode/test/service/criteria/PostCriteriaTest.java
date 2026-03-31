package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PostCriteriaTest {

    @Test
    void newPostCriteriaHasAllFiltersNullTest() {
        var postCriteria = new PostCriteria();
        assertThat(postCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void postCriteriaFluentMethodsCreatesFiltersTest() {
        var postCriteria = new PostCriteria();

        setAllFilters(postCriteria);

        assertThat(postCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void postCriteriaCopyCreatesNullFilterTest() {
        var postCriteria = new PostCriteria();
        var copy = postCriteria.copy();

        assertThat(postCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(postCriteria)
        );
    }

    @Test
    void postCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var postCriteria = new PostCriteria();
        setAllFilters(postCriteria);

        var copy = postCriteria.copy();

        assertThat(postCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(postCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var postCriteria = new PostCriteria();

        assertThat(postCriteria).hasToString("PostCriteria{}");
    }

    private static void setAllFilters(PostCriteria postCriteria) {
        postCriteria.id();
        postCriteria.creationDate();
        postCriteria.publicationDate();
        postCriteria.headline();
        postCriteria.leadtext();
        postCriteria.bodytext();
        postCriteria.quote();
        postCriteria.conclusion();
        postCriteria.linkText();
        postCriteria.linkURL();
        postCriteria.commentId();
        postCriteria.appuserId();
        postCriteria.blogId();
        postCriteria.tagId();
        postCriteria.topicId();
        postCriteria.distinct();
    }

    private static Condition<PostCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCreationDate()) &&
                condition.apply(criteria.getPublicationDate()) &&
                condition.apply(criteria.getHeadline()) &&
                condition.apply(criteria.getLeadtext()) &&
                condition.apply(criteria.getBodytext()) &&
                condition.apply(criteria.getQuote()) &&
                condition.apply(criteria.getConclusion()) &&
                condition.apply(criteria.getLinkText()) &&
                condition.apply(criteria.getLinkURL()) &&
                condition.apply(criteria.getCommentId()) &&
                condition.apply(criteria.getAppuserId()) &&
                condition.apply(criteria.getBlogId()) &&
                condition.apply(criteria.getTagId()) &&
                condition.apply(criteria.getTopicId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PostCriteria> copyFiltersAre(PostCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCreationDate(), copy.getCreationDate()) &&
                condition.apply(criteria.getPublicationDate(), copy.getPublicationDate()) &&
                condition.apply(criteria.getHeadline(), copy.getHeadline()) &&
                condition.apply(criteria.getLeadtext(), copy.getLeadtext()) &&
                condition.apply(criteria.getBodytext(), copy.getBodytext()) &&
                condition.apply(criteria.getQuote(), copy.getQuote()) &&
                condition.apply(criteria.getConclusion(), copy.getConclusion()) &&
                condition.apply(criteria.getLinkText(), copy.getLinkText()) &&
                condition.apply(criteria.getLinkURL(), copy.getLinkURL()) &&
                condition.apply(criteria.getCommentId(), copy.getCommentId()) &&
                condition.apply(criteria.getAppuserId(), copy.getAppuserId()) &&
                condition.apply(criteria.getBlogId(), copy.getBlogId()) &&
                condition.apply(criteria.getTagId(), copy.getTagId()) &&
                condition.apply(criteria.getTopicId(), copy.getTopicId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
