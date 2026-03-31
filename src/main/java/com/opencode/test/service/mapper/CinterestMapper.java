package com.opencode.test.service.mapper;

import com.opencode.test.domain.Cinterest;
import com.opencode.test.domain.Community;
import com.opencode.test.service.dto.CinterestDTO;
import com.opencode.test.service.dto.CommunityDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cinterest} and its DTO {@link CinterestDTO}.
 */
@Mapper(componentModel = "spring")
public interface CinterestMapper extends EntityMapper<CinterestDTO, Cinterest> {
    @Mapping(target = "communities", source = "communities", qualifiedByName = "communityIdSet")
    CinterestDTO toDto(Cinterest s);

    @Mapping(target = "removeCommunity", ignore = true)
    Cinterest toEntity(CinterestDTO cinterestDTO);

    @Named("communityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommunityDTO toDtoCommunityId(Community community);

    @Named("communityIdSet")
    default Set<CommunityDTO> toDtoCommunityIdSet(Set<Community> community) {
        return community.stream().map(this::toDtoCommunityId).collect(Collectors.toSet());
    }
}
