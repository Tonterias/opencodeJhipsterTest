package com.opencode.test.service;

import com.opencode.test.service.dto.BlockuserDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.opencode.test.domain.Blockuser}.
 */
public interface BlockuserService {
    /**
     * Save a blockuser.
     *
     * @param blockuserDTO the entity to save.
     * @return the persisted entity.
     */
    BlockuserDTO save(BlockuserDTO blockuserDTO);

    /**
     * Updates a blockuser.
     *
     * @param blockuserDTO the entity to update.
     * @return the persisted entity.
     */
    BlockuserDTO update(BlockuserDTO blockuserDTO);

    /**
     * Partially updates a blockuser.
     *
     * @param blockuserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BlockuserDTO> partialUpdate(BlockuserDTO blockuserDTO);

    /**
     * Get the "id" blockuser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlockuserDTO> findOne(Long id);

    /**
     * Delete the "id" blockuser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
