package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appphoto;
import com.opencode.test.domain.Appuser;
import com.opencode.test.service.dto.AppphotoDTO;
import com.opencode.test.service.dto.AppuserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Appphoto} and its DTO {@link AppphotoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppphotoMapper extends EntityMapper<AppphotoDTO, Appphoto> {
    @Mapping(target = "appuser", source = "appuser", qualifiedByName = "appuserId")
    AppphotoDTO toDto(Appphoto s);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);
}
