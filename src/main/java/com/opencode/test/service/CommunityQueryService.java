package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Community;
import com.opencode.test.repository.CommunityRepository;
import com.opencode.test.service.criteria.CommunityCriteria;
import com.opencode.test.service.dto.CommunityDTO;
import com.opencode.test.service.mapper.CommunityMapper;
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
 * Service for executing complex queries for {@link Community} entities in the database.
 * The main input is a {@link CommunityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CommunityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommunityQueryService extends QueryService<Community> {

    private static final Logger LOG = LoggerFactory.getLogger(CommunityQueryService.class);

    private final CommunityRepository communityRepository;

    private final CommunityMapper communityMapper;

    public CommunityQueryService(CommunityRepository communityRepository, CommunityMapper communityMapper) {
        this.communityRepository = communityRepository;
        this.communityMapper = communityMapper;
    }

    /**
     * Return a {@link Page} of {@link CommunityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommunityDTO> findByCriteria(CommunityCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Community> specification = createSpecification(criteria);
        return communityRepository.findAll(specification, page).map(communityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommunityCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Community> specification = createSpecification(criteria);
        return communityRepository.count(specification);
    }

    /**
     * Function to convert {@link CommunityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Community> createSpecification(CommunityCriteria criteria) {
        Specification<Community> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Community_.id),
                buildRangeSpecification(criteria.getCreationDate(), Community_.creationDate),
                buildStringSpecification(criteria.getCommunityName(), Community_.communityName),
                buildStringSpecification(criteria.getCommunityDescription(), Community_.communityDescription),
                buildSpecification(criteria.getIsActive(), Community_.isActive),
                buildSpecification(criteria.getBlogId(), root -> root.join(Community_.blogs, JoinType.LEFT).get(Blog_.id)),
                buildSpecification(criteria.getCfollowedId(), root -> root.join(Community_.cfolloweds, JoinType.LEFT).get(Follow_.id)),
                buildSpecification(criteria.getCfollowingId(), root -> root.join(Community_.cfollowings, JoinType.LEFT).get(Follow_.id)),
                buildSpecification(criteria.getCblockeduserId(), root ->
                    root.join(Community_.cblockedusers, JoinType.LEFT).get(Blockuser_.id)
                ),
                buildSpecification(criteria.getCblockinguserId(), root ->
                    root.join(Community_.cblockingusers, JoinType.LEFT).get(Blockuser_.id)
                ),
                buildSpecification(criteria.getAppuserId(), root -> root.join(Community_.appuser, JoinType.LEFT).get(Appuser_.id)),
                buildSpecification(criteria.getCinterestId(), root -> root.join(Community_.cinterests, JoinType.LEFT).get(Cinterest_.id)),
                buildSpecification(criteria.getCactivityId(), root -> root.join(Community_.cactivities, JoinType.LEFT).get(Cactivity_.id)),
                buildSpecification(criteria.getCcelebId(), root -> root.join(Community_.ccelebs, JoinType.LEFT).get(Cceleb_.id))
            );
        }
        return specification;
    }
}
