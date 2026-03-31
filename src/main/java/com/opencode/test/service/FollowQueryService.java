package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Follow;
import com.opencode.test.repository.FollowRepository;
import com.opencode.test.service.criteria.FollowCriteria;
import com.opencode.test.service.dto.FollowDTO;
import com.opencode.test.service.mapper.FollowMapper;
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
 * Service for executing complex queries for {@link Follow} entities in the database.
 * The main input is a {@link FollowCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FollowDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FollowQueryService extends QueryService<Follow> {

    private static final Logger LOG = LoggerFactory.getLogger(FollowQueryService.class);

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    public FollowQueryService(FollowRepository followRepository, FollowMapper followMapper) {
        this.followRepository = followRepository;
        this.followMapper = followMapper;
    }

    /**
     * Return a {@link Page} of {@link FollowDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FollowDTO> findByCriteria(FollowCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Follow> specification = createSpecification(criteria);
        return followRepository.findAll(specification, page).map(followMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FollowCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Follow> specification = createSpecification(criteria);
        return followRepository.count(specification);
    }

    /**
     * Function to convert {@link FollowCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Follow> createSpecification(FollowCriteria criteria) {
        Specification<Follow> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Follow_.id),
                buildRangeSpecification(criteria.getCreationDate(), Follow_.creationDate),
                buildSpecification(criteria.getFollowedId(), root -> root.join(Follow_.followed, JoinType.LEFT).get(Appuser_.id)),
                buildSpecification(criteria.getFollowingId(), root -> root.join(Follow_.following, JoinType.LEFT).get(Appuser_.id)),
                buildSpecification(criteria.getCfollowedId(), root -> root.join(Follow_.cfollowed, JoinType.LEFT).get(Community_.id)),
                buildSpecification(criteria.getCfollowingId(), root -> root.join(Follow_.cfollowing, JoinType.LEFT).get(Community_.id))
            );
        }
        return specification;
    }
}
