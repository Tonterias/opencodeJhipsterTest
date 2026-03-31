package com.opencode.test.service.mapper;

import com.opencode.test.domain.Appuser;
import com.opencode.test.domain.Notification;
import com.opencode.test.service.dto.AppuserDTO;
import com.opencode.test.service.dto.NotificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "appuser", source = "appuser", qualifiedByName = "appuserId")
    NotificationDTO toDto(Notification s);

    @Named("appuserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppuserDTO toDtoAppuserId(Appuser appuser);
}
