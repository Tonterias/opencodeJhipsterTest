package com.opencode.test.web.rest;

import com.opencode.test.repository.CactivityRepository;
import com.opencode.test.service.CactivityQueryService;
import com.opencode.test.service.CactivityService;
import com.opencode.test.service.criteria.CactivityCriteria;
import com.opencode.test.service.dto.CactivityDTO;
import com.opencode.test.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.opencode.test.domain.Cactivity}.
 */
@RestController
@RequestMapping("/api/cactivities")
public class CactivityResource {

    private static final Logger LOG = LoggerFactory.getLogger(CactivityResource.class);

    private static final String ENTITY_NAME = "cactivity";

    @Value("${jhipster.clientApp.name:opencodetest}")
    private String applicationName;

    private final CactivityService cactivityService;

    private final CactivityRepository cactivityRepository;

    private final CactivityQueryService cactivityQueryService;

    public CactivityResource(
        CactivityService cactivityService,
        CactivityRepository cactivityRepository,
        CactivityQueryService cactivityQueryService
    ) {
        this.cactivityService = cactivityService;
        this.cactivityRepository = cactivityRepository;
        this.cactivityQueryService = cactivityQueryService;
    }

    /**
     * {@code POST  /cactivities} : Create a new cactivity.
     *
     * @param cactivityDTO the cactivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cactivityDTO, or with status {@code 400 (Bad Request)} if the cactivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CactivityDTO> createCactivity(@Valid @RequestBody CactivityDTO cactivityDTO) throws URISyntaxException {
        LOG.debug("REST request to save Cactivity : {}", cactivityDTO);
        if (cactivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new cactivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cactivityDTO = cactivityService.save(cactivityDTO);
        return ResponseEntity.created(new URI("/api/cactivities/" + cactivityDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cactivityDTO.getId().toString()))
            .body(cactivityDTO);
    }

    /**
     * {@code PUT  /cactivities/:id} : Updates an existing cactivity.
     *
     * @param id the id of the cactivityDTO to save.
     * @param cactivityDTO the cactivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cactivityDTO,
     * or with status {@code 400 (Bad Request)} if the cactivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cactivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CactivityDTO> updateCactivity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CactivityDTO cactivityDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Cactivity : {}, {}", id, cactivityDTO);
        if (cactivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cactivityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cactivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cactivityDTO = cactivityService.update(cactivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cactivityDTO.getId().toString()))
            .body(cactivityDTO);
    }

    /**
     * {@code PATCH  /cactivities/:id} : Partial updates given fields of an existing cactivity, field will ignore if it is null
     *
     * @param id the id of the cactivityDTO to save.
     * @param cactivityDTO the cactivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cactivityDTO,
     * or with status {@code 400 (Bad Request)} if the cactivityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cactivityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cactivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CactivityDTO> partialUpdateCactivity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CactivityDTO cactivityDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Cactivity partially : {}, {}", id, cactivityDTO);
        if (cactivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cactivityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cactivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CactivityDTO> result = cactivityService.partialUpdate(cactivityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cactivityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cactivities} : get all the Cactivities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Cactivities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CactivityDTO>> getAllCactivities(
        CactivityCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Cactivities by criteria: {}", criteria);

        Page<CactivityDTO> page = cactivityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cactivities/count} : count all the cactivities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCactivities(CactivityCriteria criteria) {
        LOG.debug("REST request to count Cactivities by criteria: {}", criteria);
        return ResponseEntity.ok().body(cactivityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cactivities/:id} : get the "id" cactivity.
     *
     * @param id the id of the cactivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cactivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CactivityDTO> getCactivity(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Cactivity : {}", id);
        Optional<CactivityDTO> cactivityDTO = cactivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cactivityDTO);
    }

    /**
     * {@code DELETE  /cactivities/:id} : delete the "id" cactivity.
     *
     * @param id the id of the cactivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCactivity(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Cactivity : {}", id);
        cactivityService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
