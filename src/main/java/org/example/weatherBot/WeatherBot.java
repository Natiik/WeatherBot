package org.example.weatherBot;

//import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

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
                message.setText("weather forecast");
                try {
                    execute(message);
                    WeatherRequest weatherRequest = new WeatherRequest();
                    weatherRequest.sendRequest();

                } catch (TelegramApiException | IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
