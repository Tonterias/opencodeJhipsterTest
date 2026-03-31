package com.opencode.test.web.rest;

import com.opencode.test.repository.AppphotoRepository;
import com.opencode.test.service.AppphotoQueryService;
import com.opencode.test.service.AppphotoService;
import com.opencode.test.service.criteria.AppphotoCriteria;
import com.opencode.test.service.dto.AppphotoDTO;
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
 * REST controller for managing {@link com.opencode.test.domain.Appphoto}.
 */
@RestController
@RequestMapping("/api/appphotos")
public class AppphotoResource {

    private static final Logger LOG = LoggerFactory.getLogger(AppphotoResource.class);

    private static final String ENTITY_NAME = "appphoto";

    @Value("${jhipster.clientApp.name:opencodetest}")
    private String applicationName;

    private final AppphotoService appphotoService;

    private final AppphotoRepository appphotoRepository;

    private final AppphotoQueryService appphotoQueryService;

    public AppphotoResource(
        AppphotoService appphotoService,
        AppphotoRepository appphotoRepository,
        AppphotoQueryService appphotoQueryService
    ) {
        this.appphotoService = appphotoService;
        this.appphotoRepository = appphotoRepository;
        this.appphotoQueryService = appphotoQueryService;
    }

    /**
     * {@code POST  /appphotos} : Create a new appphoto.
     *
     * @param appphotoDTO the appphotoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appphotoDTO, or with status {@code 400 (Bad Request)} if the appphoto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppphotoDTO> createAppphoto(@Valid @RequestBody AppphotoDTO appphotoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Appphoto : {}", appphotoDTO);
        if (appphotoDTO.getId() != null) {
            throw new BadRequestAlertException("A new appphoto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        appphotoDTO = appphotoService.save(appphotoDTO);
        return ResponseEntity.created(new URI("/api/appphotos/" + appphotoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, appphotoDTO.getId().toString()))
            .body(appphotoDTO);
    }

    /**
     * {@code PUT  /appphotos/:id} : Updates an existing appphoto.
     *
     * @param id the id of the appphotoDTO to save.
     * @param appphotoDTO the appphotoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appphotoDTO,
     * or with status {@code 400 (Bad Request)} if the appphotoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appphotoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppphotoDTO> updateAppphoto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AppphotoDTO appphotoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Appphoto : {}, {}", id, appphotoDTO);
        if (appphotoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appphotoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appphotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        appphotoDTO = appphotoService.update(appphotoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appphotoDTO.getId().toString()))
            .body(appphotoDTO);
    }

    /**
     * {@code PATCH  /appphotos/:id} : Partial updates given fields of an existing appphoto, field will ignore if it is null
     *
     * @param id the id of the appphotoDTO to save.
     * @param appphotoDTO the appphotoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appphotoDTO,
     * or with status {@code 400 (Bad Request)} if the appphotoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appphotoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appphotoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppphotoDTO> partialUpdateAppphoto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AppphotoDTO appphotoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Appphoto partially : {}, {}", id, appphotoDTO);
        if (appphotoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appphotoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appphotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppphotoDTO> result = appphotoService.partialUpdate(appphotoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appphotoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /appphotos} : get all the Appphotos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Appphotos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AppphotoDTO>> getAllAppphotos(
        AppphotoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Appphotos by criteria: {}", criteria);

        Page<AppphotoDTO> page = appphotoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appphotos/count} : count all the appphotos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAppphotos(AppphotoCriteria criteria) {
        LOG.debug("REST request to count Appphotos by criteria: {}", criteria);
        return ResponseEntity.ok().body(appphotoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appphotos/:id} : get the "id" appphoto.
     *
     * @param id the id of the appphotoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appphotoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppphotoDTO> getAppphoto(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Appphoto : {}", id);
        Optional<AppphotoDTO> appphotoDTO = appphotoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appphotoDTO);
    }

    /**
     * {@code DELETE  /appphotos/:id} : delete the "id" appphoto.
     *
     * @param id the id of the appphotoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppphoto(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Appphoto : {}", id);
        appphotoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
