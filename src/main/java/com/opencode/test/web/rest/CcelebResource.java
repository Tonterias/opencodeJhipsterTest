package com.opencode.test.web.rest;

import com.opencode.test.repository.CcelebRepository;
import com.opencode.test.service.CcelebQueryService;
import com.opencode.test.service.CcelebService;
import com.opencode.test.service.criteria.CcelebCriteria;
import com.opencode.test.service.dto.CcelebDTO;
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
 * REST controller for managing {@link com.opencode.test.domain.Cceleb}.
 */
@RestController
@RequestMapping("/api/ccelebs")
public class CcelebResource {

    private static final Logger LOG = LoggerFactory.getLogger(CcelebResource.class);

    private static final String ENTITY_NAME = "cceleb";

    @Value("${jhipster.clientApp.name:opencodetest}")
    private String applicationName;

    private final CcelebService ccelebService;

    private final CcelebRepository ccelebRepository;

    private final CcelebQueryService ccelebQueryService;

    public CcelebResource(CcelebService ccelebService, CcelebRepository ccelebRepository, CcelebQueryService ccelebQueryService) {
        this.ccelebService = ccelebService;
        this.ccelebRepository = ccelebRepository;
        this.ccelebQueryService = ccelebQueryService;
    }

    /**
     * {@code POST  /ccelebs} : Create a new cceleb.
     *
     * @param ccelebDTO the ccelebDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ccelebDTO, or with status {@code 400 (Bad Request)} if the cceleb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CcelebDTO> createCceleb(@Valid @RequestBody CcelebDTO ccelebDTO) throws URISyntaxException {
        LOG.debug("REST request to save Cceleb : {}", ccelebDTO);
        if (ccelebDTO.getId() != null) {
            throw new BadRequestAlertException("A new cceleb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ccelebDTO = ccelebService.save(ccelebDTO);
        return ResponseEntity.created(new URI("/api/ccelebs/" + ccelebDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ccelebDTO.getId().toString()))
            .body(ccelebDTO);
    }

    /**
     * {@code PUT  /ccelebs/:id} : Updates an existing cceleb.
     *
     * @param id the id of the ccelebDTO to save.
     * @param ccelebDTO the ccelebDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ccelebDTO,
     * or with status {@code 400 (Bad Request)} if the ccelebDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ccelebDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CcelebDTO> updateCceleb(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CcelebDTO ccelebDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Cceleb : {}, {}", id, ccelebDTO);
        if (ccelebDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ccelebDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ccelebRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ccelebDTO = ccelebService.update(ccelebDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ccelebDTO.getId().toString()))
            .body(ccelebDTO);
    }

    /**
     * {@code PATCH  /ccelebs/:id} : Partial updates given fields of an existing cceleb, field will ignore if it is null
     *
     * @param id the id of the ccelebDTO to save.
     * @param ccelebDTO the ccelebDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ccelebDTO,
     * or with status {@code 400 (Bad Request)} if the ccelebDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ccelebDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ccelebDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CcelebDTO> partialUpdateCceleb(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CcelebDTO ccelebDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Cceleb partially : {}, {}", id, ccelebDTO);
        if (ccelebDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ccelebDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ccelebRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CcelebDTO> result = ccelebService.partialUpdate(ccelebDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ccelebDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ccelebs} : get all the Ccelebs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Ccelebs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CcelebDTO>> getAllCcelebs(
        CcelebCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Ccelebs by criteria: {}", criteria);

        Page<CcelebDTO> page = ccelebQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ccelebs/count} : count all the ccelebs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCcelebs(CcelebCriteria criteria) {
        LOG.debug("REST request to count Ccelebs by criteria: {}", criteria);
        return ResponseEntity.ok().body(ccelebQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ccelebs/:id} : get the "id" cceleb.
     *
     * @param id the id of the ccelebDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ccelebDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CcelebDTO> getCceleb(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Cceleb : {}", id);
        Optional<CcelebDTO> ccelebDTO = ccelebService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ccelebDTO);
    }

    /**
     * {@code DELETE  /ccelebs/:id} : delete the "id" cceleb.
     *
     * @param id the id of the ccelebDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCceleb(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Cceleb : {}", id);
        ccelebService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
