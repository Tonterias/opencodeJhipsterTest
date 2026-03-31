package com.opencode.test.service.mapper;

import com.opencode.test.domain.Urllink;
import com.opencode.test.service.dto.UrllinkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Urllink} and its DTO {@link UrllinkDTO}.
 */
@Mapper(componentModel = "spring")
public interface UrllinkMapper extends EntityMapper<UrllinkDTO, Urllink> {}
