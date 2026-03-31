package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Urllink;
import com.opencode.test.repository.UrllinkRepository;
import com.opencode.test.service.criteria.UrllinkCriteria;
import com.opencode.test.service.dto.UrllinkDTO;
import com.opencode.test.service.mapper.UrllinkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Urllink} entities in the database.
 * The main input is a {@link UrllinkCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link UrllinkDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UrllinkQueryService extends QueryService<Urllink> {

    private static final Logger LOG = LoggerFactory.getLogger(UrllinkQueryService.class);

    private final UrllinkRepository urllinkRepository;

    private final UrllinkMapper urllinkMapper;

    public UrllinkQueryService(UrllinkRepository urllinkRepository, UrllinkMapper urllinkMapper) {
        this.urllinkRepository = urllinkRepository;
        this.urllinkMapper = urllinkMapper;
    }

    /**
     * Return a {@link Page} of {@link UrllinkDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UrllinkDTO> findByCriteria(UrllinkCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Urllink> specification = createSpecification(criteria);
        return urllinkRepository.findAll(specification, page).map(urllinkMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UrllinkCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Urllink> specification = createSpecification(criteria);
        return urllinkRepository.count(specification);
    }

    /**
     * Function to convert {@link UrllinkCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Urllink> createSpecification(UrllinkCriteria criteria) {
        Specification<Urllink> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Urllink_.id),
                buildStringSpecification(criteria.getLinkText(), Urllink_.linkText),
                buildStringSpecification(criteria.getLinkURL(), Urllink_.linkURL)
            );
        }
        return specification;
    }
}
