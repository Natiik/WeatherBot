package org.example.weatherBot.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.entities.user_entity_structure.Language;
import org.example.weatherBot.response.Response;
import org.example.weatherBot.response.structure.Weather;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    public String writeWeather(Response response, UserEntity user) {
        return getText(user.getLanguage(), "weather_state_" + user.getMetrics().toString().toLowerCase())
                .formatted(
                        response.getName(),
                        DateUtil.toNormal(response.getDt()),
                        response.getWeather().stream().map(Weather::getDescription).collect(Collectors.joining(", ")),
                        (int) Math.round(response.getMain().getTemp()),
                        (int) Math.round(response.getMain().getFeels_like()),
                        response.getMain().getHumidity(), response.getMain().getPressure(),
                        response.getWind().getSpeed(),
                        DateUtil.toNormalTime(response.getSys().getSunrise()),
                        DateUtil.toNormalTime(response.getSys().getSunset()));
    }


    @SneakyThrows
    public String getText(Language language, String key) {
        return new ObjectMapper()
                .readValue(new File("src/main/resources/message_strings/%s.json".formatted(language.toString().toLowerCase())), HashMap.class)
                .get(key)
                .toString();
    }

    @SneakyThrows
    public List<String> getMenuButtonsNames(Language language, List<String> keys) {
        HashMap<String, String> hashMap = new ObjectMapper()
                .readValue(new File("src/main/resources/message_strings/%s.json".formatted(language.toString().toLowerCase())), HashMap.class);
        return keys.stream()
                .map(hashMap::get)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public Map<String, String> getInlineButtonsNames(Language language, List<String> keys) {
        HashMap<String, String> hashMap = new ObjectMapper()
                .readValue(new File("src/main/resources/message_strings/%s.json".formatted(language.toString().toLowerCase())), HashMap.class);
        return keys.stream()
                .collect(Collectors.toMap(key -> key, hashMap::get));
    }
}
