package com.opencode.test.service.mapper;

import com.opencode.test.domain.Frontpageconfig;
import com.opencode.test.service.dto.FrontpageconfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Frontpageconfig} and its DTO {@link FrontpageconfigDTO}.
 */
@Mapper(componentModel = "spring")
public interface FrontpageconfigMapper extends EntityMapper<FrontpageconfigDTO, Frontpageconfig> {}
