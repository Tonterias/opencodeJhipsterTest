package com.opencode.test.service;

import com.opencode.test.service.dto.ConfigVariablesDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.opencode.test.domain.ConfigVariables}.
 */
public interface ConfigVariablesService {
    /**
     * Save a configVariables.
     *
     * @param configVariablesDTO the entity to save.
     * @return the persisted entity.
     */
    ConfigVariablesDTO save(ConfigVariablesDTO configVariablesDTO);

    /**
     * Updates a configVariables.
     *
     * @param configVariablesDTO the entity to update.
     * @return the persisted entity.
     */
    ConfigVariablesDTO update(ConfigVariablesDTO configVariablesDTO);

    /**
     * Partially updates a configVariables.
     *
     * @param configVariablesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConfigVariablesDTO> partialUpdate(ConfigVariablesDTO configVariablesDTO);

    /**
     * Get the "id" configVariables.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConfigVariablesDTO> findOne(Long id);

    /**
     * Delete the "id" configVariables.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
