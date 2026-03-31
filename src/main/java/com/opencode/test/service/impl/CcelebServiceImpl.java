package com.opencode.test.service.impl;

import com.opencode.test.domain.Cceleb;
import com.opencode.test.repository.CcelebRepository;
import com.opencode.test.service.CcelebService;
import com.opencode.test.service.dto.CcelebDTO;
import com.opencode.test.service.mapper.CcelebMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Cceleb}.
 */
@Service
@Transactional
public class CcelebServiceImpl implements CcelebService {

    private static final Logger LOG = LoggerFactory.getLogger(CcelebServiceImpl.class);

    private final CcelebRepository ccelebRepository;

    private final CcelebMapper ccelebMapper;

    public CcelebServiceImpl(CcelebRepository ccelebRepository, CcelebMapper ccelebMapper) {
        this.ccelebRepository = ccelebRepository;
        this.ccelebMapper = ccelebMapper;
    }

    @Override
    public CcelebDTO save(CcelebDTO ccelebDTO) {
        LOG.debug("Request to save Cceleb : {}", ccelebDTO);
        Cceleb cceleb = ccelebMapper.toEntity(ccelebDTO);
        cceleb = ccelebRepository.save(cceleb);
        return ccelebMapper.toDto(cceleb);
    }

    @Override
    public CcelebDTO update(CcelebDTO ccelebDTO) {
        LOG.debug("Request to update Cceleb : {}", ccelebDTO);
        Cceleb cceleb = ccelebMapper.toEntity(ccelebDTO);
        cceleb = ccelebRepository.save(cceleb);
        return ccelebMapper.toDto(cceleb);
    }

    @Override
    public Optional<CcelebDTO> partialUpdate(CcelebDTO ccelebDTO) {
        LOG.debug("Request to partially update Cceleb : {}", ccelebDTO);

        return ccelebRepository
            .findById(ccelebDTO.getId())
            .map(existingCceleb -> {
                ccelebMapper.partialUpdate(existingCceleb, ccelebDTO);

                return existingCceleb;
            })
            .map(ccelebRepository::save)
            .map(ccelebMapper::toDto);
    }

    public Page<CcelebDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ccelebRepository.findAllWithEagerRelationships(pageable).map(ccelebMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CcelebDTO> findOne(Long id) {
        LOG.debug("Request to get Cceleb : {}", id);
        return ccelebRepository.findOneWithEagerRelationships(id).map(ccelebMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Cceleb : {}", id);
        ccelebRepository.deleteById(id);
    }
}
