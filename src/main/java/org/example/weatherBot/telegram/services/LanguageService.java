package org.example.weatherBot.telegram.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.entities.user_entity_structure.Language;
import org.example.weatherBot.response.OpenWeatherResponse;
import org.example.weatherBot.response.structure.Weather;
import org.example.weatherBot.utility.DateUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageCache cache;

    public String writeWeather(OpenWeatherResponse openWeatherResponse, UserEntity user) {
        return getText(user.getLanguage(), "weather_state_" + user.getMetrics().toString().toLowerCase())
                .formatted(
                        openWeatherResponse.getName(),
                        DateUtil.toNormal(openWeatherResponse.getDt()),
                        openWeatherResponse.getWeather().stream().map(Weather::getDescription).collect(Collectors.joining(", ")),
                        (int) Math.round(openWeatherResponse.getMain().getTemp()),
                        (int) Math.round(openWeatherResponse.getMain().getFeels_like()),
                        openWeatherResponse.getMain().getHumidity(), openWeatherResponse.getMain().getPressure(),
                        openWeatherResponse.getWind().getSpeed(),
                        DateUtil.toNormalTime(openWeatherResponse.getSys().getSunrise()),
                        DateUtil.toNormalTime(openWeatherResponse.getSys().getSunset()));
    }

    public String getText(Language language, String key) {
        return cache.getTitleByLanguageAndKey(language, key);
    }

    public List<String> getMenuButtonsNames(Language language, List<String> keys) {
        return keys.stream()
                .map(key-> cache.getTitleByLanguageAndKey(language, key))
                .toList();
    }


    public Map<String, String> getInlineButtonsNames(Language language, List<String> keys) {
         return keys.stream()
                .collect(Collectors.toMap(key -> key, k->cache.getTitleByLanguageAndKey(language, k)));
    }

    public List<String> getTexts(String key){
        return cache.getTitlesByKey(key);
    }
}
