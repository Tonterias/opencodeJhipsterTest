package com.opencode.test.service;

import com.opencode.test.service.dto.CommunityDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.opencode.test.domain.Community}.
 */
public interface CommunityService {
    /**
     * Save a community.
     *
     * @param communityDTO the entity to save.
     * @return the persisted entity.
     */
    CommunityDTO save(CommunityDTO communityDTO);

    /**
     * Updates a community.
     *
     * @param communityDTO the entity to update.
     * @return the persisted entity.
     */
    CommunityDTO update(CommunityDTO communityDTO);

    /**
     * Partially updates a community.
     *
     * @param communityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommunityDTO> partialUpdate(CommunityDTO communityDTO);

    /**
     * Get the "id" community.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommunityDTO> findOne(Long id);

    /**
     * Delete the "id" community.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
