package com.opencode.test.service.mapper;

import com.opencode.test.domain.Post;
import com.opencode.test.domain.Topic;
import com.opencode.test.service.dto.PostDTO;
import com.opencode.test.service.dto.TopicDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Topic} and its DTO {@link TopicDTO}.
 */
@Mapper(componentModel = "spring")
public interface TopicMapper extends EntityMapper<TopicDTO, Topic> {
    @Mapping(target = "posts", source = "posts", qualifiedByName = "postHeadlineSet")
    TopicDTO toDto(Topic s);

    @Mapping(target = "removePost", ignore = true)
    Topic toEntity(TopicDTO topicDTO);

    @Named("postHeadline")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "headline", source = "headline")
    PostDTO toDtoPostHeadline(Post post);

    @Named("postHeadlineSet")
    default Set<PostDTO> toDtoPostHeadlineSet(Set<Post> post) {
        return post.stream().map(this::toDtoPostHeadline).collect(Collectors.toSet());
    }
}
