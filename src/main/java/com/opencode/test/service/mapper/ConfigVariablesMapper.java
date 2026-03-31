package com.opencode.test.service.mapper;

import com.opencode.test.domain.ConfigVariables;
import com.opencode.test.service.dto.ConfigVariablesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConfigVariables} and its DTO {@link ConfigVariablesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConfigVariablesMapper extends EntityMapper<ConfigVariablesDTO, ConfigVariables> {}
