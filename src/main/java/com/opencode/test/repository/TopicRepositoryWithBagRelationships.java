package com.opencode.test.repository;

import com.opencode.test.domain.Topic;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TopicRepositoryWithBagRelationships {
    Optional<Topic> fetchBagRelationships(Optional<Topic> topic);

    List<Topic> fetchBagRelationships(List<Topic> topics);

    Page<Topic> fetchBagRelationships(Page<Topic> topics);
}
