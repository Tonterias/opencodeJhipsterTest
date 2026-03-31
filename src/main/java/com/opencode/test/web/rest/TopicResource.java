package com.opencode.test.web.rest;

import com.opencode.test.repository.TopicRepository;
import com.opencode.test.service.TopicQueryService;
import com.opencode.test.service.TopicService;
import com.opencode.test.service.criteria.TopicCriteria;
import com.opencode.test.service.dto.TopicDTO;
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
 * REST controller for managing {@link com.opencode.test.domain.Topic}.
 */
@RestController
@RequestMapping("/api/topics")
public class TopicResource {

    private static final Logger LOG = LoggerFactory.getLogger(TopicResource.class);

    private static final String ENTITY_NAME = "topic";

    @Value("${jhipster.clientApp.name:opencodetest}")
    private String applicationName;

    private final TopicService topicService;

    private final TopicRepository topicRepository;

    private final TopicQueryService topicQueryService;

    public TopicResource(TopicService topicService, TopicRepository topicRepository, TopicQueryService topicQueryService) {
        this.topicService = topicService;
        this.topicRepository = topicRepository;
        this.topicQueryService = topicQueryService;
    }

    /**
     * {@code POST  /topics} : Create a new topic.
     *
     * @param topicDTO the topicDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicDTO, or with status {@code 400 (Bad Request)} if the topic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TopicDTO> createTopic(@Valid @RequestBody TopicDTO topicDTO) throws URISyntaxException {
        LOG.debug("REST request to save Topic : {}", topicDTO);
        if (topicDTO.getId() != null) {
            throw new BadRequestAlertException("A new topic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        topicDTO = topicService.save(topicDTO);
        return ResponseEntity.created(new URI("/api/topics/" + topicDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, topicDTO.getId().toString()))
            .body(topicDTO);
    }

    /**
     * {@code PUT  /topics/:id} : Updates an existing topic.
     *
     * @param id the id of the topicDTO to save.
     * @param topicDTO the topicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicDTO,
     * or with status {@code 400 (Bad Request)} if the topicDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TopicDTO> updateTopic(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TopicDTO topicDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Topic : {}, {}", id, topicDTO);
        if (topicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, topicDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!topicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        topicDTO = topicService.update(topicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topicDTO.getId().toString()))
            .body(topicDTO);
    }

    /**
     * {@code PATCH  /topics/:id} : Partial updates given fields of an existing topic, field will ignore if it is null
     *
     * @param id the id of the topicDTO to save.
     * @param topicDTO the topicDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicDTO,
     * or with status {@code 400 (Bad Request)} if the topicDTO is not valid,
     * or with status {@code 404 (Not Found)} if the topicDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the topicDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TopicDTO> partialUpdateTopic(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TopicDTO topicDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Topic partially : {}, {}", id, topicDTO);
        if (topicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, topicDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!topicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TopicDTO> result = topicService.partialUpdate(topicDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topicDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /topics} : get all the Topics.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Topics in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TopicDTO>> getAllTopics(
        TopicCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Topics by criteria: {}", criteria);

        Page<TopicDTO> page = topicQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topics/count} : count all the topics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTopics(TopicCriteria criteria) {
        LOG.debug("REST request to count Topics by criteria: {}", criteria);
        return ResponseEntity.ok().body(topicQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /topics/:id} : get the "id" topic.
     *
     * @param id the id of the topicDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getTopic(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Topic : {}", id);
        Optional<TopicDTO> topicDTO = topicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicDTO);
    }

    /**
     * {@code DELETE  /topics/:id} : delete the "id" topic.
     *
     * @param id the id of the topicDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Topic : {}", id);
        topicService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
