package com.opencode.test.service.impl;

import com.opencode.test.domain.ConfigVariables;
import com.opencode.test.repository.ConfigVariablesRepository;
import com.opencode.test.service.ConfigVariablesService;
import com.opencode.test.service.dto.ConfigVariablesDTO;
import com.opencode.test.service.mapper.ConfigVariablesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.ConfigVariables}.
 */
@Service
@Transactional
public class ConfigVariablesServiceImpl implements ConfigVariablesService {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigVariablesServiceImpl.class);

    private final ConfigVariablesRepository configVariablesRepository;

    private final ConfigVariablesMapper configVariablesMapper;

    public ConfigVariablesServiceImpl(ConfigVariablesRepository configVariablesRepository, ConfigVariablesMapper configVariablesMapper) {
        this.configVariablesRepository = configVariablesRepository;
        this.configVariablesMapper = configVariablesMapper;
    }

    @Override
    public ConfigVariablesDTO save(ConfigVariablesDTO configVariablesDTO) {
        LOG.debug("Request to save ConfigVariables : {}", configVariablesDTO);
        ConfigVariables configVariables = configVariablesMapper.toEntity(configVariablesDTO);
        configVariables = configVariablesRepository.save(configVariables);
        return configVariablesMapper.toDto(configVariables);
    }

    @Override
    public ConfigVariablesDTO update(ConfigVariablesDTO configVariablesDTO) {
        LOG.debug("Request to update ConfigVariables : {}", configVariablesDTO);
        ConfigVariables configVariables = configVariablesMapper.toEntity(configVariablesDTO);
        configVariables = configVariablesRepository.save(configVariables);
        return configVariablesMapper.toDto(configVariables);
    }

    @Override
    public Optional<ConfigVariablesDTO> partialUpdate(ConfigVariablesDTO configVariablesDTO) {
        LOG.debug("Request to partially update ConfigVariables : {}", configVariablesDTO);

        return configVariablesRepository
            .findById(configVariablesDTO.getId())
            .map(existingConfigVariables -> {
                configVariablesMapper.partialUpdate(existingConfigVariables, configVariablesDTO);

                return existingConfigVariables;
            })
            .map(configVariablesRepository::save)
            .map(configVariablesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigVariablesDTO> findOne(Long id) {
        LOG.debug("Request to get ConfigVariables : {}", id);
        return configVariablesRepository.findById(id).map(configVariablesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ConfigVariables : {}", id);
        configVariablesRepository.deleteById(id);
    }
}
