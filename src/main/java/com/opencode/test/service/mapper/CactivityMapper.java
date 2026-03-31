package com.opencode.test.service.mapper;

import com.opencode.test.domain.Cactivity;
import com.opencode.test.domain.Community;
import com.opencode.test.service.dto.CactivityDTO;
import com.opencode.test.service.dto.CommunityDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cactivity} and its DTO {@link CactivityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CactivityMapper extends EntityMapper<CactivityDTO, Cactivity> {
    @Mapping(target = "communities", source = "communities", qualifiedByName = "communityIdSet")
    CactivityDTO toDto(Cactivity s);

    @Mapping(target = "removeCommunity", ignore = true)
    Cactivity toEntity(CactivityDTO cactivityDTO);

    @Named("communityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommunityDTO toDtoCommunityId(Community community);

    @Named("communityIdSet")
    default Set<CommunityDTO> toDtoCommunityIdSet(Set<Community> community) {
        return community.stream().map(this::toDtoCommunityId).collect(Collectors.toSet());
    }
}
