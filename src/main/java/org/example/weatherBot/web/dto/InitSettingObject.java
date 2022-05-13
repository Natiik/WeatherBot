package org.example.weatherBot.web.dto;

import lombok.Builder;
import lombok.Value;
import org.example.weatherBot.web.dto.init_settings.LanguageObject;
import org.example.weatherBot.web.dto.init_settings.LocationObject;

@Value
@Builder
public class InitSettingObject {
    LanguageObject language;
    LocationObject location;
    String metrics;
}
