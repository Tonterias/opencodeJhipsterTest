package com.opencode.test.repository;

import com.opencode.test.domain.Cactivity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CactivityRepositoryWithBagRelationshipsImpl implements CactivityRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String CACTIVITIES_PARAMETER = "cactivities";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cactivity> fetchBagRelationships(Optional<Cactivity> cactivity) {
        return cactivity.map(this::fetchCommunities);
    }

    @Override
    public Page<Cactivity> fetchBagRelationships(Page<Cactivity> cactivities) {
        return new PageImpl<>(fetchBagRelationships(cactivities.getContent()), cactivities.getPageable(), cactivities.getTotalElements());
    }

    @Override
    public List<Cactivity> fetchBagRelationships(List<Cactivity> cactivities) {
        return Optional.of(cactivities).map(this::fetchCommunities).orElse(Collections.emptyList());
    }

    Cactivity fetchCommunities(Cactivity result) {
        return entityManager
            .createQuery(
                "select cactivity from Cactivity cactivity left join fetch cactivity.communities where cactivity.id = :id",
                Cactivity.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Cactivity> fetchCommunities(List<Cactivity> cactivities) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cactivities.size()).forEach(index -> order.put(cactivities.get(index).getId(), index));
        List<Cactivity> result = entityManager
            .createQuery(
                "select cactivity from Cactivity cactivity left join fetch cactivity.communities where cactivity in :cactivities",
                Cactivity.class
            )
            .setParameter(CACTIVITIES_PARAMETER, cactivities)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
