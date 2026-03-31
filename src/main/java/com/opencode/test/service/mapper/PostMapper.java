package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Blog;
import com.opencode.test.domain.Post;
import com.opencode.test.domain.Tag;
import com.opencode.test.domain.Topic;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.BlogDTO;
import com.opencode.test.service.dto.PostDTO;
import com.opencode.test.service.dto.TagDTO;
import com.opencode.test.service.dto.TopicDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "appuser", source = "appuser", qualifiedByName = "appuserId")
    @Mapping(target = "blog", source = "blog", qualifiedByName = "blogTitle")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagTagNameSet")
    @Mapping(target = "topics", source = "topics", qualifiedByName = "topicTopicNameSet")
    PostDTO toDto(Post s);

    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "removeTag", ignore = true)
    @Mapping(target = "topics", ignore = true)
    @Mapping(target = "removeTopic", ignore = true)
    Post toEntity(PostDTO postDTO);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);

    @Named("blogTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    BlogDTO toDtoBlogTitle(Blog blog);

    @Named("tagTagName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tagName", source = "tagName")
    TagDTO toDtoTagTagName(Tag tag);

    @Named("tagTagNameSet")
    default Set<TagDTO> toDtoTagTagNameSet(Set<Tag> tag) {
        return tag.stream().map(this::toDtoTagTagName).collect(Collectors.toSet());
    }

    @Named("topicTopicName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "topicName", source = "topicName")
    TopicDTO toDtoTopicTopicName(Topic topic);

    @Named("topicTopicNameSet")
    default Set<TopicDTO> toDtoTopicTopicNameSet(Set<Topic> topic) {
        return topic.stream().map(this::toDtoTopicTopicName).collect(Collectors.toSet());
    }
}
