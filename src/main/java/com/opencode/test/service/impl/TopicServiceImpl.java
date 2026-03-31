package com.opencode.test.service.impl;

import com.opencode.test.domain.Topic;
import com.opencode.test.repository.TopicRepository;
import com.opencode.test.service.TopicService;
import com.opencode.test.service.dto.TopicDTO;
import com.opencode.test.service.mapper.TopicMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Topic}.
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private static final Logger LOG = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;

    private final TopicMapper topicMapper;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    @Override
    public TopicDTO save(TopicDTO topicDTO) {
        LOG.debug("Request to save Topic : {}", topicDTO);
        Topic topic = topicMapper.toEntity(topicDTO);
        topic = topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    @Override
    public TopicDTO update(TopicDTO topicDTO) {
        LOG.debug("Request to update Topic : {}", topicDTO);
        Topic topic = topicMapper.toEntity(topicDTO);
        topic = topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    @Override
    public Optional<TopicDTO> partialUpdate(TopicDTO topicDTO) {
        LOG.debug("Request to partially update Topic : {}", topicDTO);

        return topicRepository
            .findById(topicDTO.getId())
            .map(existingTopic -> {
                topicMapper.partialUpdate(existingTopic, topicDTO);

                return existingTopic;
            })
            .map(topicRepository::save)
            .map(topicMapper::toDto);
    }

    public Page<TopicDTO> findAllWithEagerRelationships(Pageable pageable) {
        return topicRepository.findAllWithEagerRelationships(pageable).map(topicMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TopicDTO> findOne(Long id) {
        LOG.debug("Request to get Topic : {}", id);
        return topicRepository.findOneWithEagerRelationships(id).map(topicMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Topic : {}", id);
        topicRepository.deleteById(id);
    }
}
