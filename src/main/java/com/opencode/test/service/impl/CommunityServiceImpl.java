package com.opencode.test.service.impl;

import com.opencode.test.domain.Community;
import com.opencode.test.repository.CommunityRepository;
import com.opencode.test.service.CommunityService;
import com.opencode.test.service.dto.CommunityDTO;
import com.opencode.test.service.mapper.CommunityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Community}.
 */
@Service
@Transactional
public class CommunityServiceImpl implements CommunityService {

    private static final Logger LOG = LoggerFactory.getLogger(CommunityServiceImpl.class);

    private final CommunityRepository communityRepository;

    private final CommunityMapper communityMapper;

    public CommunityServiceImpl(CommunityRepository communityRepository, CommunityMapper communityMapper) {
        this.communityRepository = communityRepository;
        this.communityMapper = communityMapper;
    }

    @Override
    public CommunityDTO save(CommunityDTO communityDTO) {
        LOG.debug("Request to save Community : {}", communityDTO);
        Community community = communityMapper.toEntity(communityDTO);
        community = communityRepository.save(community);
        return communityMapper.toDto(community);
    }

    @Override
    public CommunityDTO update(CommunityDTO communityDTO) {
        LOG.debug("Request to update Community : {}", communityDTO);
        Community community = communityMapper.toEntity(communityDTO);
        community = communityRepository.save(community);
        return communityMapper.toDto(community);
    }

    @Override
    public Optional<CommunityDTO> partialUpdate(CommunityDTO communityDTO) {
        LOG.debug("Request to partially update Community : {}", communityDTO);

        return communityRepository
            .findById(communityDTO.getId())
            .map(existingCommunity -> {
                communityMapper.partialUpdate(existingCommunity, communityDTO);

                return existingCommunity;
            })
            .map(communityRepository::save)
            .map(communityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommunityDTO> findOne(Long id) {
        LOG.debug("Request to get Community : {}", id);
        return communityRepository.findById(id).map(communityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Community : {}", id);
        communityRepository.deleteById(id);
    }
}
