package ru.petrovov.kafkajuggler;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

@Service
public class TopicCreatorServiceImpl implements TopicCreatorService {
    @Override
    public NewTopic createOrUpdateTopic(TopicConfig config) {
        return TopicBuilder.name(config.getName())
                .partitions(config.getPartitions())
                .replicas(config.getReplicas())
                .build();
    }
}
