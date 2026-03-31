package com.opencode.test.service.mapper;

import com.opencode.test.domain.Cceleb;
import com.opencode.test.domain.Community;
import com.opencode.test.service.dto.CcelebDTO;
import com.opencode.test.service.dto.CommunityDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cceleb} and its DTO {@link CcelebDTO}.
 */
@Mapper(componentModel = "spring")
public interface CcelebMapper extends EntityMapper<CcelebDTO, Cceleb> {
    @Mapping(target = "communities", source = "communities", qualifiedByName = "communityIdSet")
    CcelebDTO toDto(Cceleb s);

    @Mapping(target = "removeCommunity", ignore = true)
    Cceleb toEntity(CcelebDTO ccelebDTO);

    @Named("communityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommunityDTO toDtoCommunityId(Community community);

    @Named("communityIdSet")
    default Set<CommunityDTO> toDtoCommunityIdSet(Set<Community> community) {
        return community.stream().map(this::toDtoCommunityId).collect(Collectors.toSet());
    }
}
