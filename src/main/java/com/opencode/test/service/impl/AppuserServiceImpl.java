package com.opencode.test.service.impl;

import com.opencode.test.domain.Appuser;
import com.opencode.test.repository.AppuserRepository;
import com.opencode.test.service.AppuserService;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.mapper.AppuserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Appuser}.
 */
@Service
@Transactional
public class AppuserServiceImpl implements AppuserService {

    private static final Logger LOG = LoggerFactory.getLogger(AppuserServiceImpl.class);

    private final AppuserRepository appuserRepository;

    private final AppuserMapper appuserMapper;

    public AppuserServiceImpl(AppuserRepository appuserRepository, AppuserMapper appuserMapper) {
        this.appuserRepository = appuserRepository;
        this.appuserMapper = appuserMapper;
    }

    @Override
    public AppuserDTO save(AppuserDTO appuserDTO) {
        LOG.debug("Request to save Appuser : {}", appuserDTO);
        Appuser appuser = appuserMapper.toEntity(appuserDTO);
        appuser = appuserRepository.save(appuser);
        return appuserMapper.toDto(appuser);
    }

    @Override
    public AppuserDTO update(AppuserDTO appuserDTO) {
        LOG.debug("Request to update Appuser : {}", appuserDTO);
        Appuser appuser = appuserMapper.toEntity(appuserDTO);
        appuser = appuserRepository.save(appuser);
        return appuserMapper.toDto(appuser);
    }

    @Override
    public Optional<AppuserDTO> partialUpdate(AppuserDTO appuserDTO) {
        LOG.debug("Request to partially update Appuser : {}", appuserDTO);

        return appuserRepository
            .findById(appuserDTO.getId())
            .map(existingAppuser -> {
                appuserMapper.partialUpdate(existingAppuser, appuserDTO);

                return existingAppuser;
            })
            .map(appuserRepository::save)
            .map(appuserMapper::toDto);
    }

    /**
     *  Get all the appusers where Appphoto is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppuserDTO> findAllWhereAppphotoIsNull() {
        LOG.debug("Request to get all appusers where Appphoto is null");
        return StreamSupport.stream(appuserRepository.findAll().spliterator(), false)
            .filter(appuser -> appuser.getAppphoto() == null)
            .map(appuserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppuserDTO> findOne(Long id) {
        LOG.debug("Request to get Appuser : {}", id);
        return appuserRepository.findById(id).map(appuserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Appuser : {}", id);
        appuserRepository.deleteById(id);
    }
}
