package com.opencode.test.repository;

import com.opencode.test.domain.Cinterest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CinterestRepositoryWithBagRelationships {
    Optional<Cinterest> fetchBagRelationships(Optional<Cinterest> cinterest);

    List<Cinterest> fetchBagRelationships(List<Cinterest> cinterests);

    Page<Cinterest> fetchBagRelationships(Page<Cinterest> cinterests);
}
