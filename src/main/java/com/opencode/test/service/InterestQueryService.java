package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Interest;
import com.opencode.test.repository.InterestRepository;
import com.opencode.test.service.criteria.InterestCriteria;
import com.opencode.test.service.dto.InterestDTO;
import com.opencode.test.service.mapper.InterestMapper;
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
 * Service for executing complex queries for {@link Interest} entities in the database.
 * The main input is a {@link InterestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link InterestDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InterestQueryService extends QueryService<Interest> {

    private static final Logger LOG = LoggerFactory.getLogger(InterestQueryService.class);

    private final InterestRepository interestRepository;

    private final InterestMapper interestMapper;

    public InterestQueryService(InterestRepository interestRepository, InterestMapper interestMapper) {
        this.interestRepository = interestRepository;
        this.interestMapper = interestMapper;
    }

    /**
     * Return a {@link Page} of {@link InterestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InterestDTO> findByCriteria(InterestCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Interest> specification = createSpecification(criteria);
        return interestRepository.fetchBagRelationships(interestRepository.findAll(specification, page)).map(interestMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InterestCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Interest> specification = createSpecification(criteria);
        return interestRepository.count(specification);
    }

    /**
     * Function to convert {@link InterestCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Interest> createSpecification(InterestCriteria criteria) {
        Specification<Interest> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Interest_.id),
                buildStringSpecification(criteria.getInterestName(), Interest_.interestName),
                buildSpecification(criteria.getAppuserId(), root -> root.join(Interest_.appusers, JoinType.LEFT).get(Appuser_.id))
            );
        }
        return specification;
    }
}
