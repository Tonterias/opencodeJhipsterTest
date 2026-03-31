package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Blog;
import com.opencode.test.domain.Community;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.BlogDTO;
import com.opencode.test.service.dto.CommunityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Blog} and its DTO {@link BlogDTO}.
 */
@Mapper(componentModel = "spring")
public interface BlogMapper extends EntityMapper<BlogDTO, Blog> {
    @Mapping(target = "appuser", source = "appuser", qualifiedByName = "appuserId")
    @Mapping(target = "community", source = "community", qualifiedByName = "communityCommunityName")
    BlogDTO toDto(Blog s);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);

    @Named("communityCommunityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "communityName", source = "communityName")
    CommunityDTO toDtoCommunityCommunityName(Community community);
}
