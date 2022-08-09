package ru.petrovov.kafkajuggler;

import lombok.RequiredArgsConstructor;
import net.datafaker.ChuckNorris;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Faker faker = Faker.instance();

    @EventListener(ApplicationReadyEvent.class)
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
}
