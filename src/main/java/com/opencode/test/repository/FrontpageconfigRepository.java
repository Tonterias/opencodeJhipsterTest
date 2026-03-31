package com.opencode.test.repository;

import com.opencode.test.domain.Frontpageconfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Frontpageconfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FrontpageconfigRepository extends JpaRepository<Frontpageconfig, Long>, JpaSpecificationExecutor<Frontpageconfig> {}
