package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Cinterest;
import com.opencode.test.repository.CinterestRepository;
import com.opencode.test.service.criteria.CinterestCriteria;
import com.opencode.test.service.dto.CinterestDTO;
import com.opencode.test.service.mapper.CinterestMapper;
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
 * Service for executing complex queries for {@link Cinterest} entities in the database.
 * The main input is a {@link CinterestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CinterestDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CinterestQueryService extends QueryService<Cinterest> {

    private static final Logger LOG = LoggerFactory.getLogger(CinterestQueryService.class);

    private final CinterestRepository cinterestRepository;

    private final CinterestMapper cinterestMapper;

    public CinterestQueryService(CinterestRepository cinterestRepository, CinterestMapper cinterestMapper) {
        this.cinterestRepository = cinterestRepository;
        this.cinterestMapper = cinterestMapper;
    }

    /**
     * Return a {@link Page} of {@link CinterestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CinterestDTO> findByCriteria(CinterestCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cinterest> specification = createSpecification(criteria);
        return cinterestRepository.fetchBagRelationships(cinterestRepository.findAll(specification, page)).map(cinterestMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CinterestCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Cinterest> specification = createSpecification(criteria);
        return cinterestRepository.count(specification);
    }

    /**
     * Function to convert {@link CinterestCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cinterest> createSpecification(CinterestCriteria criteria) {
        Specification<Cinterest> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Cinterest_.id),
                buildStringSpecification(criteria.getInterestName(), Cinterest_.interestName),
                buildSpecification(criteria.getCommunityId(), root -> root.join(Cinterest_.communities, JoinType.LEFT).get(Community_.id))
            );
        }
        return specification;
    }
}
