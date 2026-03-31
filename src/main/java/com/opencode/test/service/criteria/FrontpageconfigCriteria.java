package com.opencode.test.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.opencode.test.domain.Frontpageconfig} entity. This class is used
 * in {@link com.opencode.test.web.rest.FrontpageconfigResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /frontpageconfigs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FrontpageconfigCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter creationDate;

    private LongFilter topNews1;

    private LongFilter topNews2;

    private LongFilter topNews3;

    private LongFilter topNews4;

    private LongFilter topNews5;

    private LongFilter latestNews1;

    private LongFilter latestNews2;

    private LongFilter latestNews3;

    private LongFilter latestNews4;

    private LongFilter latestNews5;

    private LongFilter breakingNews1;

    private LongFilter recentPosts1;

    private LongFilter recentPosts2;

    private LongFilter recentPosts3;

    private LongFilter recentPosts4;

    private LongFilter featuredArticles1;

    private LongFilter featuredArticles2;

    private LongFilter featuredArticles3;

    private LongFilter featuredArticles4;

    private LongFilter featuredArticles5;

    private LongFilter featuredArticles6;

    private LongFilter featuredArticles7;

    private LongFilter featuredArticles8;

    private LongFilter featuredArticles9;

    private LongFilter featuredArticles10;

    private LongFilter popularNews1;

    private LongFilter popularNews2;

    private LongFilter popularNews3;

    private LongFilter popularNews4;

    private LongFilter popularNews5;

    private LongFilter popularNews6;

    private LongFilter popularNews7;

    private LongFilter popularNews8;

    private LongFilter weeklyNews1;

    private LongFilter weeklyNews2;

    private LongFilter weeklyNews3;

    private LongFilter weeklyNews4;

    private LongFilter newsFeeds1;

    private LongFilter newsFeeds2;

    private LongFilter newsFeeds3;

    private LongFilter newsFeeds4;

    private LongFilter newsFeeds5;

    private LongFilter newsFeeds6;

    private LongFilter usefulLinks1;

    private LongFilter usefulLinks2;

    private LongFilter usefulLinks3;

    private LongFilter usefulLinks4;

    private LongFilter usefulLinks5;

    private LongFilter usefulLinks6;

    private LongFilter recentVideos1;

    private LongFilter recentVideos2;

    private LongFilter recentVideos3;

    private LongFilter recentVideos4;

    private LongFilter recentVideos5;

    private LongFilter recentVideos6;

    private Boolean distinct;

    public FrontpageconfigCriteria() {}

    public FrontpageconfigCriteria(FrontpageconfigCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.creationDate = other.optionalCreationDate().map(InstantFilter::copy).orElse(null);
        this.topNews1 = other.optionalTopNews1().map(LongFilter::copy).orElse(null);
        this.topNews2 = other.optionalTopNews2().map(LongFilter::copy).orElse(null);
        this.topNews3 = other.optionalTopNews3().map(LongFilter::copy).orElse(null);
        this.topNews4 = other.optionalTopNews4().map(LongFilter::copy).orElse(null);
        this.topNews5 = other.optionalTopNews5().map(LongFilter::copy).orElse(null);
        this.latestNews1 = other.optionalLatestNews1().map(LongFilter::copy).orElse(null);
        this.latestNews2 = other.optionalLatestNews2().map(LongFilter::copy).orElse(null);
        this.latestNews3 = other.optionalLatestNews3().map(LongFilter::copy).orElse(null);
        this.latestNews4 = other.optionalLatestNews4().map(LongFilter::copy).orElse(null);
        this.latestNews5 = other.optionalLatestNews5().map(LongFilter::copy).orElse(null);
        this.breakingNews1 = other.optionalBreakingNews1().map(LongFilter::copy).orElse(null);
        this.recentPosts1 = other.optionalRecentPosts1().map(LongFilter::copy).orElse(null);
        this.recentPosts2 = other.optionalRecentPosts2().map(LongFilter::copy).orElse(null);
        this.recentPosts3 = other.optionalRecentPosts3().map(LongFilter::copy).orElse(null);
        this.recentPosts4 = other.optionalRecentPosts4().map(LongFilter::copy).orElse(null);
        this.featuredArticles1 = other.optionalFeaturedArticles1().map(LongFilter::copy).orElse(null);
        this.featuredArticles2 = other.optionalFeaturedArticles2().map(LongFilter::copy).orElse(null);
        this.featuredArticles3 = other.optionalFeaturedArticles3().map(LongFilter::copy).orElse(null);
        this.featuredArticles4 = other.optionalFeaturedArticles4().map(LongFilter::copy).orElse(null);
        this.featuredArticles5 = other.optionalFeaturedArticles5().map(LongFilter::copy).orElse(null);
        this.featuredArticles6 = other.optionalFeaturedArticles6().map(LongFilter::copy).orElse(null);
        this.featuredArticles7 = other.optionalFeaturedArticles7().map(LongFilter::copy).orElse(null);
        this.featuredArticles8 = other.optionalFeaturedArticles8().map(LongFilter::copy).orElse(null);
        this.featuredArticles9 = other.optionalFeaturedArticles9().map(LongFilter::copy).orElse(null);
        this.featuredArticles10 = other.optionalFeaturedArticles10().map(LongFilter::copy).orElse(null);
        this.popularNews1 = other.optionalPopularNews1().map(LongFilter::copy).orElse(null);
        this.popularNews2 = other.optionalPopularNews2().map(LongFilter::copy).orElse(null);
        this.popularNews3 = other.optionalPopularNews3().map(LongFilter::copy).orElse(null);
        this.popularNews4 = other.optionalPopularNews4().map(LongFilter::copy).orElse(null);
        this.popularNews5 = other.optionalPopularNews5().map(LongFilter::copy).orElse(null);
        this.popularNews6 = other.optionalPopularNews6().map(LongFilter::copy).orElse(null);
        this.popularNews7 = other.optionalPopularNews7().map(LongFilter::copy).orElse(null);
        this.popularNews8 = other.optionalPopularNews8().map(LongFilter::copy).orElse(null);
        this.weeklyNews1 = other.optionalWeeklyNews1().map(LongFilter::copy).orElse(null);
        this.weeklyNews2 = other.optionalWeeklyNews2().map(LongFilter::copy).orElse(null);
        this.weeklyNews3 = other.optionalWeeklyNews3().map(LongFilter::copy).orElse(null);
        this.weeklyNews4 = other.optionalWeeklyNews4().map(LongFilter::copy).orElse(null);
        this.newsFeeds1 = other.optionalNewsFeeds1().map(LongFilter::copy).orElse(null);
        this.newsFeeds2 = other.optionalNewsFeeds2().map(LongFilter::copy).orElse(null);
        this.newsFeeds3 = other.optionalNewsFeeds3().map(LongFilter::copy).orElse(null);
        this.newsFeeds4 = other.optionalNewsFeeds4().map(LongFilter::copy).orElse(null);
        this.newsFeeds5 = other.optionalNewsFeeds5().map(LongFilter::copy).orElse(null);
        this.newsFeeds6 = other.optionalNewsFeeds6().map(LongFilter::copy).orElse(null);
        this.usefulLinks1 = other.optionalUsefulLinks1().map(LongFilter::copy).orElse(null);
        this.usefulLinks2 = other.optionalUsefulLinks2().map(LongFilter::copy).orElse(null);
        this.usefulLinks3 = other.optionalUsefulLinks3().map(LongFilter::copy).orElse(null);
        this.usefulLinks4 = other.optionalUsefulLinks4().map(LongFilter::copy).orElse(null);
        this.usefulLinks5 = other.optionalUsefulLinks5().map(LongFilter::copy).orElse(null);
        this.usefulLinks6 = other.optionalUsefulLinks6().map(LongFilter::copy).orElse(null);
        this.recentVideos1 = other.optionalRecentVideos1().map(LongFilter::copy).orElse(null);
        this.recentVideos2 = other.optionalRecentVideos2().map(LongFilter::copy).orElse(null);
        this.recentVideos3 = other.optionalRecentVideos3().map(LongFilter::copy).orElse(null);
        this.recentVideos4 = other.optionalRecentVideos4().map(LongFilter::copy).orElse(null);
        this.recentVideos5 = other.optionalRecentVideos5().map(LongFilter::copy).orElse(null);
        this.recentVideos6 = other.optionalRecentVideos6().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FrontpageconfigCriteria copy() {
        return new FrontpageconfigCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public Optional<InstantFilter> optionalCreationDate() {
        return Optional.ofNullable(creationDate);
    }

    public InstantFilter creationDate() {
        if (creationDate == null) {
            setCreationDate(new InstantFilter());
        }
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public LongFilter getTopNews1() {
        return topNews1;
    }

    public Optional<LongFilter> optionalTopNews1() {
        return Optional.ofNullable(topNews1);
    }

    public LongFilter topNews1() {
        if (topNews1 == null) {
            setTopNews1(new LongFilter());
        }
        return topNews1;
    }

    public void setTopNews1(LongFilter topNews1) {
        this.topNews1 = topNews1;
    }

    public LongFilter getTopNews2() {
        return topNews2;
    }

    public Optional<LongFilter> optionalTopNews2() {
        return Optional.ofNullable(topNews2);
    }

    public LongFilter topNews2() {
        if (topNews2 == null) {
            setTopNews2(new LongFilter());
        }
        return topNews2;
    }

    public void setTopNews2(LongFilter topNews2) {
        this.topNews2 = topNews2;
    }

    public LongFilter getTopNews3() {
        return topNews3;
    }

    public Optional<LongFilter> optionalTopNews3() {
        return Optional.ofNullable(topNews3);
    }

    public LongFilter topNews3() {
        if (topNews3 == null) {
            setTopNews3(new LongFilter());
        }
        return topNews3;
    }

    public void setTopNews3(LongFilter topNews3) {
        this.topNews3 = topNews3;
    }

    public LongFilter getTopNews4() {
        return topNews4;
    }

    public Optional<LongFilter> optionalTopNews4() {
        return Optional.ofNullable(topNews4);
    }

    public LongFilter topNews4() {
        if (topNews4 == null) {
            setTopNews4(new LongFilter());
        }
        return topNews4;
    }

    public void setTopNews4(LongFilter topNews4) {
        this.topNews4 = topNews4;
    }

    public LongFilter getTopNews5() {
        return topNews5;
    }

    public Optional<LongFilter> optionalTopNews5() {
        return Optional.ofNullable(topNews5);
    }

    public LongFilter topNews5() {
        if (topNews5 == null) {
            setTopNews5(new LongFilter());
        }
        return topNews5;
    }

    public void setTopNews5(LongFilter topNews5) {
        this.topNews5 = topNews5;
    }

    public LongFilter getLatestNews1() {
        return latestNews1;
    }

    public Optional<LongFilter> optionalLatestNews1() {
        return Optional.ofNullable(latestNews1);
    }

    public LongFilter latestNews1() {
        if (latestNews1 == null) {
            setLatestNews1(new LongFilter());
        }
        return latestNews1;
    }

    public void setLatestNews1(LongFilter latestNews1) {
        this.latestNews1 = latestNews1;
    }

    public LongFilter getLatestNews2() {
        return latestNews2;
    }

    public Optional<LongFilter> optionalLatestNews2() {
        return Optional.ofNullable(latestNews2);
    }

    public LongFilter latestNews2() {
        if (latestNews2 == null) {
            setLatestNews2(new LongFilter());
        }
        return latestNews2;
    }

    public void setLatestNews2(LongFilter latestNews2) {
        this.latestNews2 = latestNews2;
    }

    public LongFilter getLatestNews3() {
        return latestNews3;
    }

    public Optional<LongFilter> optionalLatestNews3() {
        return Optional.ofNullable(latestNews3);
    }

    public LongFilter latestNews3() {
        if (latestNews3 == null) {
            setLatestNews3(new LongFilter());
        }
        return latestNews3;
    }

    public void setLatestNews3(LongFilter latestNews3) {
        this.latestNews3 = latestNews3;
    }

    public LongFilter getLatestNews4() {
        return latestNews4;
    }

    public Optional<LongFilter> optionalLatestNews4() {
        return Optional.ofNullable(latestNews4);
    }

    public LongFilter latestNews4() {
        if (latestNews4 == null) {
            setLatestNews4(new LongFilter());
        }
        return latestNews4;
    }

    public void setLatestNews4(LongFilter latestNews4) {
        this.latestNews4 = latestNews4;
    }

    public LongFilter getLatestNews5() {
        return latestNews5;
    }

    public Optional<LongFilter> optionalLatestNews5() {
        return Optional.ofNullable(latestNews5);
    }

    public LongFilter latestNews5() {
        if (latestNews5 == null) {
            setLatestNews5(new LongFilter());
        }
        return latestNews5;
    }

    public void setLatestNews5(LongFilter latestNews5) {
        this.latestNews5 = latestNews5;
    }

    public LongFilter getBreakingNews1() {
        return breakingNews1;
    }

    public Optional<LongFilter> optionalBreakingNews1() {
        return Optional.ofNullable(breakingNews1);
    }

    public LongFilter breakingNews1() {
        if (breakingNews1 == null) {
            setBreakingNews1(new LongFilter());
        }
        return breakingNews1;
    }

    public void setBreakingNews1(LongFilter breakingNews1) {
        this.breakingNews1 = breakingNews1;
    }

    public LongFilter getRecentPosts1() {
        return recentPosts1;
    }

    public Optional<LongFilter> optionalRecentPosts1() {
        return Optional.ofNullable(recentPosts1);
    }

    public LongFilter recentPosts1() {
        if (recentPosts1 == null) {
            setRecentPosts1(new LongFilter());
        }
        return recentPosts1;
    }

    public void setRecentPosts1(LongFilter recentPosts1) {
        this.recentPosts1 = recentPosts1;
    }

    public LongFilter getRecentPosts2() {
        return recentPosts2;
    }

    public Optional<LongFilter> optionalRecentPosts2() {
        return Optional.ofNullable(recentPosts2);
    }

    public LongFilter recentPosts2() {
        if (recentPosts2 == null) {
            setRecentPosts2(new LongFilter());
        }
        return recentPosts2;
    }

    public void setRecentPosts2(LongFilter recentPosts2) {
        this.recentPosts2 = recentPosts2;
    }

    public LongFilter getRecentPosts3() {
        return recentPosts3;
    }

    public Optional<LongFilter> optionalRecentPosts3() {
        return Optional.ofNullable(recentPosts3);
    }

    public LongFilter recentPosts3() {
        if (recentPosts3 == null) {
            setRecentPosts3(new LongFilter());
        }
        return recentPosts3;
    }

    public void setRecentPosts3(LongFilter recentPosts3) {
        this.recentPosts3 = recentPosts3;
    }

    public LongFilter getRecentPosts4() {
        return recentPosts4;
    }

    public Optional<LongFilter> optionalRecentPosts4() {
        return Optional.ofNullable(recentPosts4);
    }

    public LongFilter recentPosts4() {
        if (recentPosts4 == null) {
            setRecentPosts4(new LongFilter());
        }
        return recentPosts4;
    }

    public void setRecentPosts4(LongFilter recentPosts4) {
        this.recentPosts4 = recentPosts4;
    }

    public LongFilter getFeaturedArticles1() {
        return featuredArticles1;
    }

    public Optional<LongFilter> optionalFeaturedArticles1() {
        return Optional.ofNullable(featuredArticles1);
    }

    public LongFilter featuredArticles1() {
        if (featuredArticles1 == null) {
            setFeaturedArticles1(new LongFilter());
        }
        return featuredArticles1;
    }

    public void setFeaturedArticles1(LongFilter featuredArticles1) {
        this.featuredArticles1 = featuredArticles1;
    }

    public LongFilter getFeaturedArticles2() {
        return featuredArticles2;
    }

    public Optional<LongFilter> optionalFeaturedArticles2() {
        return Optional.ofNullable(featuredArticles2);
    }

    public LongFilter featuredArticles2() {
        if (featuredArticles2 == null) {
            setFeaturedArticles2(new LongFilter());
        }
        return featuredArticles2;
    }

    public void setFeaturedArticles2(LongFilter featuredArticles2) {
        this.featuredArticles2 = featuredArticles2;
    }

    public LongFilter getFeaturedArticles3() {
        return featuredArticles3;
    }

    public Optional<LongFilter> optionalFeaturedArticles3() {
        return Optional.ofNullable(featuredArticles3);
    }

    public LongFilter featuredArticles3() {
        if (featuredArticles3 == null) {
            setFeaturedArticles3(new LongFilter());
        }
        return featuredArticles3;
    }

    public void setFeaturedArticles3(LongFilter featuredArticles3) {
        this.featuredArticles3 = featuredArticles3;
    }

    public LongFilter getFeaturedArticles4() {
        return featuredArticles4;
    }

    public Optional<LongFilter> optionalFeaturedArticles4() {
        return Optional.ofNullable(featuredArticles4);
    }

    public LongFilter featuredArticles4() {
        if (featuredArticles4 == null) {
            setFeaturedArticles4(new LongFilter());
        }
        return featuredArticles4;
    }

    public void setFeaturedArticles4(LongFilter featuredArticles4) {
        this.featuredArticles4 = featuredArticles4;
    }

    public LongFilter getFeaturedArticles5() {
        return featuredArticles5;
    }

    public Optional<LongFilter> optionalFeaturedArticles5() {
        return Optional.ofNullable(featuredArticles5);
    }

    public LongFilter featuredArticles5() {
        if (featuredArticles5 == null) {
            setFeaturedArticles5(new LongFilter());
        }
        return featuredArticles5;
    }

    public void setFeaturedArticles5(LongFilter featuredArticles5) {
        this.featuredArticles5 = featuredArticles5;
    }

    public LongFilter getFeaturedArticles6() {
        return featuredArticles6;
    }

    public Optional<LongFilter> optionalFeaturedArticles6() {
        return Optional.ofNullable(featuredArticles6);
    }

    public LongFilter featuredArticles6() {
        if (featuredArticles6 == null) {
            setFeaturedArticles6(new LongFilter());
        }
        return featuredArticles6;
    }

    public void setFeaturedArticles6(LongFilter featuredArticles6) {
        this.featuredArticles6 = featuredArticles6;
    }

    public LongFilter getFeaturedArticles7() {
        return featuredArticles7;
    }

    public Optional<LongFilter> optionalFeaturedArticles7() {
        return Optional.ofNullable(featuredArticles7);
    }

    public LongFilter featuredArticles7() {
        if (featuredArticles7 == null) {
            setFeaturedArticles7(new LongFilter());
        }
        return featuredArticles7;
    }

    public void setFeaturedArticles7(LongFilter featuredArticles7) {
        this.featuredArticles7 = featuredArticles7;
    }

    public LongFilter getFeaturedArticles8() {
        return featuredArticles8;
    }

    public Optional<LongFilter> optionalFeaturedArticles8() {
        return Optional.ofNullable(featuredArticles8);
    }

    public LongFilter featuredArticles8() {
        if (featuredArticles8 == null) {
            setFeaturedArticles8(new LongFilter());
        }
        return featuredArticles8;
    }

    public void setFeaturedArticles8(LongFilter featuredArticles8) {
        this.featuredArticles8 = featuredArticles8;
    }

    public LongFilter getFeaturedArticles9() {
        return featuredArticles9;
    }

    public Optional<LongFilter> optionalFeaturedArticles9() {
        return Optional.ofNullable(featuredArticles9);
    }

    public LongFilter featuredArticles9() {
        if (featuredArticles9 == null) {
            setFeaturedArticles9(new LongFilter());
        }
        return featuredArticles9;
    }

    public void setFeaturedArticles9(LongFilter featuredArticles9) {
        this.featuredArticles9 = featuredArticles9;
    }

    public LongFilter getFeaturedArticles10() {
        return featuredArticles10;
    }

    public Optional<LongFilter> optionalFeaturedArticles10() {
        return Optional.ofNullable(featuredArticles10);
    }

    public LongFilter featuredArticles10() {
        if (featuredArticles10 == null) {
            setFeaturedArticles10(new LongFilter());
        }
        return featuredArticles10;
    }

    public void setFeaturedArticles10(LongFilter featuredArticles10) {
        this.featuredArticles10 = featuredArticles10;
    }

    public LongFilter getPopularNews1() {
        return popularNews1;
    }

    public Optional<LongFilter> optionalPopularNews1() {
        return Optional.ofNullable(popularNews1);
    }

    public LongFilter popularNews1() {
        if (popularNews1 == null) {
            setPopularNews1(new LongFilter());
        }
        return popularNews1;
    }

    public void setPopularNews1(LongFilter popularNews1) {
        this.popularNews1 = popularNews1;
    }

    public LongFilter getPopularNews2() {
        return popularNews2;
    }

    public Optional<LongFilter> optionalPopularNews2() {
        return Optional.ofNullable(popularNews2);
    }

    public LongFilter popularNews2() {
        if (popularNews2 == null) {
            setPopularNews2(new LongFilter());
        }
        return popularNews2;
    }

    public void setPopularNews2(LongFilter popularNews2) {
        this.popularNews2 = popularNews2;
    }

    public LongFilter getPopularNews3() {
        return popularNews3;
    }

    public Optional<LongFilter> optionalPopularNews3() {
        return Optional.ofNullable(popularNews3);
    }

    public LongFilter popularNews3() {
        if (popularNews3 == null) {
            setPopularNews3(new LongFilter());
        }
        return popularNews3;
    }

    public void setPopularNews3(LongFilter popularNews3) {
        this.popularNews3 = popularNews3;
    }

    public LongFilter getPopularNews4() {
        return popularNews4;
    }

    public Optional<LongFilter> optionalPopularNews4() {
        return Optional.ofNullable(popularNews4);
    }

    public LongFilter popularNews4() {
        if (popularNews4 == null) {
            setPopularNews4(new LongFilter());
        }
        return popularNews4;
    }

    public void setPopularNews4(LongFilter popularNews4) {
        this.popularNews4 = popularNews4;
    }

    public LongFilter getPopularNews5() {
        return popularNews5;
    }

    public Optional<LongFilter> optionalPopularNews5() {
        return Optional.ofNullable(popularNews5);
    }

    public LongFilter popularNews5() {
        if (popularNews5 == null) {
            setPopularNews5(new LongFilter());
        }
        return popularNews5;
    }

    public void setPopularNews5(LongFilter popularNews5) {
        this.popularNews5 = popularNews5;
    }

    public LongFilter getPopularNews6() {
        return popularNews6;
    }

    public Optional<LongFilter> optionalPopularNews6() {
        return Optional.ofNullable(popularNews6);
    }

    public LongFilter popularNews6() {
        if (popularNews6 == null) {
            setPopularNews6(new LongFilter());
        }
        return popularNews6;
    }

    public void setPopularNews6(LongFilter popularNews6) {
        this.popularNews6 = popularNews6;
    }

    public LongFilter getPopularNews7() {
        return popularNews7;
    }

    public Optional<LongFilter> optionalPopularNews7() {
        return Optional.ofNullable(popularNews7);
    }

    public LongFilter popularNews7() {
        if (popularNews7 == null) {
            setPopularNews7(new LongFilter());
        }
        return popularNews7;
    }

    public void setPopularNews7(LongFilter popularNews7) {
        this.popularNews7 = popularNews7;
    }

    public LongFilter getPopularNews8() {
        return popularNews8;
    }

    public Optional<LongFilter> optionalPopularNews8() {
        return Optional.ofNullable(popularNews8);
    }

    public LongFilter popularNews8() {
        if (popularNews8 == null) {
            setPopularNews8(new LongFilter());
        }
        return popularNews8;
    }

    public void setPopularNews8(LongFilter popularNews8) {
        this.popularNews8 = popularNews8;
    }

    public LongFilter getWeeklyNews1() {
        return weeklyNews1;
    }

    public Optional<LongFilter> optionalWeeklyNews1() {
        return Optional.ofNullable(weeklyNews1);
    }

    public LongFilter weeklyNews1() {
        if (weeklyNews1 == null) {
            setWeeklyNews1(new LongFilter());
        }
        return weeklyNews1;
    }

    public void setWeeklyNews1(LongFilter weeklyNews1) {
        this.weeklyNews1 = weeklyNews1;
    }

    public LongFilter getWeeklyNews2() {
        return weeklyNews2;
    }

    public Optional<LongFilter> optionalWeeklyNews2() {
        return Optional.ofNullable(weeklyNews2);
    }

    public LongFilter weeklyNews2() {
        if (weeklyNews2 == null) {
            setWeeklyNews2(new LongFilter());
        }
        return weeklyNews2;
    }

    public void setWeeklyNews2(LongFilter weeklyNews2) {
        this.weeklyNews2 = weeklyNews2;
    }

    public LongFilter getWeeklyNews3() {
        return weeklyNews3;
    }

    public Optional<LongFilter> optionalWeeklyNews3() {
        return Optional.ofNullable(weeklyNews3);
    }

    public LongFilter weeklyNews3() {
        if (weeklyNews3 == null) {
            setWeeklyNews3(new LongFilter());
        }
        return weeklyNews3;
    }

    public void setWeeklyNews3(LongFilter weeklyNews3) {
        this.weeklyNews3 = weeklyNews3;
    }

    public LongFilter getWeeklyNews4() {
        return weeklyNews4;
    }

    public Optional<LongFilter> optionalWeeklyNews4() {
        return Optional.ofNullable(weeklyNews4);
    }

    public LongFilter weeklyNews4() {
        if (weeklyNews4 == null) {
            setWeeklyNews4(new LongFilter());
        }
        return weeklyNews4;
    }

    public void setWeeklyNews4(LongFilter weeklyNews4) {
        this.weeklyNews4 = weeklyNews4;
    }

    public LongFilter getNewsFeeds1() {
        return newsFeeds1;
    }

    public Optional<LongFilter> optionalNewsFeeds1() {
        return Optional.ofNullable(newsFeeds1);
    }

    public LongFilter newsFeeds1() {
        if (newsFeeds1 == null) {
            setNewsFeeds1(new LongFilter());
        }
        return newsFeeds1;
    }

    public void setNewsFeeds1(LongFilter newsFeeds1) {
        this.newsFeeds1 = newsFeeds1;
    }

    public LongFilter getNewsFeeds2() {
        return newsFeeds2;
    }

    public Optional<LongFilter> optionalNewsFeeds2() {
        return Optional.ofNullable(newsFeeds2);
    }

    public LongFilter newsFeeds2() {
        if (newsFeeds2 == null) {
            setNewsFeeds2(new LongFilter());
        }
        return newsFeeds2;
    }

    public void setNewsFeeds2(LongFilter newsFeeds2) {
        this.newsFeeds2 = newsFeeds2;
    }

    public LongFilter getNewsFeeds3() {
        return newsFeeds3;
    }

    public Optional<LongFilter> optionalNewsFeeds3() {
        return Optional.ofNullable(newsFeeds3);
    }

    public LongFilter newsFeeds3() {
        if (newsFeeds3 == null) {
            setNewsFeeds3(new LongFilter());
        }
        return newsFeeds3;
    }

    public void setNewsFeeds3(LongFilter newsFeeds3) {
        this.newsFeeds3 = newsFeeds3;
    }

    public LongFilter getNewsFeeds4() {
        return newsFeeds4;
    }

    public Optional<LongFilter> optionalNewsFeeds4() {
        return Optional.ofNullable(newsFeeds4);
    }

    public LongFilter newsFeeds4() {
        if (newsFeeds4 == null) {
            setNewsFeeds4(new LongFilter());
        }
        return newsFeeds4;
    }

    public void setNewsFeeds4(LongFilter newsFeeds4) {
        this.newsFeeds4 = newsFeeds4;
    }

    public LongFilter getNewsFeeds5() {
        return newsFeeds5;
    }

    public Optional<LongFilter> optionalNewsFeeds5() {
        return Optional.ofNullable(newsFeeds5);
    }

    public LongFilter newsFeeds5() {
        if (newsFeeds5 == null) {
            setNewsFeeds5(new LongFilter());
        }
        return newsFeeds5;
    }

    public void setNewsFeeds5(LongFilter newsFeeds5) {
        this.newsFeeds5 = newsFeeds5;
    }

    public LongFilter getNewsFeeds6() {
        return newsFeeds6;
    }

    public Optional<LongFilter> optionalNewsFeeds6() {
        return Optional.ofNullable(newsFeeds6);
    }

    public LongFilter newsFeeds6() {
        if (newsFeeds6 == null) {
            setNewsFeeds6(new LongFilter());
        }
        return newsFeeds6;
    }

    public void setNewsFeeds6(LongFilter newsFeeds6) {
        this.newsFeeds6 = newsFeeds6;
    }

    public LongFilter getUsefulLinks1() {
        return usefulLinks1;
    }

    public Optional<LongFilter> optionalUsefulLinks1() {
        return Optional.ofNullable(usefulLinks1);
    }

    public LongFilter usefulLinks1() {
        if (usefulLinks1 == null) {
            setUsefulLinks1(new LongFilter());
        }
        return usefulLinks1;
    }

    public void setUsefulLinks1(LongFilter usefulLinks1) {
        this.usefulLinks1 = usefulLinks1;
    }

    public LongFilter getUsefulLinks2() {
        return usefulLinks2;
    }

    public Optional<LongFilter> optionalUsefulLinks2() {
        return Optional.ofNullable(usefulLinks2);
    }

    public LongFilter usefulLinks2() {
        if (usefulLinks2 == null) {
            setUsefulLinks2(new LongFilter());
        }
        return usefulLinks2;
    }

    public void setUsefulLinks2(LongFilter usefulLinks2) {
        this.usefulLinks2 = usefulLinks2;
    }

    public LongFilter getUsefulLinks3() {
        return usefulLinks3;
    }

    public Optional<LongFilter> optionalUsefulLinks3() {
        return Optional.ofNullable(usefulLinks3);
    }

    public LongFilter usefulLinks3() {
        if (usefulLinks3 == null) {
            setUsefulLinks3(new LongFilter());
        }
        return usefulLinks3;
    }

    public void setUsefulLinks3(LongFilter usefulLinks3) {
        this.usefulLinks3 = usefulLinks3;
    }

    public LongFilter getUsefulLinks4() {
        return usefulLinks4;
    }

    public Optional<LongFilter> optionalUsefulLinks4() {
        return Optional.ofNullable(usefulLinks4);
    }

    public LongFilter usefulLinks4() {
        if (usefulLinks4 == null) {
            setUsefulLinks4(new LongFilter());
        }
        return usefulLinks4;
    }

    public void setUsefulLinks4(LongFilter usefulLinks4) {
        this.usefulLinks4 = usefulLinks4;
    }

    public LongFilter getUsefulLinks5() {
        return usefulLinks5;
    }

    public Optional<LongFilter> optionalUsefulLinks5() {
        return Optional.ofNullable(usefulLinks5);
    }

    public LongFilter usefulLinks5() {
        if (usefulLinks5 == null) {
            setUsefulLinks5(new LongFilter());
        }
        return usefulLinks5;
    }

    public void setUsefulLinks5(LongFilter usefulLinks5) {
        this.usefulLinks5 = usefulLinks5;
    }

    public LongFilter getUsefulLinks6() {
        return usefulLinks6;
    }

    public Optional<LongFilter> optionalUsefulLinks6() {
        return Optional.ofNullable(usefulLinks6);
    }

    public LongFilter usefulLinks6() {
        if (usefulLinks6 == null) {
            setUsefulLinks6(new LongFilter());
        }
        return usefulLinks6;
    }

    public void setUsefulLinks6(LongFilter usefulLinks6) {
        this.usefulLinks6 = usefulLinks6;
    }

    public LongFilter getRecentVideos1() {
        return recentVideos1;
    }

    public Optional<LongFilter> optionalRecentVideos1() {
        return Optional.ofNullable(recentVideos1);
    }

    public LongFilter recentVideos1() {
        if (recentVideos1 == null) {
            setRecentVideos1(new LongFilter());
        }
        return recentVideos1;
    }

    public void setRecentVideos1(LongFilter recentVideos1) {
        this.recentVideos1 = recentVideos1;
    }

    public LongFilter getRecentVideos2() {
        return recentVideos2;
    }

    public Optional<LongFilter> optionalRecentVideos2() {
        return Optional.ofNullable(recentVideos2);
    }

    public LongFilter recentVideos2() {
        if (recentVideos2 == null) {
            setRecentVideos2(new LongFilter());
        }
        return recentVideos2;
    }

    public void setRecentVideos2(LongFilter recentVideos2) {
        this.recentVideos2 = recentVideos2;
    }

    public LongFilter getRecentVideos3() {
        return recentVideos3;
    }

    public Optional<LongFilter> optionalRecentVideos3() {
        return Optional.ofNullable(recentVideos3);
    }

    public LongFilter recentVideos3() {
        if (recentVideos3 == null) {
            setRecentVideos3(new LongFilter());
        }
        return recentVideos3;
    }

    public void setRecentVideos3(LongFilter recentVideos3) {
        this.recentVideos3 = recentVideos3;
    }

    public LongFilter getRecentVideos4() {
        return recentVideos4;
    }

    public Optional<LongFilter> optionalRecentVideos4() {
        return Optional.ofNullable(recentVideos4);
    }

    public LongFilter recentVideos4() {
        if (recentVideos4 == null) {
            setRecentVideos4(new LongFilter());
        }
        return recentVideos4;
    }

    public void setRecentVideos4(LongFilter recentVideos4) {
        this.recentVideos4 = recentVideos4;
    }

    public LongFilter getRecentVideos5() {
        return recentVideos5;
    }

    public Optional<LongFilter> optionalRecentVideos5() {
        return Optional.ofNullable(recentVideos5);
    }

    public LongFilter recentVideos5() {
        if (recentVideos5 == null) {
            setRecentVideos5(new LongFilter());
        }
        return recentVideos5;
    }

    public void setRecentVideos5(LongFilter recentVideos5) {
        this.recentVideos5 = recentVideos5;
    }

    public LongFilter getRecentVideos6() {
        return recentVideos6;
    }

    public Optional<LongFilter> optionalRecentVideos6() {
        return Optional.ofNullable(recentVideos6);
    }

    public LongFilter recentVideos6() {
        if (recentVideos6 == null) {
            setRecentVideos6(new LongFilter());
        }
        return recentVideos6;
    }

    public void setRecentVideos6(LongFilter recentVideos6) {
        this.recentVideos6 = recentVideos6;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FrontpageconfigCriteria that = (FrontpageconfigCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(topNews1, that.topNews1) &&
            Objects.equals(topNews2, that.topNews2) &&
            Objects.equals(topNews3, that.topNews3) &&
            Objects.equals(topNews4, that.topNews4) &&
            Objects.equals(topNews5, that.topNews5) &&
            Objects.equals(latestNews1, that.latestNews1) &&
            Objects.equals(latestNews2, that.latestNews2) &&
            Objects.equals(latestNews3, that.latestNews3) &&
            Objects.equals(latestNews4, that.latestNews4) &&
            Objects.equals(latestNews5, that.latestNews5) &&
            Objects.equals(breakingNews1, that.breakingNews1) &&
            Objects.equals(recentPosts1, that.recentPosts1) &&
            Objects.equals(recentPosts2, that.recentPosts2) &&
            Objects.equals(recentPosts3, that.recentPosts3) &&
            Objects.equals(recentPosts4, that.recentPosts4) &&
            Objects.equals(featuredArticles1, that.featuredArticles1) &&
            Objects.equals(featuredArticles2, that.featuredArticles2) &&
            Objects.equals(featuredArticles3, that.featuredArticles3) &&
            Objects.equals(featuredArticles4, that.featuredArticles4) &&
            Objects.equals(featuredArticles5, that.featuredArticles5) &&
            Objects.equals(featuredArticles6, that.featuredArticles6) &&
            Objects.equals(featuredArticles7, that.featuredArticles7) &&
            Objects.equals(featuredArticles8, that.featuredArticles8) &&
            Objects.equals(featuredArticles9, that.featuredArticles9) &&
            Objects.equals(featuredArticles10, that.featuredArticles10) &&
            Objects.equals(popularNews1, that.popularNews1) &&
            Objects.equals(popularNews2, that.popularNews2) &&
            Objects.equals(popularNews3, that.popularNews3) &&
            Objects.equals(popularNews4, that.popularNews4) &&
            Objects.equals(popularNews5, that.popularNews5) &&
            Objects.equals(popularNews6, that.popularNews6) &&
            Objects.equals(popularNews7, that.popularNews7) &&
            Objects.equals(popularNews8, that.popularNews8) &&
            Objects.equals(weeklyNews1, that.weeklyNews1) &&
            Objects.equals(weeklyNews2, that.weeklyNews2) &&
            Objects.equals(weeklyNews3, that.weeklyNews3) &&
            Objects.equals(weeklyNews4, that.weeklyNews4) &&
            Objects.equals(newsFeeds1, that.newsFeeds1) &&
            Objects.equals(newsFeeds2, that.newsFeeds2) &&
            Objects.equals(newsFeeds3, that.newsFeeds3) &&
            Objects.equals(newsFeeds4, that.newsFeeds4) &&
            Objects.equals(newsFeeds5, that.newsFeeds5) &&
            Objects.equals(newsFeeds6, that.newsFeeds6) &&
            Objects.equals(usefulLinks1, that.usefulLinks1) &&
            Objects.equals(usefulLinks2, that.usefulLinks2) &&
            Objects.equals(usefulLinks3, that.usefulLinks3) &&
            Objects.equals(usefulLinks4, that.usefulLinks4) &&
            Objects.equals(usefulLinks5, that.usefulLinks5) &&
            Objects.equals(usefulLinks6, that.usefulLinks6) &&
            Objects.equals(recentVideos1, that.recentVideos1) &&
            Objects.equals(recentVideos2, that.recentVideos2) &&
            Objects.equals(recentVideos3, that.recentVideos3) &&
            Objects.equals(recentVideos4, that.recentVideos4) &&
            Objects.equals(recentVideos5, that.recentVideos5) &&
            Objects.equals(recentVideos6, that.recentVideos6) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            creationDate,
            topNews1,
            topNews2,
            topNews3,
            topNews4,
            topNews5,
            latestNews1,
            latestNews2,
            latestNews3,
            latestNews4,
            latestNews5,
            breakingNews1,
            recentPosts1,
            recentPosts2,
            recentPosts3,
            recentPosts4,
            featuredArticles1,
            featuredArticles2,
            featuredArticles3,
            featuredArticles4,
            featuredArticles5,
            featuredArticles6,
            featuredArticles7,
            featuredArticles8,
            featuredArticles9,
            featuredArticles10,
            popularNews1,
            popularNews2,
            popularNews3,
            popularNews4,
            popularNews5,
            popularNews6,
            popularNews7,
            popularNews8,
            weeklyNews1,
            weeklyNews2,
            weeklyNews3,
            weeklyNews4,
            newsFeeds1,
            newsFeeds2,
            newsFeeds3,
            newsFeeds4,
            newsFeeds5,
            newsFeeds6,
            usefulLinks1,
            usefulLinks2,
            usefulLinks3,
            usefulLinks4,
            usefulLinks5,
            usefulLinks6,
            recentVideos1,
            recentVideos2,
            recentVideos3,
            recentVideos4,
            recentVideos5,
            recentVideos6,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FrontpageconfigCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCreationDate().map(f -> "creationDate=" + f + ", ").orElse("") +
            optionalTopNews1().map(f -> "topNews1=" + f + ", ").orElse("") +
            optionalTopNews2().map(f -> "topNews2=" + f + ", ").orElse("") +
            optionalTopNews3().map(f -> "topNews3=" + f + ", ").orElse("") +
            optionalTopNews4().map(f -> "topNews4=" + f + ", ").orElse("") +
            optionalTopNews5().map(f -> "topNews5=" + f + ", ").orElse("") +
            optionalLatestNews1().map(f -> "latestNews1=" + f + ", ").orElse("") +
            optionalLatestNews2().map(f -> "latestNews2=" + f + ", ").orElse("") +
            optionalLatestNews3().map(f -> "latestNews3=" + f + ", ").orElse("") +
            optionalLatestNews4().map(f -> "latestNews4=" + f + ", ").orElse("") +
            optionalLatestNews5().map(f -> "latestNews5=" + f + ", ").orElse("") +
            optionalBreakingNews1().map(f -> "breakingNews1=" + f + ", ").orElse("") +
            optionalRecentPosts1().map(f -> "recentPosts1=" + f + ", ").orElse("") +
            optionalRecentPosts2().map(f -> "recentPosts2=" + f + ", ").orElse("") +
            optionalRecentPosts3().map(f -> "recentPosts3=" + f + ", ").orElse("") +
            optionalRecentPosts4().map(f -> "recentPosts4=" + f + ", ").orElse("") +
            optionalFeaturedArticles1().map(f -> "featuredArticles1=" + f + ", ").orElse("") +
            optionalFeaturedArticles2().map(f -> "featuredArticles2=" + f + ", ").orElse("") +
            optionalFeaturedArticles3().map(f -> "featuredArticles3=" + f + ", ").orElse("") +
            optionalFeaturedArticles4().map(f -> "featuredArticles4=" + f + ", ").orElse("") +
            optionalFeaturedArticles5().map(f -> "featuredArticles5=" + f + ", ").orElse("") +
            optionalFeaturedArticles6().map(f -> "featuredArticles6=" + f + ", ").orElse("") +
            optionalFeaturedArticles7().map(f -> "featuredArticles7=" + f + ", ").orElse("") +
            optionalFeaturedArticles8().map(f -> "featuredArticles8=" + f + ", ").orElse("") +
            optionalFeaturedArticles9().map(f -> "featuredArticles9=" + f + ", ").orElse("") +
            optionalFeaturedArticles10().map(f -> "featuredArticles10=" + f + ", ").orElse("") +
            optionalPopularNews1().map(f -> "popularNews1=" + f + ", ").orElse("") +
            optionalPopularNews2().map(f -> "popularNews2=" + f + ", ").orElse("") +
            optionalPopularNews3().map(f -> "popularNews3=" + f + ", ").orElse("") +
            optionalPopularNews4().map(f -> "popularNews4=" + f + ", ").orElse("") +
            optionalPopularNews5().map(f -> "popularNews5=" + f + ", ").orElse("") +
            optionalPopularNews6().map(f -> "popularNews6=" + f + ", ").orElse("") +
            optionalPopularNews7().map(f -> "popularNews7=" + f + ", ").orElse("") +
            optionalPopularNews8().map(f -> "popularNews8=" + f + ", ").orElse("") +
            optionalWeeklyNews1().map(f -> "weeklyNews1=" + f + ", ").orElse("") +
            optionalWeeklyNews2().map(f -> "weeklyNews2=" + f + ", ").orElse("") +
            optionalWeeklyNews3().map(f -> "weeklyNews3=" + f + ", ").orElse("") +
            optionalWeeklyNews4().map(f -> "weeklyNews4=" + f + ", ").orElse("") +
            optionalNewsFeeds1().map(f -> "newsFeeds1=" + f + ", ").orElse("") +
            optionalNewsFeeds2().map(f -> "newsFeeds2=" + f + ", ").orElse("") +
            optionalNewsFeeds3().map(f -> "newsFeeds3=" + f + ", ").orElse("") +
            optionalNewsFeeds4().map(f -> "newsFeeds4=" + f + ", ").orElse("") +
            optionalNewsFeeds5().map(f -> "newsFeeds5=" + f + ", ").orElse("") +
            optionalNewsFeeds6().map(f -> "newsFeeds6=" + f + ", ").orElse("") +
            optionalUsefulLinks1().map(f -> "usefulLinks1=" + f + ", ").orElse("") +
            optionalUsefulLinks2().map(f -> "usefulLinks2=" + f + ", ").orElse("") +
            optionalUsefulLinks3().map(f -> "usefulLinks3=" + f + ", ").orElse("") +
            optionalUsefulLinks4().map(f -> "usefulLinks4=" + f + ", ").orElse("") +
            optionalUsefulLinks5().map(f -> "usefulLinks5=" + f + ", ").orElse("") +
            optionalUsefulLinks6().map(f -> "usefulLinks6=" + f + ", ").orElse("") +
            optionalRecentVideos1().map(f -> "recentVideos1=" + f + ", ").orElse("") +
            optionalRecentVideos2().map(f -> "recentVideos2=" + f + ", ").orElse("") +
            optionalRecentVideos3().map(f -> "recentVideos3=" + f + ", ").orElse("") +
            optionalRecentVideos4().map(f -> "recentVideos4=" + f + ", ").orElse("") +
            optionalRecentVideos5().map(f -> "recentVideos5=" + f + ", ").orElse("") +
            optionalRecentVideos6().map(f -> "recentVideos6=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
