package ru.petrovov.kafkajuggler;

import org.apache.kafka.clients.admin.NewTopic;

public interface TopicCreatorService {

    NewTopic createOrUpdateTopic(TopicConfig config);
}
