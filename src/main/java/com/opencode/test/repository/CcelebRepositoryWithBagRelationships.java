package com.opencode.test.repository;

import com.opencode.test.domain.Cceleb;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CcelebRepositoryWithBagRelationships {
    Optional<Cceleb> fetchBagRelationships(Optional<Cceleb> cceleb);

    List<Cceleb> fetchBagRelationships(List<Cceleb> ccelebs);

    Page<Cceleb> fetchBagRelationships(Page<Cceleb> ccelebs);
}
