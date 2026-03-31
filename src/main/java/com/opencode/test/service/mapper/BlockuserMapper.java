package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Blockuser;
import com.opencode.test.domain.Community;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.BlockuserDTO;
import com.opencode.test.service.dto.CommunityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Blockuser} and its DTO {@link BlockuserDTO}.
 */
@Mapper(componentModel = "spring")
public interface BlockuserMapper extends EntityMapper<BlockuserDTO, Blockuser> {
    @Mapping(target = "blockeduser", source = "blockeduser", qualifiedByName = "appuserId")
    @Mapping(target = "blockinguser", source = "blockinguser", qualifiedByName = "appuserId")
    @Mapping(target = "cblockeduser", source = "cblockeduser", qualifiedByName = "communityId")
    @Mapping(target = "cblockinguser", source = "cblockinguser", qualifiedByName = "communityId")
    BlockuserDTO toDto(Blockuser s);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);

    @Named("communityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommunityDTO toDtoCommunityId(Community community);
}
