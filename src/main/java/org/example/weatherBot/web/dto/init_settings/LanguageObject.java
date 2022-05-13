package org.example.weatherBot.web.dto.init_settings;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LanguageObject {
    String value;
    String label;
}
