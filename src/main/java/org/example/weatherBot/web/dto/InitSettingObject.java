package org.example.weatherBot.web.dto;

import lombok.Builder;
import lombok.Value;
import org.example.weatherBot.web.dto.init.LanguageObject;
import org.example.weatherBot.web.dto.init.LocationObject;
import org.example.weatherBot.web.dto.init.MetricsObject;

@Value
@Builder
public class InitSettingObject {
    LanguageObject language;
    LocationObject location;
    MetricsObject metrics;
}
