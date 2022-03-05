package org.example.weatherBot.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CityObject {
    Integer Id;
    String name;
}
