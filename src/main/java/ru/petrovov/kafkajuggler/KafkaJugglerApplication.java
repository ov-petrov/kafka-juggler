package ru.petrovov.kafkajuggler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class KafkaJugglerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaJugglerApplication.class, args);
    }

}
