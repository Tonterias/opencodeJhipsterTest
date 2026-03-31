package com.opencode.test.service.impl;

import com.opencode.test.domain.Urllink;
import com.opencode.test.repository.UrllinkRepository;
import com.opencode.test.service.UrllinkService;
import com.opencode.test.service.dto.UrllinkDTO;
import com.opencode.test.service.mapper.UrllinkMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Urllink}.
 */
@Service
@Transactional
public class UrllinkServiceImpl implements UrllinkService {

    private static final Logger LOG = LoggerFactory.getLogger(UrllinkServiceImpl.class);

    private final UrllinkRepository urllinkRepository;

    private final UrllinkMapper urllinkMapper;

    public UrllinkServiceImpl(UrllinkRepository urllinkRepository, UrllinkMapper urllinkMapper) {
        this.urllinkRepository = urllinkRepository;
        this.urllinkMapper = urllinkMapper;
    }

    @Override
    public UrllinkDTO save(UrllinkDTO urllinkDTO) {
        LOG.debug("Request to save Urllink : {}", urllinkDTO);
        Urllink urllink = urllinkMapper.toEntity(urllinkDTO);
        urllink = urllinkRepository.save(urllink);
        return urllinkMapper.toDto(urllink);
    }

    @Override
    public UrllinkDTO update(UrllinkDTO urllinkDTO) {
        LOG.debug("Request to update Urllink : {}", urllinkDTO);
        Urllink urllink = urllinkMapper.toEntity(urllinkDTO);
        urllink = urllinkRepository.save(urllink);
        return urllinkMapper.toDto(urllink);
    }

    @Override
    public Optional<UrllinkDTO> partialUpdate(UrllinkDTO urllinkDTO) {
        LOG.debug("Request to partially update Urllink : {}", urllinkDTO);

        return urllinkRepository
            .findById(urllinkDTO.getId())
            .map(existingUrllink -> {
                urllinkMapper.partialUpdate(existingUrllink, urllinkDTO);

                return existingUrllink;
            })
            .map(urllinkRepository::save)
            .map(urllinkMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UrllinkDTO> findOne(Long id) {
        LOG.debug("Request to get Urllink : {}", id);
        return urllinkRepository.findById(id).map(urllinkMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Urllink : {}", id);
        urllinkRepository.deleteById(id);
    }
}
