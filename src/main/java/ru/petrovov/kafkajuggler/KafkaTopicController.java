package ru.petrovov.kafkajuggler;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class KafkaTopicController {
    private final ConsumerFactory<String, String> consumerFactory;

    @GetMapping("/{topic}")
    public List<String> showTopic(@PathVariable String topic) {
        ConsumerRecords<String, String> records;

        var props = new Properties();
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
        try (Consumer<String, String> consumer = consumerFactory.createConsumer(null,
                null,
                null,
                props)) {
            var partition = new TopicPartition(topic, 0);
            consumer.assign(List.of(partition));
            consumer.seekToBeginning(List.of(partition));
            records = consumer.poll(Duration.ofSeconds(2));
        }

        var list = new ArrayList<String>();
        records.iterator().forEachRemaining(v -> list.add(v.key() + " : " + v.value()));

        return list;
    }

}
