package org.example.weatherBot.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CountryObject {
    String value;
    String label;
}
