package com.opencode.test.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FrontpageconfigCriteriaTest {

    @Test
    void newFrontpageconfigCriteriaHasAllFiltersNullTest() {
        var frontpageconfigCriteria = new FrontpageconfigCriteria();
        assertThat(frontpageconfigCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void frontpageconfigCriteriaFluentMethodsCreatesFiltersTest() {
        var frontpageconfigCriteria = new FrontpageconfigCriteria();

        setAllFilters(frontpageconfigCriteria);

        assertThat(frontpageconfigCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void frontpageconfigCriteriaCopyCreatesNullFilterTest() {
        var frontpageconfigCriteria = new FrontpageconfigCriteria();
        var copy = frontpageconfigCriteria.copy();

        assertThat(frontpageconfigCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(frontpageconfigCriteria)
        );
    }

    @Test
    void frontpageconfigCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var frontpageconfigCriteria = new FrontpageconfigCriteria();
        setAllFilters(frontpageconfigCriteria);

        var copy = frontpageconfigCriteria.copy();

        assertThat(frontpageconfigCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(frontpageconfigCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var frontpageconfigCriteria = new FrontpageconfigCriteria();

        assertThat(frontpageconfigCriteria).hasToString("FrontpageconfigCriteria{}");
    }

    private static void setAllFilters(FrontpageconfigCriteria frontpageconfigCriteria) {
        frontpageconfigCriteria.id();
        frontpageconfigCriteria.creationDate();
        frontpageconfigCriteria.topNews1();
        frontpageconfigCriteria.topNews2();
        frontpageconfigCriteria.topNews3();
        frontpageconfigCriteria.topNews4();
        frontpageconfigCriteria.topNews5();
        frontpageconfigCriteria.latestNews1();
        frontpageconfigCriteria.latestNews2();
        frontpageconfigCriteria.latestNews3();
        frontpageconfigCriteria.latestNews4();
        frontpageconfigCriteria.latestNews5();
        frontpageconfigCriteria.breakingNews1();
        frontpageconfigCriteria.recentPosts1();
        frontpageconfigCriteria.recentPosts2();
        frontpageconfigCriteria.recentPosts3();
        frontpageconfigCriteria.recentPosts4();
        frontpageconfigCriteria.featuredArticles1();
        frontpageconfigCriteria.featuredArticles2();
        frontpageconfigCriteria.featuredArticles3();
        frontpageconfigCriteria.featuredArticles4();
        frontpageconfigCriteria.featuredArticles5();
        frontpageconfigCriteria.featuredArticles6();
        frontpageconfigCriteria.featuredArticles7();
        frontpageconfigCriteria.featuredArticles8();
        frontpageconfigCriteria.featuredArticles9();
        frontpageconfigCriteria.featuredArticles10();
        frontpageconfigCriteria.popularNews1();
        frontpageconfigCriteria.popularNews2();
        frontpageconfigCriteria.popularNews3();
        frontpageconfigCriteria.popularNews4();
        frontpageconfigCriteria.popularNews5();
        frontpageconfigCriteria.popularNews6();
        frontpageconfigCriteria.popularNews7();
        frontpageconfigCriteria.popularNews8();
        frontpageconfigCriteria.weeklyNews1();
        frontpageconfigCriteria.weeklyNews2();
        frontpageconfigCriteria.weeklyNews3();
        frontpageconfigCriteria.weeklyNews4();
        frontpageconfigCriteria.newsFeeds1();
        frontpageconfigCriteria.newsFeeds2();
        frontpageconfigCriteria.newsFeeds3();
        frontpageconfigCriteria.newsFeeds4();
        frontpageconfigCriteria.newsFeeds5();
        frontpageconfigCriteria.newsFeeds6();
        frontpageconfigCriteria.usefulLinks1();
        frontpageconfigCriteria.usefulLinks2();
        frontpageconfigCriteria.usefulLinks3();
        frontpageconfigCriteria.usefulLinks4();
        frontpageconfigCriteria.usefulLinks5();
        frontpageconfigCriteria.usefulLinks6();
        frontpageconfigCriteria.recentVideos1();
        frontpageconfigCriteria.recentVideos2();
        frontpageconfigCriteria.recentVideos3();
        frontpageconfigCriteria.recentVideos4();
        frontpageconfigCriteria.recentVideos5();
        frontpageconfigCriteria.recentVideos6();
        frontpageconfigCriteria.distinct();
    }

    private static Condition<FrontpageconfigCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCreationDate()) &&
                condition.apply(criteria.getTopNews1()) &&
                condition.apply(criteria.getTopNews2()) &&
                condition.apply(criteria.getTopNews3()) &&
                condition.apply(criteria.getTopNews4()) &&
                condition.apply(criteria.getTopNews5()) &&
                condition.apply(criteria.getLatestNews1()) &&
                condition.apply(criteria.getLatestNews2()) &&
                condition.apply(criteria.getLatestNews3()) &&
                condition.apply(criteria.getLatestNews4()) &&
                condition.apply(criteria.getLatestNews5()) &&
                condition.apply(criteria.getBreakingNews1()) &&
                condition.apply(criteria.getRecentPosts1()) &&
                condition.apply(criteria.getRecentPosts2()) &&
                condition.apply(criteria.getRecentPosts3()) &&
                condition.apply(criteria.getRecentPosts4()) &&
                condition.apply(criteria.getFeaturedArticles1()) &&
                condition.apply(criteria.getFeaturedArticles2()) &&
                condition.apply(criteria.getFeaturedArticles3()) &&
                condition.apply(criteria.getFeaturedArticles4()) &&
                condition.apply(criteria.getFeaturedArticles5()) &&
                condition.apply(criteria.getFeaturedArticles6()) &&
                condition.apply(criteria.getFeaturedArticles7()) &&
                condition.apply(criteria.getFeaturedArticles8()) &&
                condition.apply(criteria.getFeaturedArticles9()) &&
                condition.apply(criteria.getFeaturedArticles10()) &&
                condition.apply(criteria.getPopularNews1()) &&
                condition.apply(criteria.getPopularNews2()) &&
                condition.apply(criteria.getPopularNews3()) &&
                condition.apply(criteria.getPopularNews4()) &&
                condition.apply(criteria.getPopularNews5()) &&
                condition.apply(criteria.getPopularNews6()) &&
                condition.apply(criteria.getPopularNews7()) &&
                condition.apply(criteria.getPopularNews8()) &&
                condition.apply(criteria.getWeeklyNews1()) &&
                condition.apply(criteria.getWeeklyNews2()) &&
                condition.apply(criteria.getWeeklyNews3()) &&
                condition.apply(criteria.getWeeklyNews4()) &&
                condition.apply(criteria.getNewsFeeds1()) &&
                condition.apply(criteria.getNewsFeeds2()) &&
                condition.apply(criteria.getNewsFeeds3()) &&
                condition.apply(criteria.getNewsFeeds4()) &&
                condition.apply(criteria.getNewsFeeds5()) &&
                condition.apply(criteria.getNewsFeeds6()) &&
                condition.apply(criteria.getUsefulLinks1()) &&
                condition.apply(criteria.getUsefulLinks2()) &&
                condition.apply(criteria.getUsefulLinks3()) &&
                condition.apply(criteria.getUsefulLinks4()) &&
                condition.apply(criteria.getUsefulLinks5()) &&
                condition.apply(criteria.getUsefulLinks6()) &&
                condition.apply(criteria.getRecentVideos1()) &&
                condition.apply(criteria.getRecentVideos2()) &&
                condition.apply(criteria.getRecentVideos3()) &&
                condition.apply(criteria.getRecentVideos4()) &&
                condition.apply(criteria.getRecentVideos5()) &&
                condition.apply(criteria.getRecentVideos6()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FrontpageconfigCriteria> copyFiltersAre(
        FrontpageconfigCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCreationDate(), copy.getCreationDate()) &&
                condition.apply(criteria.getTopNews1(), copy.getTopNews1()) &&
                condition.apply(criteria.getTopNews2(), copy.getTopNews2()) &&
                condition.apply(criteria.getTopNews3(), copy.getTopNews3()) &&
                condition.apply(criteria.getTopNews4(), copy.getTopNews4()) &&
                condition.apply(criteria.getTopNews5(), copy.getTopNews5()) &&
                condition.apply(criteria.getLatestNews1(), copy.getLatestNews1()) &&
                condition.apply(criteria.getLatestNews2(), copy.getLatestNews2()) &&
                condition.apply(criteria.getLatestNews3(), copy.getLatestNews3()) &&
                condition.apply(criteria.getLatestNews4(), copy.getLatestNews4()) &&
                condition.apply(criteria.getLatestNews5(), copy.getLatestNews5()) &&
                condition.apply(criteria.getBreakingNews1(), copy.getBreakingNews1()) &&
                condition.apply(criteria.getRecentPosts1(), copy.getRecentPosts1()) &&
                condition.apply(criteria.getRecentPosts2(), copy.getRecentPosts2()) &&
                condition.apply(criteria.getRecentPosts3(), copy.getRecentPosts3()) &&
                condition.apply(criteria.getRecentPosts4(), copy.getRecentPosts4()) &&
                condition.apply(criteria.getFeaturedArticles1(), copy.getFeaturedArticles1()) &&
                condition.apply(criteria.getFeaturedArticles2(), copy.getFeaturedArticles2()) &&
                condition.apply(criteria.getFeaturedArticles3(), copy.getFeaturedArticles3()) &&
                condition.apply(criteria.getFeaturedArticles4(), copy.getFeaturedArticles4()) &&
                condition.apply(criteria.getFeaturedArticles5(), copy.getFeaturedArticles5()) &&
                condition.apply(criteria.getFeaturedArticles6(), copy.getFeaturedArticles6()) &&
                condition.apply(criteria.getFeaturedArticles7(), copy.getFeaturedArticles7()) &&
                condition.apply(criteria.getFeaturedArticles8(), copy.getFeaturedArticles8()) &&
                condition.apply(criteria.getFeaturedArticles9(), copy.getFeaturedArticles9()) &&
                condition.apply(criteria.getFeaturedArticles10(), copy.getFeaturedArticles10()) &&
                condition.apply(criteria.getPopularNews1(), copy.getPopularNews1()) &&
                condition.apply(criteria.getPopularNews2(), copy.getPopularNews2()) &&
                condition.apply(criteria.getPopularNews3(), copy.getPopularNews3()) &&
                condition.apply(criteria.getPopularNews4(), copy.getPopularNews4()) &&
                condition.apply(criteria.getPopularNews5(), copy.getPopularNews5()) &&
                condition.apply(criteria.getPopularNews6(), copy.getPopularNews6()) &&
                condition.apply(criteria.getPopularNews7(), copy.getPopularNews7()) &&
                condition.apply(criteria.getPopularNews8(), copy.getPopularNews8()) &&
                condition.apply(criteria.getWeeklyNews1(), copy.getWeeklyNews1()) &&
                condition.apply(criteria.getWeeklyNews2(), copy.getWeeklyNews2()) &&
                condition.apply(criteria.getWeeklyNews3(), copy.getWeeklyNews3()) &&
                condition.apply(criteria.getWeeklyNews4(), copy.getWeeklyNews4()) &&
                condition.apply(criteria.getNewsFeeds1(), copy.getNewsFeeds1()) &&
                condition.apply(criteria.getNewsFeeds2(), copy.getNewsFeeds2()) &&
                condition.apply(criteria.getNewsFeeds3(), copy.getNewsFeeds3()) &&
                condition.apply(criteria.getNewsFeeds4(), copy.getNewsFeeds4()) &&
                condition.apply(criteria.getNewsFeeds5(), copy.getNewsFeeds5()) &&
                condition.apply(criteria.getNewsFeeds6(), copy.getNewsFeeds6()) &&
                condition.apply(criteria.getUsefulLinks1(), copy.getUsefulLinks1()) &&
                condition.apply(criteria.getUsefulLinks2(), copy.getUsefulLinks2()) &&
                condition.apply(criteria.getUsefulLinks3(), copy.getUsefulLinks3()) &&
                condition.apply(criteria.getUsefulLinks4(), copy.getUsefulLinks4()) &&
                condition.apply(criteria.getUsefulLinks5(), copy.getUsefulLinks5()) &&
                condition.apply(criteria.getUsefulLinks6(), copy.getUsefulLinks6()) &&
                condition.apply(criteria.getRecentVideos1(), copy.getRecentVideos1()) &&
                condition.apply(criteria.getRecentVideos2(), copy.getRecentVideos2()) &&
                condition.apply(criteria.getRecentVideos3(), copy.getRecentVideos3()) &&
                condition.apply(criteria.getRecentVideos4(), copy.getRecentVideos4()) &&
                condition.apply(criteria.getRecentVideos5(), copy.getRecentVideos5()) &&
                condition.apply(criteria.getRecentVideos6(), copy.getRecentVideos6()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
