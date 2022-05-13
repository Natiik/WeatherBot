package org.example.weatherBot.web.dto;

import lombok.Value;

@Value
public class UpdateSettingObject {
    Long id;
    Integer location;
    String language;
    String metrics;
}
