package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Frontpageconfig;
import com.opencode.test.repository.FrontpageconfigRepository;
import com.opencode.test.service.criteria.FrontpageconfigCriteria;
import com.opencode.test.service.dto.FrontpageconfigDTO;
import com.opencode.test.service.mapper.FrontpageconfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Frontpageconfig} entities in the database.
 * The main input is a {@link FrontpageconfigCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FrontpageconfigDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FrontpageconfigQueryService extends QueryService<Frontpageconfig> {

    private static final Logger LOG = LoggerFactory.getLogger(FrontpageconfigQueryService.class);

    private final FrontpageconfigRepository frontpageconfigRepository;

    private final FrontpageconfigMapper frontpageconfigMapper;

    public FrontpageconfigQueryService(FrontpageconfigRepository frontpageconfigRepository, FrontpageconfigMapper frontpageconfigMapper) {
        this.frontpageconfigRepository = frontpageconfigRepository;
        this.frontpageconfigMapper = frontpageconfigMapper;
    }

    /**
     * Return a {@link Page} of {@link FrontpageconfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FrontpageconfigDTO> findByCriteria(FrontpageconfigCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Frontpageconfig> specification = createSpecification(criteria);
        return frontpageconfigRepository.findAll(specification, page).map(frontpageconfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FrontpageconfigCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Frontpageconfig> specification = createSpecification(criteria);
        return frontpageconfigRepository.count(specification);
    }

    /**
     * Function to convert {@link FrontpageconfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Frontpageconfig> createSpecification(FrontpageconfigCriteria criteria) {
        Specification<Frontpageconfig> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Frontpageconfig_.id),
                buildRangeSpecification(criteria.getCreationDate(), Frontpageconfig_.creationDate),
                buildRangeSpecification(criteria.getTopNews1(), Frontpageconfig_.topNews1),
                buildRangeSpecification(criteria.getTopNews2(), Frontpageconfig_.topNews2),
                buildRangeSpecification(criteria.getTopNews3(), Frontpageconfig_.topNews3),
                buildRangeSpecification(criteria.getTopNews4(), Frontpageconfig_.topNews4),
                buildRangeSpecification(criteria.getTopNews5(), Frontpageconfig_.topNews5),
                buildRangeSpecification(criteria.getLatestNews1(), Frontpageconfig_.latestNews1),
                buildRangeSpecification(criteria.getLatestNews2(), Frontpageconfig_.latestNews2),
                buildRangeSpecification(criteria.getLatestNews3(), Frontpageconfig_.latestNews3),
                buildRangeSpecification(criteria.getLatestNews4(), Frontpageconfig_.latestNews4),
                buildRangeSpecification(criteria.getLatestNews5(), Frontpageconfig_.latestNews5),
                buildRangeSpecification(criteria.getBreakingNews1(), Frontpageconfig_.breakingNews1),
                buildRangeSpecification(criteria.getRecentPosts1(), Frontpageconfig_.recentPosts1),
                buildRangeSpecification(criteria.getRecentPosts2(), Frontpageconfig_.recentPosts2),
                buildRangeSpecification(criteria.getRecentPosts3(), Frontpageconfig_.recentPosts3),
                buildRangeSpecification(criteria.getRecentPosts4(), Frontpageconfig_.recentPosts4),
                buildRangeSpecification(criteria.getFeaturedArticles1(), Frontpageconfig_.featuredArticles1),
                buildRangeSpecification(criteria.getFeaturedArticles2(), Frontpageconfig_.featuredArticles2),
                buildRangeSpecification(criteria.getFeaturedArticles3(), Frontpageconfig_.featuredArticles3),
                buildRangeSpecification(criteria.getFeaturedArticles4(), Frontpageconfig_.featuredArticles4),
                buildRangeSpecification(criteria.getFeaturedArticles5(), Frontpageconfig_.featuredArticles5),
                buildRangeSpecification(criteria.getFeaturedArticles6(), Frontpageconfig_.featuredArticles6),
                buildRangeSpecification(criteria.getFeaturedArticles7(), Frontpageconfig_.featuredArticles7),
                buildRangeSpecification(criteria.getFeaturedArticles8(), Frontpageconfig_.featuredArticles8),
                buildRangeSpecification(criteria.getFeaturedArticles9(), Frontpageconfig_.featuredArticles9),
                buildRangeSpecification(criteria.getFeaturedArticles10(), Frontpageconfig_.featuredArticles10),
                buildRangeSpecification(criteria.getPopularNews1(), Frontpageconfig_.popularNews1),
                buildRangeSpecification(criteria.getPopularNews2(), Frontpageconfig_.popularNews2),
                buildRangeSpecification(criteria.getPopularNews3(), Frontpageconfig_.popularNews3),
                buildRangeSpecification(criteria.getPopularNews4(), Frontpageconfig_.popularNews4),
                buildRangeSpecification(criteria.getPopularNews5(), Frontpageconfig_.popularNews5),
                buildRangeSpecification(criteria.getPopularNews6(), Frontpageconfig_.popularNews6),
                buildRangeSpecification(criteria.getPopularNews7(), Frontpageconfig_.popularNews7),
                buildRangeSpecification(criteria.getPopularNews8(), Frontpageconfig_.popularNews8),
                buildRangeSpecification(criteria.getWeeklyNews1(), Frontpageconfig_.weeklyNews1),
                buildRangeSpecification(criteria.getWeeklyNews2(), Frontpageconfig_.weeklyNews2),
                buildRangeSpecification(criteria.getWeeklyNews3(), Frontpageconfig_.weeklyNews3),
                buildRangeSpecification(criteria.getWeeklyNews4(), Frontpageconfig_.weeklyNews4),
                buildRangeSpecification(criteria.getNewsFeeds1(), Frontpageconfig_.newsFeeds1),
                buildRangeSpecification(criteria.getNewsFeeds2(), Frontpageconfig_.newsFeeds2),
                buildRangeSpecification(criteria.getNewsFeeds3(), Frontpageconfig_.newsFeeds3),
                buildRangeSpecification(criteria.getNewsFeeds4(), Frontpageconfig_.newsFeeds4),
                buildRangeSpecification(criteria.getNewsFeeds5(), Frontpageconfig_.newsFeeds5),
                buildRangeSpecification(criteria.getNewsFeeds6(), Frontpageconfig_.newsFeeds6),
                buildRangeSpecification(criteria.getUsefulLinks1(), Frontpageconfig_.usefulLinks1),
                buildRangeSpecification(criteria.getUsefulLinks2(), Frontpageconfig_.usefulLinks2),
                buildRangeSpecification(criteria.getUsefulLinks3(), Frontpageconfig_.usefulLinks3),
                buildRangeSpecification(criteria.getUsefulLinks4(), Frontpageconfig_.usefulLinks4),
                buildRangeSpecification(criteria.getUsefulLinks5(), Frontpageconfig_.usefulLinks5),
                buildRangeSpecification(criteria.getUsefulLinks6(), Frontpageconfig_.usefulLinks6),
                buildRangeSpecification(criteria.getRecentVideos1(), Frontpageconfig_.recentVideos1),
                buildRangeSpecification(criteria.getRecentVideos2(), Frontpageconfig_.recentVideos2),
                buildRangeSpecification(criteria.getRecentVideos3(), Frontpageconfig_.recentVideos3),
                buildRangeSpecification(criteria.getRecentVideos4(), Frontpageconfig_.recentVideos4),
                buildRangeSpecification(criteria.getRecentVideos5(), Frontpageconfig_.recentVideos5),
                buildRangeSpecification(criteria.getRecentVideos6(), Frontpageconfig_.recentVideos6)
            );
        }
        return specification;
    }
}
