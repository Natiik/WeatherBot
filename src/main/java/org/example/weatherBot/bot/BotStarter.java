package org.example.weatherBot.bot;

import org.example.weatherBot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotStarter {

    public BotStarter(@Autowired WeatherBot weatherBot, @Autowired UserRepository userRepository) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(weatherBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
