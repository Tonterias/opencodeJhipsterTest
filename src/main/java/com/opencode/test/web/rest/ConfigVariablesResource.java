package com.opencode.test.web.rest;

import com.opencode.test.repository.ConfigVariablesRepository;
import com.opencode.test.service.ConfigVariablesQueryService;
import com.opencode.test.service.ConfigVariablesService;
import com.opencode.test.service.criteria.ConfigVariablesCriteria;
import com.opencode.test.service.dto.ConfigVariablesDTO;
import com.opencode.test.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.opencode.test.domain.ConfigVariables}.
 */
@RestController
@RequestMapping("/api/config-variables")
public class ConfigVariablesResource {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigVariablesResource.class);

    private static final String ENTITY_NAME = "configVariables";

    @Value("${jhipster.clientApp.name:opencodetest}")
    private String applicationName;

    private final ConfigVariablesService configVariablesService;

    private final ConfigVariablesRepository configVariablesRepository;

    private final ConfigVariablesQueryService configVariablesQueryService;

    public ConfigVariablesResource(
        ConfigVariablesService configVariablesService,
        ConfigVariablesRepository configVariablesRepository,
        ConfigVariablesQueryService configVariablesQueryService
    ) {
        this.configVariablesService = configVariablesService;
        this.configVariablesRepository = configVariablesRepository;
        this.configVariablesQueryService = configVariablesQueryService;
    }

    /**
     * {@code POST  /config-variables} : Create a new configVariables.
     *
     * @param configVariablesDTO the configVariablesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configVariablesDTO, or with status {@code 400 (Bad Request)} if the configVariables has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConfigVariablesDTO> createConfigVariables(@RequestBody ConfigVariablesDTO configVariablesDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ConfigVariables : {}", configVariablesDTO);
        if (configVariablesDTO.getId() != null) {
            throw new BadRequestAlertException("A new configVariables cannot already have an ID", ENTITY_NAME, "idexists");
        }
        configVariablesDTO = configVariablesService.save(configVariablesDTO);
        return ResponseEntity.created(new URI("/api/config-variables/" + configVariablesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, configVariablesDTO.getId().toString()))
            .body(configVariablesDTO);
    }

    /**
     * {@code PUT  /config-variables/:id} : Updates an existing configVariables.
     *
     * @param id the id of the configVariablesDTO to save.
     * @param configVariablesDTO the configVariablesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configVariablesDTO,
     * or with status {@code 400 (Bad Request)} if the configVariablesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configVariablesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConfigVariablesDTO> updateConfigVariables(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfigVariablesDTO configVariablesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ConfigVariables : {}, {}", id, configVariablesDTO);
        if (configVariablesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configVariablesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configVariablesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        configVariablesDTO = configVariablesService.update(configVariablesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, configVariablesDTO.getId().toString()))
            .body(configVariablesDTO);
    }

    /**
     * {@code PATCH  /config-variables/:id} : Partial updates given fields of an existing configVariables, field will ignore if it is null
     *
     * @param id the id of the configVariablesDTO to save.
     * @param configVariablesDTO the configVariablesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configVariablesDTO,
     * or with status {@code 400 (Bad Request)} if the configVariablesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the configVariablesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the configVariablesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConfigVariablesDTO> partialUpdateConfigVariables(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConfigVariablesDTO configVariablesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ConfigVariables partially : {}, {}", id, configVariablesDTO);
        if (configVariablesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, configVariablesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!configVariablesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConfigVariablesDTO> result = configVariablesService.partialUpdate(configVariablesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, configVariablesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /config-variables} : get all the Config Variables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Config Variables in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ConfigVariablesDTO>> getAllConfigVariableses(
        ConfigVariablesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get ConfigVariableses by criteria: {}", criteria);

        Page<ConfigVariablesDTO> page = configVariablesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /config-variables/count} : count all the configVariableses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countConfigVariableses(ConfigVariablesCriteria criteria) {
        LOG.debug("REST request to count ConfigVariableses by criteria: {}", criteria);
        return ResponseEntity.ok().body(configVariablesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /config-variables/:id} : get the "id" configVariables.
     *
     * @param id the id of the configVariablesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configVariablesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConfigVariablesDTO> getConfigVariables(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ConfigVariables : {}", id);
        Optional<ConfigVariablesDTO> configVariablesDTO = configVariablesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configVariablesDTO);
    }

    /**
     * {@code DELETE  /config-variables/:id} : delete the "id" configVariables.
     *
     * @param id the id of the configVariablesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfigVariables(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ConfigVariables : {}", id);
        configVariablesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
