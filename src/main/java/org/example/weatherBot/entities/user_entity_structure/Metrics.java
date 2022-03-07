package org.example.weatherBot.entities.user_entity_structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Metrics {
    STANDARD("K", "m/s","Standard"),
    METRIC("C", "m/s","Metric"),
    IMPERIAL("F", "m/h","Imperial");

    private final String temperature;
    private final String speed;
    private final String label;
}
