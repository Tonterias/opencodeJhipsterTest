package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Cactivity;
import com.opencode.test.domain.Cceleb;
import com.opencode.test.domain.Cinterest;
import com.opencode.test.domain.Community;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.CactivityDTO;
import com.opencode.test.service.dto.CcelebDTO;
import com.opencode.test.service.dto.CinterestDTO;
import com.opencode.test.service.dto.CommunityDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Community} and its DTO {@link CommunityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommunityMapper extends EntityMapper<CommunityDTO, Community> {
    @Mapping(target = "appuser", source = "appuser", qualifiedByName = "appuserId")
    @Mapping(target = "cinterests", source = "cinterests", qualifiedByName = "cinterestInterestNameSet")
    @Mapping(target = "cactivities", source = "cactivities", qualifiedByName = "cactivityActivityNameSet")
    @Mapping(target = "ccelebs", source = "ccelebs", qualifiedByName = "ccelebCelebNameSet")
    CommunityDTO toDto(Community s);

    @Mapping(target = "cinterests", ignore = true)
    @Mapping(target = "removeCinterest", ignore = true)
    @Mapping(target = "cactivities", ignore = true)
    @Mapping(target = "removeCactivity", ignore = true)
    @Mapping(target = "ccelebs", ignore = true)
    @Mapping(target = "removeCceleb", ignore = true)
    Community toEntity(CommunityDTO communityDTO);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);

    @Named("cinterestInterestName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "interestName", source = "interestName")
    CinterestDTO toDtoCinterestInterestName(Cinterest cinterest);

    @Named("cinterestInterestNameSet")
    default Set<CinterestDTO> toDtoCinterestInterestNameSet(Set<Cinterest> cinterest) {
        return cinterest.stream().map(this::toDtoCinterestInterestName).collect(Collectors.toSet());
    }

    @Named("cactivityActivityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "activityName", source = "activityName")
    CactivityDTO toDtoCactivityActivityName(Cactivity cactivity);

    @Named("cactivityActivityNameSet")
    default Set<CactivityDTO> toDtoCactivityActivityNameSet(Set<Cactivity> cactivity) {
        return cactivity.stream().map(this::toDtoCactivityActivityName).collect(Collectors.toSet());
    }

    @Named("ccelebCelebName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "celebName", source = "celebName")
    CcelebDTO toDtoCcelebCelebName(Cceleb cceleb);

    @Named("ccelebCelebNameSet")
    default Set<CcelebDTO> toDtoCcelebCelebNameSet(Set<Cceleb> cceleb) {
        return cceleb.stream().map(this::toDtoCcelebCelebName).collect(Collectors.toSet());
    }
}
