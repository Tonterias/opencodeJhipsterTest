package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Celeb;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.CelebDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Celeb} and its DTO {@link CelebDTO}.
 */
@Mapper(componentModel = "spring")
public interface CelebMapper extends EntityMapper<CelebDTO, Celeb> {
    @Mapping(target = "appusers", source = "appusers", qualifiedByName = "appuserIdSet")
    CelebDTO toDto(Celeb s);

    @Mapping(target = "removeAppuser", ignore = true)
    Celeb toEntity(CelebDTO celebDTO);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);

    @Named("appuserIdSet")
    default Set<AppuserDTO> toDtoAppuserIdSet(Set<Appuser> appuser) {
        return appuser.stream().map(this::toDtoAppuserId).collect(Collectors.toSet());
    }
}
