package org.example.weatherBot.web.dto;

import lombok.Value;
import org.example.weatherBot.entities.user_entity_structure.Language;
import org.example.weatherBot.entities.user_entity_structure.Metrics;

@Value
public class SettingObject {
    Long id;
    Long location;
    String language;
    String metrics;
}
