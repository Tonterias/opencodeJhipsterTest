package com.opencode.test.repository;

import com.opencode.test.domain.Cceleb;
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
public class CcelebRepositoryWithBagRelationshipsImpl implements CcelebRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String CCELEBS_PARAMETER = "ccelebs";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cceleb> fetchBagRelationships(Optional<Cceleb> cceleb) {
        return cceleb.map(this::fetchCommunities);
    }

    @Override
    public Page<Cceleb> fetchBagRelationships(Page<Cceleb> ccelebs) {
        return new PageImpl<>(fetchBagRelationships(ccelebs.getContent()), ccelebs.getPageable(), ccelebs.getTotalElements());
    }

    @Override
    public List<Cceleb> fetchBagRelationships(List<Cceleb> ccelebs) {
        return Optional.of(ccelebs).map(this::fetchCommunities).orElse(Collections.emptyList());
    }

    Cceleb fetchCommunities(Cceleb result) {
        return entityManager
            .createQuery("select cceleb from Cceleb cceleb left join fetch cceleb.communities where cceleb.id = :id", Cceleb.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Cceleb> fetchCommunities(List<Cceleb> ccelebs) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, ccelebs.size()).forEach(index -> order.put(ccelebs.get(index).getId(), index));
        List<Cceleb> result = entityManager
            .createQuery("select cceleb from Cceleb cceleb left join fetch cceleb.communities where cceleb in :ccelebs", Cceleb.class)
            .setParameter(CCELEBS_PARAMETER, ccelebs)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
