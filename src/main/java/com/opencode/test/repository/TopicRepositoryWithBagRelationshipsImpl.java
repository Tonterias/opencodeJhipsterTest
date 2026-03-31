package com.opencode.test.repository;

import com.opencode.test.domain.Topic;
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
public class TopicRepositoryWithBagRelationshipsImpl implements TopicRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String TOPICS_PARAMETER = "topics";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Topic> fetchBagRelationships(Optional<Topic> topic) {
        return topic.map(this::fetchPosts);
    }

    @Override
    public Page<Topic> fetchBagRelationships(Page<Topic> topics) {
        return new PageImpl<>(fetchBagRelationships(topics.getContent()), topics.getPageable(), topics.getTotalElements());
    }

    @Override
    public List<Topic> fetchBagRelationships(List<Topic> topics) {
        return Optional.of(topics).map(this::fetchPosts).orElse(Collections.emptyList());
    }

    Topic fetchPosts(Topic result) {
        return entityManager
            .createQuery("select topic from Topic topic left join fetch topic.posts where topic.id = :id", Topic.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Topic> fetchPosts(List<Topic> topics) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, topics.size()).forEach(index -> order.put(topics.get(index).getId(), index));
        List<Topic> result = entityManager
            .createQuery("select topic from Topic topic left join fetch topic.posts where topic in :topics", Topic.class)
            .setParameter(TOPICS_PARAMETER, topics)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
