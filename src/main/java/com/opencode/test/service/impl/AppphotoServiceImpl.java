package com.opencode.test.service.impl;

import com.opencode.test.domain.Appphoto;
import com.opencode.test.repository.AppphotoRepository;
import com.opencode.test.service.AppphotoService;
import com.opencode.test.service.dto.AppphotoDTO;
import com.opencode.test.service.mapper.AppphotoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Appphoto}.
 */
@Service
@Transactional
public class AppphotoServiceImpl implements AppphotoService {

    private static final Logger LOG = LoggerFactory.getLogger(AppphotoServiceImpl.class);

    private final AppphotoRepository appphotoRepository;

    private final AppphotoMapper appphotoMapper;

    public AppphotoServiceImpl(AppphotoRepository appphotoRepository, AppphotoMapper appphotoMapper) {
        this.appphotoRepository = appphotoRepository;
        this.appphotoMapper = appphotoMapper;
    }

    @Override
    public AppphotoDTO save(AppphotoDTO appphotoDTO) {
        LOG.debug("Request to save Appphoto : {}", appphotoDTO);
        Appphoto appphoto = appphotoMapper.toEntity(appphotoDTO);
        appphoto = appphotoRepository.save(appphoto);
        return appphotoMapper.toDto(appphoto);
    }

    @Override
    public AppphotoDTO update(AppphotoDTO appphotoDTO) {
        LOG.debug("Request to update Appphoto : {}", appphotoDTO);
        Appphoto appphoto = appphotoMapper.toEntity(appphotoDTO);
        appphoto = appphotoRepository.save(appphoto);
        return appphotoMapper.toDto(appphoto);
    }

    @Override
    public Optional<AppphotoDTO> partialUpdate(AppphotoDTO appphotoDTO) {
        LOG.debug("Request to partially update Appphoto : {}", appphotoDTO);

        return appphotoRepository
            .findById(appphotoDTO.getId())
            .map(existingAppphoto -> {
                appphotoMapper.partialUpdate(existingAppphoto, appphotoDTO);

                return existingAppphoto;
            })
            .map(appphotoRepository::save)
            .map(appphotoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppphotoDTO> findOne(Long id) {
        LOG.debug("Request to get Appphoto : {}", id);
        return appphotoRepository.findById(id).map(appphotoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Appphoto : {}", id);
        appphotoRepository.deleteById(id);
    }
}
