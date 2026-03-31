package com.opencode.test.service.mapper;

import com.opencode.test.domain.Activity;
import com.opencode.test.domain.Appuser;
import com.opencode.test.service.dto.ActivityDTO;
import com.opencode.test.service.dto.AppuserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Activity} and its DTO {@link ActivityDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {
    @Mapping(target = "appusers", source = "appusers", qualifiedByName = "appuserIdSet")
    ActivityDTO toDto(Activity s);

    @Mapping(target = "removeAppuser", ignore = true)
    Activity toEntity(ActivityDTO activityDTO);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);

    @Named("appuserIdSet")
    default Set<AppuserDTO> toDtoAppuserIdSet(Set<Appuser> appuser) {
        return appuser.stream().map(this::toDtoAppuserId).collect(Collectors.toSet());
    }
}
