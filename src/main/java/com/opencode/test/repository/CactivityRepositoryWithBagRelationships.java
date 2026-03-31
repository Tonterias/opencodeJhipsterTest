package com.opencode.test.repository;

import com.opencode.test.domain.Cactivity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CactivityRepositoryWithBagRelationships {
    Optional<Cactivity> fetchBagRelationships(Optional<Cactivity> cactivity);

    List<Cactivity> fetchBagRelationships(List<Cactivity> cactivities);

    Page<Cactivity> fetchBagRelationships(Page<Cactivity> cactivities);
}
