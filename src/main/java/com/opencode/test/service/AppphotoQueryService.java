package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.Appphoto;
import com.opencode.test.repository.AppphotoRepository;
import com.opencode.test.service.criteria.AppphotoCriteria;
import com.opencode.test.service.dto.AppphotoDTO;
import com.opencode.test.service.mapper.AppphotoMapper;
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
 * Service for executing complex queries for {@link Appphoto} entities in the database.
 * The main input is a {@link AppphotoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AppphotoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppphotoQueryService extends QueryService<Appphoto> {

    private static final Logger LOG = LoggerFactory.getLogger(AppphotoQueryService.class);

    private final AppphotoRepository appphotoRepository;

    private final AppphotoMapper appphotoMapper;

    public AppphotoQueryService(AppphotoRepository appphotoRepository, AppphotoMapper appphotoMapper) {
        this.appphotoRepository = appphotoRepository;
        this.appphotoMapper = appphotoMapper;
    }

    /**
     * Return a {@link Page} of {@link AppphotoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppphotoDTO> findByCriteria(AppphotoCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Appphoto> specification = createSpecification(criteria);
        return appphotoRepository.findAll(specification, page).map(appphotoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppphotoCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Appphoto> specification = createSpecification(criteria);
        return appphotoRepository.count(specification);
    }

    /**
     * Function to convert {@link AppphotoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Appphoto> createSpecification(AppphotoCriteria criteria) {
        Specification<Appphoto> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), Appphoto_.id),
                buildRangeSpecification(criteria.getCreationDate(), Appphoto_.creationDate),
                buildSpecification(criteria.getAppuserId(), root -> root.join(Appphoto_.appuser, JoinType.LEFT).get(Appuser_.id))
            );
        }
        return specification;
    }
}
