package com.opencode.test.repository;

import com.opencode.test.domain.Celeb;
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
public class CelebRepositoryWithBagRelationshipsImpl implements CelebRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String CELEBS_PARAMETER = "celebs";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Celeb> fetchBagRelationships(Optional<Celeb> celeb) {
        return celeb.map(this::fetchAppusers);
    }

    @Override
    public Page<Celeb> fetchBagRelationships(Page<Celeb> celebs) {
        return new PageImpl<>(fetchBagRelationships(celebs.getContent()), celebs.getPageable(), celebs.getTotalElements());
    }

    @Override
    public List<Celeb> fetchBagRelationships(List<Celeb> celebs) {
        return Optional.of(celebs).map(this::fetchAppusers).orElse(Collections.emptyList());
    }

    Celeb fetchAppusers(Celeb result) {
        return entityManager
            .createQuery("select celeb from Celeb celeb left join fetch celeb.appusers where celeb.id = :id", Celeb.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Celeb> fetchAppusers(List<Celeb> celebs) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, celebs.size()).forEach(index -> order.put(celebs.get(index).getId(), index));
        List<Celeb> result = entityManager
            .createQuery("select celeb from Celeb celeb left join fetch celeb.appusers where celeb in :celebs", Celeb.class)
            .setParameter(CELEBS_PARAMETER, celebs)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
