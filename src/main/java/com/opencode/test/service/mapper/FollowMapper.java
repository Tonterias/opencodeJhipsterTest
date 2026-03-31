package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Community;
import com.opencode.test.domain.Follow;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.CommunityDTO;
import com.opencode.test.service.dto.FollowDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Follow} and its DTO {@link FollowDTO}.
 */
@Mapper(componentModel = "spring")
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {
    @Mapping(target = "followed", source = "followed", qualifiedByName = "appuserId")
    @Mapping(target = "following", source = "following", qualifiedByName = "appuserId")
    @Mapping(target = "cfollowed", source = "cfollowed", qualifiedByName = "communityId")
    @Mapping(target = "cfollowing", source = "cfollowing", qualifiedByName = "communityId")
    FollowDTO toDto(Follow s);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);

    @Named("communityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommunityDTO toDtoCommunityId(Community community);
}
