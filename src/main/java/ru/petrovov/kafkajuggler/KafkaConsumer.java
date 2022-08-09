package ru.petrovov.kafkajuggler;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    @KafkaListener(topics = {"chuck"}, groupId = "spring-kafka", containerFactory = "concurrentKafkaListenerContainerFactory")
    private void consume(String event) {
        System.out.println("Received: " + event);
    }

}
