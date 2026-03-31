package com.opencode.test.repository;

import com.opencode.test.domain.Cceleb;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cceleb entity.
 *
 * When extending this class, extend CcelebRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CcelebRepository
    extends CcelebRepositoryWithBagRelationships, JpaRepository<Cceleb, Long>, JpaSpecificationExecutor<Cceleb>
{
    default Optional<Cceleb> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Cceleb> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Cceleb> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
