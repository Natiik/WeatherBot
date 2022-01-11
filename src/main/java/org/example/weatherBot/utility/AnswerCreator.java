package org.example.weatherBot.utility;

import org.example.weatherBot.response.Response;
import org.example.weatherBot.response.structure.Weather;

import java.util.stream.Collectors;

public class AnswerCreator {

   public static String writeWeather(Response response) {
        String answer = """
                Погода в городе %s на %s
                Состояние: %s
                Температура воздуха: %d°C (ощущается как %d°C)
                Влажность: %d%%
                Атмосферное давление: %d гПа
                Скорость ветра %s м/с
                Восход солнца: %s
                Заход солнца: %s"""
                .formatted(response.getName(), DateUtil.toNormal(response.getDt()), response.getWeather().stream().map(Weather::getDescription).collect(Collectors.joining(", ")), (int) Math.round(response.getMain().getTemp()), (int) Math.round(response.getMain().getFeels_like()), response.getMain().getHumidity(), response.getMain().getPressure(), response.getWind().getSpeed(), DateUtil.toNormal(response.getSys().getSunrise()), DateUtil.toNormal(response.getSys().getSunset()));
        return answer;
    }

    public static String getGreeting() {
        return "Hello! \n\nThis is WeatherBot that will help you find out about weather)\n\nDefault settings: \n\nLocation: Kyiv,Ukraine \nMeasurement system: standart (temperature :°C;  wind speed measurement: m/s)";
    }

    public static String getSettingMessage() {
        return "Choose what you`d like to change:";
    }
}
