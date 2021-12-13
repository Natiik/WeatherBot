package org.example.weatherBot;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

class WeatherRequest {
    private static HttpClient http = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    protected void sendRequest() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?q=Kyiv&appid=a502ae541ee8f22a703632571e292a64&lang=ru&units=metric"))
                .header("Accept", "application/json")
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        HashMap<String, String> result =
                (HashMap<String, String>) new ObjectMapper().readValue(response.body(), HashMap.class);

        getDescription(result);
//        System.out.println( result.get("weather"));
//        System.out.println(response.statusCode());

    }

    protected List<String> getDescription(HashMap<String, String> map) {
        String description = map.get("weather");
        System.out.println(description);
        String[] sing = {"\\[", "\\{", "]", "}"};
        for (String value : sing) {
            description = description.replaceAll(value, "");
        }
        String[] subString = description.split(", ");
        List<String> weather = new ArrayList<>();
        Arrays.stream(subString).forEach(s -> {
            if (s.startsWith("description=")) {
                weather.add(s.replaceAll("description=", ""));
            }
        });
        return weather;

    }
}

