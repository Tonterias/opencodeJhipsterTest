package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Cactivity;
import com.opencode.test.repository.CactivityRepository;
import com.opencode.test.service.criteria.CactivityCriteria;
import com.opencode.test.service.dto.CactivityDTO;
import com.opencode.test.service.mapper.CactivityMapper;
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
 * Service for executing complex queries for {@link Cactivity} entities in the database.
 * The main input is a {@link CactivityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CactivityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CactivityQueryService extends QueryService<Cactivity> {

    private static final Logger LOG = LoggerFactory.getLogger(CactivityQueryService.class);

    private final CactivityRepository cactivityRepository;

    private final CactivityMapper cactivityMapper;

    public CactivityQueryService(CactivityRepository cactivityRepository, CactivityMapper cactivityMapper) {
        this.cactivityRepository = cactivityRepository;
        this.cactivityMapper = cactivityMapper;
    }

    /**
     * Return a {@link Page} of {@link CactivityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CactivityDTO> findByCriteria(CactivityCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cactivity> specification = createSpecification(criteria);
        return cactivityRepository.fetchBagRelationships(cactivityRepository.findAll(specification, page)).map(cactivityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CactivityCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Cactivity> specification = createSpecification(criteria);
        return cactivityRepository.count(specification);
    }

    /**
     * Function to convert {@link CactivityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cactivity> createSpecification(CactivityCriteria criteria) {
        Specification<Cactivity> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Cactivity_.id),
                buildStringSpecification(criteria.getActivityName(), Cactivity_.activityName),
                buildSpecification(criteria.getCommunityId(), root -> root.join(Cactivity_.communities, JoinType.LEFT).get(Community_.id))
            );
        }
        return specification;
    }
}
