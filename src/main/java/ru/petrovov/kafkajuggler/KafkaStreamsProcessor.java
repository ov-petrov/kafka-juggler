package ru.petrovov.kafkajuggler;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class KafkaStreamsProcessor {
    @Autowired
    public void process(StreamsBuilder builder) {
        try (Serde<String> stringSerde = Serdes.String()) {
            KStream<String, String> chuckStream = builder.stream("chuck", Consumed.with(stringSerde, stringSerde));
            chuckStream.mapValues(v -> {
                        String longest = Stream.of(v.split("\\W+"))
                                .max(Comparator.comparingInt(String::length))
                                .orElse("");
                        return longest + " length: " + longest.length();
                    })
                    .to("chuck-longest-words", Produced.with(stringSerde, stringSerde));
        }


    }

}
