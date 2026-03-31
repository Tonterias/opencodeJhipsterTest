package com.opencode.test.repository;

import com.opencode.test.domain.Celeb;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CelebRepositoryWithBagRelationships {
    Optional<Celeb> fetchBagRelationships(Optional<Celeb> celeb);

    List<Celeb> fetchBagRelationships(List<Celeb> celebs);

    Page<Celeb> fetchBagRelationships(Page<Celeb> celebs);
}
