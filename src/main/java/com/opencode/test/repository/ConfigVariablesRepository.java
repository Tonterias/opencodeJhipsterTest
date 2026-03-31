package com.opencode.test.repository;

import com.opencode.test.domain.ConfigVariables;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ConfigVariables entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigVariablesRepository extends JpaRepository<ConfigVariables, Long>, JpaSpecificationExecutor<ConfigVariables> {}
