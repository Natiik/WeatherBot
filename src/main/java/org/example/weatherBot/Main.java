package org.example.weatherBot;


import org.example.weatherBot.bot.SQL;
import org.example.weatherBot.bot.WeatherBot;
import org.example.weatherBot.bot.WeatherRequester;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new WeatherBot(new WeatherRequester(), new SQL()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
