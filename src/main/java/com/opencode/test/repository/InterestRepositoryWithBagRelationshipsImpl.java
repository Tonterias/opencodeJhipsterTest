package com.opencode.test.repository;

import com.opencode.test.domain.Interest;
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
public class InterestRepositoryWithBagRelationshipsImpl implements InterestRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String INTERESTS_PARAMETER = "interests";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Interest> fetchBagRelationships(Optional<Interest> interest) {
        return interest.map(this::fetchAppusers);
    }

    @Override
    public Page<Interest> fetchBagRelationships(Page<Interest> interests) {
        return new PageImpl<>(fetchBagRelationships(interests.getContent()), interests.getPageable(), interests.getTotalElements());
    }

    @Override
    public List<Interest> fetchBagRelationships(List<Interest> interests) {
        return Optional.of(interests).map(this::fetchAppusers).orElse(Collections.emptyList());
    }

    Interest fetchAppusers(Interest result) {
        return entityManager
            .createQuery("select interest from Interest interest left join fetch interest.appusers where interest.id = :id", Interest.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Interest> fetchAppusers(List<Interest> interests) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, interests.size()).forEach(index -> order.put(interests.get(index).getId(), index));
        List<Interest> result = entityManager
            .createQuery(
                "select interest from Interest interest left join fetch interest.appusers where interest in :interests",
                Interest.class
            )
            .setParameter(INTERESTS_PARAMETER, interests)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
