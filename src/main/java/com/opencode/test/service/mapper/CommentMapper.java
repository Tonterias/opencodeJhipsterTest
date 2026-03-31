package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Comment;
import com.opencode.test.domain.Post;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.CommentDTO;
import com.opencode.test.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "appuser", source = "appuser", qualifiedByName = "appuserId")
    @Mapping(target = "post", source = "post", qualifiedByName = "postId")
    CommentDTO toDto(Comment s);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);
}
