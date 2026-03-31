package com.opencode.test.service.impl;

import com.opencode.test.domain.Celeb;
import com.opencode.test.repository.CelebRepository;
import com.opencode.test.service.CelebService;
import com.opencode.test.service.dto.CelebDTO;
import com.opencode.test.service.mapper.CelebMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Celeb}.
 */
@Service
@Transactional
public class CelebServiceImpl implements CelebService {

    private static final Logger LOG = LoggerFactory.getLogger(CelebServiceImpl.class);

    private final CelebRepository celebRepository;

    private final CelebMapper celebMapper;

    public CelebServiceImpl(CelebRepository celebRepository, CelebMapper celebMapper) {
        this.celebRepository = celebRepository;
        this.celebMapper = celebMapper;
    }

    @Override
    public CelebDTO save(CelebDTO celebDTO) {
        LOG.debug("Request to save Celeb : {}", celebDTO);
        Celeb celeb = celebMapper.toEntity(celebDTO);
        celeb = celebRepository.save(celeb);
        return celebMapper.toDto(celeb);
    }

    @Override
    public CelebDTO update(CelebDTO celebDTO) {
        LOG.debug("Request to update Celeb : {}", celebDTO);
        Celeb celeb = celebMapper.toEntity(celebDTO);
        celeb = celebRepository.save(celeb);
        return celebMapper.toDto(celeb);
    }

    @Override
    public Optional<CelebDTO> partialUpdate(CelebDTO celebDTO) {
        LOG.debug("Request to partially update Celeb : {}", celebDTO);

        return celebRepository
            .findById(celebDTO.getId())
            .map(existingCeleb -> {
                celebMapper.partialUpdate(existingCeleb, celebDTO);

                return existingCeleb;
            })
            .map(celebRepository::save)
            .map(celebMapper::toDto);
    }

    public Page<CelebDTO> findAllWithEagerRelationships(Pageable pageable) {
        return celebRepository.findAllWithEagerRelationships(pageable).map(celebMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CelebDTO> findOne(Long id) {
        LOG.debug("Request to get Celeb : {}", id);
        return celebRepository.findOneWithEagerRelationships(id).map(celebMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Celeb : {}", id);
        celebRepository.deleteById(id);
    }
}
