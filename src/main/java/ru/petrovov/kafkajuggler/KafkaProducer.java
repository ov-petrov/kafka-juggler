package ru.petrovov.kafkajuggler;

import lombok.RequiredArgsConstructor;
import net.datafaker.ChuckNorris;
import net.datafaker.Faker;
import net.datafaker.GameOfThrones;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final TopicCreatorService topicCreatorService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Faker faker = Faker.instance();

    @EventListener(ApplicationReadyEvent.class)
    private void invoke() {
        createTopicAndPublish();
    }

    private void publishToTopic() {
        ChuckNorris chuck = faker.chuckNorris();

        Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));
        Flux<String> chuckFlux = Flux.fromStream(Stream.generate(chuck::fact));

        Flux.zip(interval, chuckFlux)
                .map(v -> kafkaTemplate.send("chuck",
                        Integer.toString(faker.random().nextInt(157)),
                        v.getT2()))
                .blockLast();
    }

    private void createTopicAndPublish() {
        GameOfThrones gameOfThrones = faker.gameOfThrones();

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        Flux<String> gotQuote = Flux.fromStream(Stream.generate(gameOfThrones::quote));

        List<NewTopic> characterTopics = IntStream.rangeClosed(0, 4)
                .mapToObj(v -> gameOfThrones.character().replaceAll(" ", "_").toLowerCase())
                .map(v -> topicCreatorService.createOrUpdateTopic(TopicConfig.builder()
                        .name(v)
                        .partitions(1)
                        .replicas(1)
                        .build()))
                .toList();

        Flux.zip(interval, gotQuote)
                .map(v -> kafkaTemplate.send(characterTopics.get(faker.random().nextInt(5)).name(),
                        Integer.toString(faker.random().nextInt(100)),
                        v.getT2()))
                .blockLast();
    }
}
