package com.opencode.test.service.impl;

import com.opencode.test.domain.Interest;
import com.opencode.test.repository.InterestRepository;
import com.opencode.test.service.InterestService;
import com.opencode.test.service.dto.InterestDTO;
import com.opencode.test.service.mapper.InterestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Interest}.
 */
@Service
@Transactional
public class InterestServiceImpl implements InterestService {

    private static final Logger LOG = LoggerFactory.getLogger(InterestServiceImpl.class);

    private final InterestRepository interestRepository;

    private final InterestMapper interestMapper;

    public InterestServiceImpl(InterestRepository interestRepository, InterestMapper interestMapper) {
        this.interestRepository = interestRepository;
        this.interestMapper = interestMapper;
    }

    @Override
    public InterestDTO save(InterestDTO interestDTO) {
        LOG.debug("Request to save Interest : {}", interestDTO);
        Interest interest = interestMapper.toEntity(interestDTO);
        interest = interestRepository.save(interest);
        return interestMapper.toDto(interest);
    }

    @Override
    public InterestDTO update(InterestDTO interestDTO) {
        LOG.debug("Request to update Interest : {}", interestDTO);
        Interest interest = interestMapper.toEntity(interestDTO);
        interest = interestRepository.save(interest);
        return interestMapper.toDto(interest);
    }

    @Override
    public Optional<InterestDTO> partialUpdate(InterestDTO interestDTO) {
        LOG.debug("Request to partially update Interest : {}", interestDTO);

        return interestRepository
            .findById(interestDTO.getId())
            .map(existingInterest -> {
                interestMapper.partialUpdate(existingInterest, interestDTO);

                return existingInterest;
            })
            .map(interestRepository::save)
            .map(interestMapper::toDto);
    }

    public Page<InterestDTO> findAllWithEagerRelationships(Pageable pageable) {
        return interestRepository.findAllWithEagerRelationships(pageable).map(interestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InterestDTO> findOne(Long id) {
        LOG.debug("Request to get Interest : {}", id);
        return interestRepository.findOneWithEagerRelationships(id).map(interestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Interest : {}", id);
        interestRepository.deleteById(id);
    }
}
