package org.example.weatherBot.requesters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.properties.RequestProperties;
import org.example.weatherBot.response.Response;
import org.example.weatherBot.service.UserService;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class WeatherRequester {

    private final UserService userService;
    private final RequestProperties properties;

    private static final HttpClient HTTP = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @SneakyThrows
    public Response sendRequest(Long id) {

        UserEntity user = userService.getUserById(id);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?id=%d&appid=%s&lang=%s&units=%s".formatted(
                        user.getLocation(),
                        properties.getAppId(),
                        user.getLanguage().toString().toLowerCase(),
                        user.getMetrics().toString().toLowerCase())))
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        return new ObjectMapper().readValue(response.body(), Response.class);
    }
}

