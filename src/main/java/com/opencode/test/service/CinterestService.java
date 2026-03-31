package com.opencode.test.service;

import com.opencode.test.service.dto.CinterestDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.opencode.test.domain.Cinterest}.
 */
public interface CinterestService {
    /**
     * Save a cinterest.
     *
     * @param cinterestDTO the entity to save.
     * @return the persisted entity.
     */
    CinterestDTO save(CinterestDTO cinterestDTO);

    /**
     * Updates a cinterest.
     *
     * @param cinterestDTO the entity to update.
     * @return the persisted entity.
     */
    CinterestDTO update(CinterestDTO cinterestDTO);

    /**
     * Partially updates a cinterest.
     *
     * @param cinterestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CinterestDTO> partialUpdate(CinterestDTO cinterestDTO);

    /**
     * Get all the cinterests with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CinterestDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" cinterest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CinterestDTO> findOne(Long id);

    /**
     * Delete the "id" cinterest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
