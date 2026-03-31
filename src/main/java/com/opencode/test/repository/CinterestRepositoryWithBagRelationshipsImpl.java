package com.opencode.test.repository;

import com.opencode.test.domain.Cinterest;
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
public class CinterestRepositoryWithBagRelationshipsImpl implements CinterestRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String CINTERESTS_PARAMETER = "cinterests";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cinterest> fetchBagRelationships(Optional<Cinterest> cinterest) {
        return cinterest.map(this::fetchCommunities);
    }

    @Override
    public Page<Cinterest> fetchBagRelationships(Page<Cinterest> cinterests) {
        return new PageImpl<>(fetchBagRelationships(cinterests.getContent()), cinterests.getPageable(), cinterests.getTotalElements());
    }

    @Override
    public List<Cinterest> fetchBagRelationships(List<Cinterest> cinterests) {
        return Optional.of(cinterests).map(this::fetchCommunities).orElse(Collections.emptyList());
    }

    Cinterest fetchCommunities(Cinterest result) {
        return entityManager
            .createQuery(
                "select cinterest from Cinterest cinterest left join fetch cinterest.communities where cinterest.id = :id",
                Cinterest.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Cinterest> fetchCommunities(List<Cinterest> cinterests) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, cinterests.size()).forEach(index -> order.put(cinterests.get(index).getId(), index));
        List<Cinterest> result = entityManager
            .createQuery(
                "select cinterest from Cinterest cinterest left join fetch cinterest.communities where cinterest in :cinterests",
                Cinterest.class
            )
            .setParameter(CINTERESTS_PARAMETER, cinterests)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
