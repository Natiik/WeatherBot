package org.example.weatherBot;

import org.example.weatherBot.structure.Weather;

import java.util.stream.Collectors;

 class AnswerString {

    static String weatherCondition(Response response) {
        String answer = """
                Погода в городе %s на %s
                Состояние: %s
                Температура воздуха: %d°C (ощущается как %d°C)
                Влажность: %d%%
                Атмосферное давление: %d гПа
                Скорость ветра %s м/с
                Восход солнца: %s
                Заход солнца: %s"""
                .formatted(response.getName(), DateConvertorT.toNormal(response.getDt()), response.getWeather().stream().map(Weather::getDescription).collect(Collectors.joining(", ")), (int) Math.round(response.getMain().getTemp()), (int) Math.round(response.getMain().getFeels_like()), response.getMain().getHumidity(), response.getMain().getPressure(), response.getWind().getSpeed(), DateConvertorT.toNormal(response.getSys().getSunrise()), DateConvertorT.toNormal(response.getSys().getSunset()));
        return answer;
    }

    static  String firstString (){
        return "Hello! \n\nThis is WeatherBot that will help you find out about weather)\n\nDefault settings: \n\nLocation: Kyiv,Ukraine \nMeasurement system: standart (temperature :°C;  wind speed measurement: m/s)";
    }
}
