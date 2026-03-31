package com.opencode.test.service.mapper;

import com.opencode.test.domain.Post;
import com.opencode.test.domain.Tag;
import com.opencode.test.service.dto.PostDTO;
import com.opencode.test.service.dto.TagDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "posts", source = "posts", qualifiedByName = "postHeadlineSet")
    TagDTO toDto(Tag s);

    @Mapping(target = "removePost", ignore = true)
    Tag toEntity(TagDTO tagDTO);

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
