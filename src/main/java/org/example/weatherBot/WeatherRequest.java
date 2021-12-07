package org.example.weatherBot;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

class WeatherRequest {
    private static HttpClient http = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    protected String sendRequest() throws IOException, InterruptedException {

        HttpRequest request= HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?q=Kyiv&appid=a502ae541ee8f22a703632571e292a64&lang=ru&units=metric"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .build();
        HttpResponse<String> response =http.send(request, HttpResponse.BodyHandlers.ofString());

        HttpHeaders headers=response.headers();
        headers.map().forEach((k,v)-> System.out.println(k+";"+v));

        System.out.println(response.statusCode());

        System.out.println(response.body());
        return response.body();
    }
}

