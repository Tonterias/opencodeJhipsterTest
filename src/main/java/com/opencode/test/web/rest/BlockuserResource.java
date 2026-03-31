package com.opencode.test.web.rest;

import com.opencode.test.repository.BlockuserRepository;
import com.opencode.test.service.BlockuserQueryService;
import com.opencode.test.service.BlockuserService;
import com.opencode.test.service.criteria.BlockuserCriteria;
import com.opencode.test.service.dto.BlockuserDTO;
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
 * REST controller for managing {@link com.opencode.test.domain.Blockuser}.
 */
@RestController
@RequestMapping("/api/blockusers")
public class BlockuserResource {

    private static final Logger LOG = LoggerFactory.getLogger(BlockuserResource.class);

    private static final String ENTITY_NAME = "blockuser";

    @Value("${jhipster.clientApp.name:opencodetest}")
    private String applicationName;

    private final BlockuserService blockuserService;

    private final BlockuserRepository blockuserRepository;

    private final BlockuserQueryService blockuserQueryService;

    public BlockuserResource(
        BlockuserService blockuserService,
        BlockuserRepository blockuserRepository,
        BlockuserQueryService blockuserQueryService
    ) {
        this.blockuserService = blockuserService;
        this.blockuserRepository = blockuserRepository;
        this.blockuserQueryService = blockuserQueryService;
    }

    /**
     * {@code POST  /blockusers} : Create a new blockuser.
     *
     * @param blockuserDTO the blockuserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blockuserDTO, or with status {@code 400 (Bad Request)} if the blockuser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BlockuserDTO> createBlockuser(@RequestBody BlockuserDTO blockuserDTO) throws URISyntaxException {
        LOG.debug("REST request to save Blockuser : {}", blockuserDTO);
        if (blockuserDTO.getId() != null) {
            throw new BadRequestAlertException("A new blockuser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        blockuserDTO = blockuserService.save(blockuserDTO);
        return ResponseEntity.created(new URI("/api/blockusers/" + blockuserDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, blockuserDTO.getId().toString()))
            .body(blockuserDTO);
    }

    /**
     * {@code PUT  /blockusers/:id} : Updates an existing blockuser.
     *
     * @param id the id of the blockuserDTO to save.
     * @param blockuserDTO the blockuserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blockuserDTO,
     * or with status {@code 400 (Bad Request)} if the blockuserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blockuserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BlockuserDTO> updateBlockuser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BlockuserDTO blockuserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Blockuser : {}, {}", id, blockuserDTO);
        if (blockuserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blockuserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blockuserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        blockuserDTO = blockuserService.update(blockuserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blockuserDTO.getId().toString()))
            .body(blockuserDTO);
    }

    /**
     * {@code PATCH  /blockusers/:id} : Partial updates given fields of an existing blockuser, field will ignore if it is null
     *
     * @param id the id of the blockuserDTO to save.
     * @param blockuserDTO the blockuserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blockuserDTO,
     * or with status {@code 400 (Bad Request)} if the blockuserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the blockuserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the blockuserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BlockuserDTO> partialUpdateBlockuser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BlockuserDTO blockuserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Blockuser partially : {}, {}", id, blockuserDTO);
        if (blockuserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blockuserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blockuserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlockuserDTO> result = blockuserService.partialUpdate(blockuserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blockuserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /blockusers} : get all the Blockusers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Blockusers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BlockuserDTO>> getAllBlockusers(
        BlockuserCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Blockusers by criteria: {}", criteria);

        Page<BlockuserDTO> page = blockuserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /blockusers/count} : count all the blockusers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBlockusers(BlockuserCriteria criteria) {
        LOG.debug("REST request to count Blockusers by criteria: {}", criteria);
        return ResponseEntity.ok().body(blockuserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /blockusers/:id} : get the "id" blockuser.
     *
     * @param id the id of the blockuserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blockuserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BlockuserDTO> getBlockuser(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Blockuser : {}", id);
        Optional<BlockuserDTO> blockuserDTO = blockuserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blockuserDTO);
    }

    /**
     * {@code DELETE  /blockusers/:id} : delete the "id" blockuser.
     *
     * @param id the id of the blockuserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlockuser(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Blockuser : {}", id);
        blockuserService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
