package com.opencode.test.service.impl;

import com.opencode.test.domain.Tag;
import com.opencode.test.repository.TagRepository;
import com.opencode.test.service.TagService;
import com.opencode.test.service.dto.TagDTO;
import com.opencode.test.service.mapper.TagMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Tag}.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    private static final Logger LOG = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public TagDTO save(TagDTO tagDTO) {
        LOG.debug("Request to save Tag : {}", tagDTO);
        Tag tag = tagMapper.toEntity(tagDTO);
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    @Override
    public TagDTO update(TagDTO tagDTO) {
        LOG.debug("Request to update Tag : {}", tagDTO);
        Tag tag = tagMapper.toEntity(tagDTO);
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    @Override
    public Optional<TagDTO> partialUpdate(TagDTO tagDTO) {
        LOG.debug("Request to partially update Tag : {}", tagDTO);

        return tagRepository
            .findById(tagDTO.getId())
            .map(existingTag -> {
                tagMapper.partialUpdate(existingTag, tagDTO);

                return existingTag;
            })
            .map(tagRepository::save)
            .map(tagMapper::toDto);
    }

    public Page<TagDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tagRepository.findAllWithEagerRelationships(pageable).map(tagMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TagDTO> findOne(Long id) {
        LOG.debug("Request to get Tag : {}", id);
        return tagRepository.findOneWithEagerRelationships(id).map(tagMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Tag : {}", id);
        tagRepository.deleteById(id);
    }
}
