package org.example.weatherBot.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.weatherBot.response.Response;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class WeatherRequester {
    private static final HttpClient HTTP = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @SneakyThrows
    public Response sendRequest() {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?q=Kyiv&appid=a502ae541ee8f22a703632571e292a64&lang=ru&units=metric"))
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        return new ObjectMapper().readValue(response.body(), Response.class);
    }
}

