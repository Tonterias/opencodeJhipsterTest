package com.opencode.test.service;

import com.opencode.test.service.dto.AppphotoDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.opencode.test.domain.Appphoto}.
 */
public interface AppphotoService {
    /**
     * Save a appphoto.
     *
     * @param appphotoDTO the entity to save.
     * @return the persisted entity.
     */
    AppphotoDTO save(AppphotoDTO appphotoDTO);

    /**
     * Updates a appphoto.
     *
     * @param appphotoDTO the entity to update.
     * @return the persisted entity.
     */
    AppphotoDTO update(AppphotoDTO appphotoDTO);

    /**
     * Partially updates a appphoto.
     *
     * @param appphotoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppphotoDTO> partialUpdate(AppphotoDTO appphotoDTO);

    /**
     * Get the "id" appphoto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppphotoDTO> findOne(Long id);

    /**
     * Delete the "id" appphoto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
