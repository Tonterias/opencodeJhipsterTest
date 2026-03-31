package com.opencode.test.service.impl;

import com.opencode.test.domain.Blog;
import com.opencode.test.repository.BlogRepository;
import com.opencode.test.service.BlogService;
import com.opencode.test.service.dto.BlogDTO;
import com.opencode.test.service.mapper.BlogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.opencode.test.domain.Blog}.
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private static final Logger LOG = LoggerFactory.getLogger(BlogServiceImpl.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;

    public BlogServiceImpl(BlogRepository blogRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
    }

    @Override
    public BlogDTO save(BlogDTO blogDTO) {
        LOG.debug("Request to save Blog : {}", blogDTO);
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        return blogMapper.toDto(blog);
    }

    @Override
    public BlogDTO update(BlogDTO blogDTO) {
        LOG.debug("Request to update Blog : {}", blogDTO);
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        return blogMapper.toDto(blog);
    }

    @Override
    public Optional<BlogDTO> partialUpdate(BlogDTO blogDTO) {
        LOG.debug("Request to partially update Blog : {}", blogDTO);

        return blogRepository
            .findById(blogDTO.getId())
            .map(existingBlog -> {
                blogMapper.partialUpdate(existingBlog, blogDTO);

                return existingBlog;
            })
            .map(blogRepository::save)
            .map(blogMapper::toDto);
    }

    public Page<BlogDTO> findAllWithEagerRelationships(Pageable pageable) {
        return blogRepository.findAllWithEagerRelationships(pageable).map(blogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlogDTO> findOne(Long id) {
        LOG.debug("Request to get Blog : {}", id);
        return blogRepository.findOneWithEagerRelationships(id).map(blogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Blog : {}", id);
        blogRepository.deleteById(id);
    }
}
