package com.opencode.test.service;

import com.opencode.test.service.dto.UrllinkDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.opencode.test.domain.Urllink}.
 */
public interface UrllinkService {
    /**
     * Save a urllink.
     *
     * @param urllinkDTO the entity to save.
     * @return the persisted entity.
     */
    UrllinkDTO save(UrllinkDTO urllinkDTO);

    /**
     * Updates a urllink.
     *
     * @param urllinkDTO the entity to update.
     * @return the persisted entity.
     */
    UrllinkDTO update(UrllinkDTO urllinkDTO);

    /**
     * Partially updates a urllink.
     *
     * @param urllinkDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UrllinkDTO> partialUpdate(UrllinkDTO urllinkDTO);

    /**
     * Get the "id" urllink.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UrllinkDTO> findOne(Long id);

    /**
     * Delete the "id" urllink.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
