package com.opencode.test.service;

import com.opencode.test.domain.*; // for static metamodels
import com.opencode.test.domain.ConfigVariables;
import com.opencode.test.repository.ConfigVariablesRepository;
import com.opencode.test.service.criteria.ConfigVariablesCriteria;
import com.opencode.test.service.dto.ConfigVariablesDTO;
import com.opencode.test.service.mapper.ConfigVariablesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ConfigVariables} entities in the database.
 * The main input is a {@link ConfigVariablesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ConfigVariablesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConfigVariablesQueryService extends QueryService<ConfigVariables> {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigVariablesQueryService.class);

    private final ConfigVariablesRepository configVariablesRepository;

    private final ConfigVariablesMapper configVariablesMapper;

    public ConfigVariablesQueryService(ConfigVariablesRepository configVariablesRepository, ConfigVariablesMapper configVariablesMapper) {
        this.configVariablesRepository = configVariablesRepository;
        this.configVariablesMapper = configVariablesMapper;
    }

    /**
     * Return a {@link Page} of {@link ConfigVariablesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfigVariablesDTO> findByCriteria(ConfigVariablesCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConfigVariables> specification = createSpecification(criteria);
        return configVariablesRepository.findAll(specification, page).map(configVariablesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConfigVariablesCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ConfigVariables> specification = createSpecification(criteria);
        return configVariablesRepository.count(specification);
    }

    /**
     * Function to convert {@link ConfigVariablesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConfigVariables> createSpecification(ConfigVariablesCriteria criteria) {
        Specification<ConfigVariables> specification = Specification.unrestricted();
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                buildRangeSpecification(criteria.getId(), ConfigVariables_.id),
                buildRangeSpecification(criteria.getConfigVarLong1(), ConfigVariables_.configVarLong1),
                buildRangeSpecification(criteria.getConfigVarLong2(), ConfigVariables_.configVarLong2),
                buildRangeSpecification(criteria.getConfigVarLong3(), ConfigVariables_.configVarLong3),
                buildRangeSpecification(criteria.getConfigVarLong4(), ConfigVariables_.configVarLong4),
                buildRangeSpecification(criteria.getConfigVarLong5(), ConfigVariables_.configVarLong5),
                buildRangeSpecification(criteria.getConfigVarLong6(), ConfigVariables_.configVarLong6),
                buildRangeSpecification(criteria.getConfigVarLong7(), ConfigVariables_.configVarLong7),
                buildRangeSpecification(criteria.getConfigVarLong8(), ConfigVariables_.configVarLong8),
                buildRangeSpecification(criteria.getConfigVarLong9(), ConfigVariables_.configVarLong9),
                buildRangeSpecification(criteria.getConfigVarLong10(), ConfigVariables_.configVarLong10),
                buildRangeSpecification(criteria.getConfigVarLong11(), ConfigVariables_.configVarLong11),
                buildRangeSpecification(criteria.getConfigVarLong12(), ConfigVariables_.configVarLong12),
                buildRangeSpecification(criteria.getConfigVarLong13(), ConfigVariables_.configVarLong13),
                buildRangeSpecification(criteria.getConfigVarLong14(), ConfigVariables_.configVarLong14),
                buildRangeSpecification(criteria.getConfigVarLong15(), ConfigVariables_.configVarLong15),
                buildSpecification(criteria.getConfigVarBoolean16(), ConfigVariables_.configVarBoolean16),
                buildSpecification(criteria.getConfigVarBoolean17(), ConfigVariables_.configVarBoolean17),
                buildSpecification(criteria.getConfigVarBoolean18(), ConfigVariables_.configVarBoolean18),
                buildStringSpecification(criteria.getConfigVarString19(), ConfigVariables_.configVarString19),
                buildStringSpecification(criteria.getConfigVarString20(), ConfigVariables_.configVarString20)
            );
        }
        return specification;
    }
}
