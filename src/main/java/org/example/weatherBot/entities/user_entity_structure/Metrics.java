package org.example.weatherBot.entities.user_entity_structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Metrics {
    STANDARD("K", "m/s"),
    METRIC("C", "m/s"),
    IMPERIAL("F", "m/h");

    private final String temperature;
    private final String speed;
}
