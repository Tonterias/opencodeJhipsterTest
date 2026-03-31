package com.opencode.test.web.rest;

import com.opencode.test.repository.AppuserRepository;
import com.opencode.test.service.AppuserQueryService;
import com.opencode.test.service.AppuserService;
import com.opencode.test.service.criteria.AppuserCriteria;
import com.opencode.test.service.dto.AppuserDTO;
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
 * REST controller for managing {@link com.opencode.test.domain.Appuser}.
 */
@RestController
@RequestMapping("/api/appusers")
public class AppuserResource {

    private static final Logger LOG = LoggerFactory.getLogger(AppuserResource.class);

    private static final String ENTITY_NAME = "appuser";

    @Value("${jhipster.clientApp.name:opencodetest}")
    private String applicationName;

    private final AppuserService appuserService;

    private final AppuserRepository appuserRepository;

    private final AppuserQueryService appuserQueryService;

    public AppuserResource(AppuserService appuserService, AppuserRepository appuserRepository, AppuserQueryService appuserQueryService) {
        this.appuserService = appuserService;
        this.appuserRepository = appuserRepository;
        this.appuserQueryService = appuserQueryService;
    }

    /**
     * {@code POST  /appusers} : Create a new appuser.
     *
     * @param appuserDTO the appuserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appuserDTO, or with status {@code 400 (Bad Request)} if the appuser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppuserDTO> createAppuser(@Valid @RequestBody AppuserDTO appuserDTO) throws URISyntaxException {
        LOG.debug("REST request to save Appuser : {}", appuserDTO);
        if (appuserDTO.getId() != null) {
            throw new BadRequestAlertException("A new appuser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appuserDTO = appuserService.save(appuserDTO);
        return ResponseEntity.created(new URI("/api/appusers/" + appuserDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, appuserDTO.getId().toString()))
            .body(appuserDTO);
    }

    /**
     * {@code PUT  /appusers/:id} : Updates an existing appuser.
     *
     * @param id the id of the appuserDTO to save.
     * @param appuserDTO the appuserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appuserDTO,
     * or with status {@code 400 (Bad Request)} if the appuserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appuserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppuserDTO> updateAppuser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AppuserDTO appuserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Appuser : {}, {}", id, appuserDTO);
        if (appuserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appuserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appuserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appuserDTO = appuserService.update(appuserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appuserDTO.getId().toString()))
            .body(appuserDTO);
    }

    /**
     * {@code PATCH  /appusers/:id} : Partial updates given fields of an existing appuser, field will ignore if it is null
     *
     * @param id the id of the appuserDTO to save.
     * @param appuserDTO the appuserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appuserDTO,
     * or with status {@code 400 (Bad Request)} if the appuserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appuserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appuserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppuserDTO> partialUpdateAppuser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AppuserDTO appuserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Appuser partially : {}, {}", id, appuserDTO);
        if (appuserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appuserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appuserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppuserDTO> result = appuserService.partialUpdate(appuserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appuserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /appusers} : get all the Appusers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Appusers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AppuserDTO>> getAllAppusers(
        AppuserCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Appusers by criteria: {}", criteria);

        Page<AppuserDTO> page = appuserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appusers/count} : count all the appusers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAppusers(AppuserCriteria criteria) {
        LOG.debug("REST request to count Appusers by criteria: {}", criteria);
        return ResponseEntity.ok().body(appuserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appusers/:id} : get the "id" appuser.
     *
     * @param id the id of the appuserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appuserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppuserDTO> getAppuser(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Appuser : {}", id);
        Optional<AppuserDTO> appuserDTO = appuserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appuserDTO);
    }

    /**
     * {@code DELETE  /appusers/:id} : delete the "id" appuser.
     *
     * @param id the id of the appuserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppuser(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Appuser : {}", id);
        appuserService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
