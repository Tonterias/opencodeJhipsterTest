package com.opencode.test.service.impl;

import com.opencode.test.domain.Frontpageconfig;
import com.opencode.test.repository.FrontpageconfigRepository;
import com.opencode.test.service.FrontpageconfigService;
import com.opencode.test.service.dto.FrontpageconfigDTO;
import com.opencode.test.service.mapper.FrontpageconfigMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Frontpageconfig}.
 */
@Service
@Transactional
public class FrontpageconfigServiceImpl implements FrontpageconfigService {

    private static final Logger LOG = LoggerFactory.getLogger(FrontpageconfigServiceImpl.class);

    private final FrontpageconfigRepository frontpageconfigRepository;

    private final FrontpageconfigMapper frontpageconfigMapper;

    public FrontpageconfigServiceImpl(FrontpageconfigRepository frontpageconfigRepository, FrontpageconfigMapper frontpageconfigMapper) {
        this.frontpageconfigRepository = frontpageconfigRepository;
        this.frontpageconfigMapper = frontpageconfigMapper;
    }

    @Override
    public FrontpageconfigDTO save(FrontpageconfigDTO frontpageconfigDTO) {
        LOG.debug("Request to save Frontpageconfig : {}", frontpageconfigDTO);
        Frontpageconfig frontpageconfig = frontpageconfigMapper.toEntity(frontpageconfigDTO);
        frontpageconfig = frontpageconfigRepository.save(frontpageconfig);
        return frontpageconfigMapper.toDto(frontpageconfig);
    }

    @Override
    public FrontpageconfigDTO update(FrontpageconfigDTO frontpageconfigDTO) {
        LOG.debug("Request to update Frontpageconfig : {}", frontpageconfigDTO);
        Frontpageconfig frontpageconfig = frontpageconfigMapper.toEntity(frontpageconfigDTO);
        frontpageconfig = frontpageconfigRepository.save(frontpageconfig);
        return frontpageconfigMapper.toDto(frontpageconfig);
    }

    @Override
    public Optional<FrontpageconfigDTO> partialUpdate(FrontpageconfigDTO frontpageconfigDTO) {
        LOG.debug("Request to partially update Frontpageconfig : {}", frontpageconfigDTO);

        return frontpageconfigRepository
            .findById(frontpageconfigDTO.getId())
            .map(existingFrontpageconfig -> {
                frontpageconfigMapper.partialUpdate(existingFrontpageconfig, frontpageconfigDTO);

                return existingFrontpageconfig;
            })
            .map(frontpageconfigRepository::save)
            .map(frontpageconfigMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FrontpageconfigDTO> findOne(Long id) {
        LOG.debug("Request to get Frontpageconfig : {}", id);
        return frontpageconfigRepository.findById(id).map(frontpageconfigMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Frontpageconfig : {}", id);
        frontpageconfigRepository.deleteById(id);
    }
}
