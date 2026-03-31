package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Blog;
import com.opencode.test.repository.BlogRepository;
import com.opencode.test.service.criteria.BlogCriteria;
import com.opencode.test.service.dto.BlogDTO;
import com.opencode.test.service.mapper.BlogMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Blog} entities in the database.
 * The main input is a {@link BlogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link BlogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BlogQueryService extends QueryService<Blog> {

    private static final Logger LOG = LoggerFactory.getLogger(BlogQueryService.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;

    public BlogQueryService(BlogRepository blogRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
    }

    /**
     * Return a {@link Page} of {@link BlogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BlogDTO> findByCriteria(BlogCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Blog> specification = createSpecification(criteria);
        return blogRepository.findAll(specification, page).map(blogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BlogCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Blog> specification = createSpecification(criteria);
        return blogRepository.count(specification);
    }

    /**
     * Function to convert {@link BlogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Blog> createSpecification(BlogCriteria criteria) {
        Specification<Blog> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Blog_.id),
                buildRangeSpecification(criteria.getCreationDate(), Blog_.creationDate),
                buildStringSpecification(criteria.getTitle(), Blog_.title),
                buildSpecification(criteria.getPostId(), root -> root.join(Blog_.posts, JoinType.LEFT).get(Post_.id)),
                buildSpecification(criteria.getAppuserId(), root -> root.join(Blog_.appuser, JoinType.LEFT).get(Appuser_.id)),
                buildSpecification(criteria.getCommunityId(), root -> root.join(Blog_.community, JoinType.LEFT).get(Community_.id))
            );
        }
        return specification;
    }
}
