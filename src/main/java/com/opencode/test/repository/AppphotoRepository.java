package com.opencode.test.repository;

import com.opencode.test.domain.Appphoto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Appphoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppphotoRepository extends JpaRepository<Appphoto, Long>, JpaSpecificationExecutor<Appphoto> {}
