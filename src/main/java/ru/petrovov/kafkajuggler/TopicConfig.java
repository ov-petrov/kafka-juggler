package ru.petrovov.kafkajuggler;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class TopicConfig {
    private final String name;
    private final Integer partitions;
    private final Integer replicas;
}
