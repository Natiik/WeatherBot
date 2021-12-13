package org.example.weatherBot;



import org.example.weatherBot.structure.Weather;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.Collectors;

class WeatherBot extends TelegramLongPollingCommandBot {


    @Override
    public String getBotUsername() {
        return "weather06_bot";
    }

    @Override
    public String getBotToken() {
        return "5068016198:AAHEfNgcmj3hCpiUAwHrJrd4T7kIvhuhfVw";
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/get_weather")) {
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(update.getMessage().getChatId()));
                WeatherRequest request=new WeatherRequest();
                Response response=request.sendRequest();
                String answer="Погода в городе "+ response.getName()+" на "+ DateConvertorT.toNormal(response.getDt())+
                        "\nСостояние: "+ response.getWeather().stream().map(Weather::getDescription).collect(Collectors.joining(", ")) +
                        "\nТемпература воздуха: "+ (int)Math.round(response.getMain().getTemperature())+"°C" +" (ощущается как "+ (int) Math.round(response.getMain().getFeels_like()) +"°C" +")"+
                        "\nВлажность: "+ response.getMain().getHumidity()+"%"+
                        "\nАтмосферное давление: "+ response.getMain().getPressure()+" гПа"+
                        "\nСкорость ветра "+ response.getWind().getSpeed()+" м/с"+
                        "\nВосход солнца: " + DateConvertorT.toNormal(response.getSys().getSunrise())+
                        "\nЗакат солнца: " + DateConvertorT.toNormal(response.getSys().getSunset());

                message.setText(answer);
                try {
                    execute(message);
                    WeatherRequest weatherRequest = new WeatherRequest();
                    weatherRequest.sendRequest();

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
