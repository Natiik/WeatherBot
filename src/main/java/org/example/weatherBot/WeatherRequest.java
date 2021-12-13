package org.example.weatherBot;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        System.out.println(response.body());
        Response result =
                 new ObjectMapper().readValue(response.body(), Response.class);
        System.out.println("");
        //getDescription(result);
//        System.out.println( result.get("weather"));
//        System.out.println(response.statusCode());

    }

    /*protected List<String> getDescription(HashMap map)  {
        return ((ArrayList) map.get("weather")).stream()
                .map(el -> ((LinkedHashMap)el).get("description"))
                .toList();
    }*/
}

