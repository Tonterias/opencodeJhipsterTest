package com.opencode.test.web.rest;

import com.opencode.test.repository.CelebRepository;
import com.opencode.test.service.CelebQueryService;
import com.opencode.test.service.CelebService;
import com.opencode.test.service.criteria.CelebCriteria;
import com.opencode.test.service.dto.CelebDTO;
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
 * REST controller for managing {@link com.opencode.test.domain.Celeb}.
 */
@RestController
@RequestMapping("/api/celebs")
public class CelebResource {

    private static final Logger LOG = LoggerFactory.getLogger(CelebResource.class);

    private static final String ENTITY_NAME = "celeb";

    @Value("${jhipster.clientApp.name:opencodetest}")
    private String applicationName;

    private final CelebService celebService;

    private final CelebRepository celebRepository;

    private final CelebQueryService celebQueryService;

    public CelebResource(CelebService celebService, CelebRepository celebRepository, CelebQueryService celebQueryService) {
        this.celebService = celebService;
        this.celebRepository = celebRepository;
        this.celebQueryService = celebQueryService;
    }

    /**
     * {@code POST  /celebs} : Create a new celeb.
     *
     * @param celebDTO the celebDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new celebDTO, or with status {@code 400 (Bad Request)} if the celeb has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CelebDTO> createCeleb(@Valid @RequestBody CelebDTO celebDTO) throws URISyntaxException {
        LOG.debug("REST request to save Celeb : {}", celebDTO);
        if (celebDTO.getId() != null) {
            throw new BadRequestAlertException("A new celeb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        celebDTO = celebService.save(celebDTO);
        return ResponseEntity.created(new URI("/api/celebs/" + celebDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, celebDTO.getId().toString()))
            .body(celebDTO);
    }

    /**
     * {@code PUT  /celebs/:id} : Updates an existing celeb.
     *
     * @param id the id of the celebDTO to save.
     * @param celebDTO the celebDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated celebDTO,
     * or with status {@code 400 (Bad Request)} if the celebDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the celebDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CelebDTO> updateCeleb(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CelebDTO celebDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Celeb : {}, {}", id, celebDTO);
        if (celebDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, celebDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!celebRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        celebDTO = celebService.update(celebDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, celebDTO.getId().toString()))
            .body(celebDTO);
    }

    /**
     * {@code PATCH  /celebs/:id} : Partial updates given fields of an existing celeb, field will ignore if it is null
     *
     * @param id the id of the celebDTO to save.
     * @param celebDTO the celebDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated celebDTO,
     * or with status {@code 400 (Bad Request)} if the celebDTO is not valid,
     * or with status {@code 404 (Not Found)} if the celebDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the celebDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CelebDTO> partialUpdateCeleb(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CelebDTO celebDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Celeb partially : {}, {}", id, celebDTO);
        if (celebDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, celebDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!celebRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CelebDTO> result = celebService.partialUpdate(celebDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, celebDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /celebs} : get all the Celebs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Celebs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CelebDTO>> getAllCelebs(
        CelebCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Celebs by criteria: {}", criteria);

        Page<CelebDTO> page = celebQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /celebs/count} : count all the celebs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCelebs(CelebCriteria criteria) {
        LOG.debug("REST request to count Celebs by criteria: {}", criteria);
        return ResponseEntity.ok().body(celebQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /celebs/:id} : get the "id" celeb.
     *
     * @param id the id of the celebDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the celebDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CelebDTO> getCeleb(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Celeb : {}", id);
        Optional<CelebDTO> celebDTO = celebService.findOne(id);
        return ResponseUtil.wrapOrNotFound(celebDTO);
    }

    /**
     * {@code DELETE  /celebs/:id} : delete the "id" celeb.
     *
     * @param id the id of the celebDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCeleb(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Celeb : {}", id);
        celebService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
