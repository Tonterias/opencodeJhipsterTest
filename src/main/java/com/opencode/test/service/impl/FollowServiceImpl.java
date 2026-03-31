package com.opencode.test.service.impl;

import com.opencode.test.domain.Follow;
import com.opencode.test.repository.FollowRepository;
import com.opencode.test.service.FollowService;
import com.opencode.test.service.dto.FollowDTO;
import com.opencode.test.service.mapper.FollowMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Follow}.
 */
@Service
@Transactional
public class FollowServiceImpl implements FollowService {

    private static final Logger LOG = LoggerFactory.getLogger(FollowServiceImpl.class);

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    public FollowServiceImpl(FollowRepository followRepository, FollowMapper followMapper) {
        this.followRepository = followRepository;
        this.followMapper = followMapper;
    }

    @Override
    public FollowDTO save(FollowDTO followDTO) {
        LOG.debug("Request to save Follow : {}", followDTO);
        Follow follow = followMapper.toEntity(followDTO);
        follow = followRepository.save(follow);
        return followMapper.toDto(follow);
    }

    @Override
    public FollowDTO update(FollowDTO followDTO) {
        LOG.debug("Request to update Follow : {}", followDTO);
        Follow follow = followMapper.toEntity(followDTO);
        follow = followRepository.save(follow);
        return followMapper.toDto(follow);
    }

    @Override
    public Optional<FollowDTO> partialUpdate(FollowDTO followDTO) {
        LOG.debug("Request to partially update Follow : {}", followDTO);

        return followRepository
            .findById(followDTO.getId())
            .map(existingFollow -> {
                followMapper.partialUpdate(existingFollow, followDTO);

                return existingFollow;
            })
            .map(followRepository::save)
            .map(followMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FollowDTO> findOne(Long id) {
        LOG.debug("Request to get Follow : {}", id);
        return followRepository.findById(id).map(followMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Follow : {}", id);
        followRepository.deleteById(id);
    }
}
