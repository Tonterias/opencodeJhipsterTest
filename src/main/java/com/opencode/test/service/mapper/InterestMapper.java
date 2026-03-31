package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Interest;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.InterestDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Interest} and its DTO {@link InterestDTO}.
 */
@Mapper(componentModel = "spring")
public interface InterestMapper extends EntityMapper<InterestDTO, Interest> {
    @Mapping(target = "appusers", source = "appusers", qualifiedByName = "appuserIdSet")
    InterestDTO toDto(Interest s);

    @Mapping(target = "removeAppuser", ignore = true)
    Interest toEntity(InterestDTO interestDTO);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);

    @Named("appuserIdSet")
    default Set<AppuserDTO> toDtoAppuserIdSet(Set<Appuser> appuser) {
        return appuser.stream().map(this::toDtoAppuserId).collect(Collectors.toSet());
    }
}
