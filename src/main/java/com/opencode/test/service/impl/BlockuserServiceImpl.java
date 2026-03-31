package com.opencode.test.service.impl;

import com.opencode.test.domain.Blockuser;
import com.opencode.test.repository.BlockuserRepository;
import com.opencode.test.service.BlockuserService;
import com.opencode.test.service.dto.BlockuserDTO;
import com.opencode.test.service.mapper.BlockuserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Blockuser}.
 */
@Service
@Transactional
public class BlockuserServiceImpl implements BlockuserService {

    private static final Logger LOG = LoggerFactory.getLogger(BlockuserServiceImpl.class);

    private final BlockuserRepository blockuserRepository;

    private final BlockuserMapper blockuserMapper;

    public BlockuserServiceImpl(BlockuserRepository blockuserRepository, BlockuserMapper blockuserMapper) {
        this.blockuserRepository = blockuserRepository;
        this.blockuserMapper = blockuserMapper;
    }

    @Override
    public BlockuserDTO save(BlockuserDTO blockuserDTO) {
        LOG.debug("Request to save Blockuser : {}", blockuserDTO);
        Blockuser blockuser = blockuserMapper.toEntity(blockuserDTO);
        blockuser = blockuserRepository.save(blockuser);
        return blockuserMapper.toDto(blockuser);
    }

    @Override
    public BlockuserDTO update(BlockuserDTO blockuserDTO) {
        LOG.debug("Request to update Blockuser : {}", blockuserDTO);
        Blockuser blockuser = blockuserMapper.toEntity(blockuserDTO);
        blockuser = blockuserRepository.save(blockuser);
        return blockuserMapper.toDto(blockuser);
    }

    @Override
    public Optional<BlockuserDTO> partialUpdate(BlockuserDTO blockuserDTO) {
        LOG.debug("Request to partially update Blockuser : {}", blockuserDTO);

        return blockuserRepository
            .findById(blockuserDTO.getId())
            .map(existingBlockuser -> {
                blockuserMapper.partialUpdate(existingBlockuser, blockuserDTO);

                return existingBlockuser;
            })
            .map(blockuserRepository::save)
            .map(blockuserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlockuserDTO> findOne(Long id) {
        LOG.debug("Request to get Blockuser : {}", id);
        return blockuserRepository.findById(id).map(blockuserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Blockuser : {}", id);
        blockuserRepository.deleteById(id);
    }
}
