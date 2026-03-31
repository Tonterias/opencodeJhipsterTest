package com.opencode.test.repository;

import com.opencode.test.domain.Interest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface InterestRepositoryWithBagRelationships {
    Optional<Interest> fetchBagRelationships(Optional<Interest> interest);

    List<Interest> fetchBagRelationships(List<Interest> interests);

    Page<Interest> fetchBagRelationships(Page<Interest> interests);
}
