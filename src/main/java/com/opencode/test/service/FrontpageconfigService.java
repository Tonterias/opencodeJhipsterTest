package com.opencode.test.service;

import com.opencode.test.service.dto.FrontpageconfigDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.opencode.test.domain.Frontpageconfig}.
 */
public interface FrontpageconfigService {
    /**
     * Save a frontpageconfig.
     *
     * @param frontpageconfigDTO the entity to save.
     * @return the persisted entity.
     */
    FrontpageconfigDTO save(FrontpageconfigDTO frontpageconfigDTO);

    /**
     * Updates a frontpageconfig.
     *
     * @param frontpageconfigDTO the entity to update.
     * @return the persisted entity.
     */
    FrontpageconfigDTO update(FrontpageconfigDTO frontpageconfigDTO);

    /**
     * Partially updates a frontpageconfig.
     *
     * @param frontpageconfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FrontpageconfigDTO> partialUpdate(FrontpageconfigDTO frontpageconfigDTO);

    /**
     * Get the "id" frontpageconfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FrontpageconfigDTO> findOne(Long id);

    /**
     * Delete the "id" frontpageconfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
