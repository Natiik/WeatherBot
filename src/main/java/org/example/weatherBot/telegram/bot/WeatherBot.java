package org.example.weatherBot.telegram.bot;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.weatherBot.entities.user_entity_structure.Language;
import org.example.weatherBot.requesters.OpenWeatherRequester;
import org.example.weatherBot.service.CityService;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.telegram.exeptions.UnsuitableResponseException;
import org.example.weatherBot.telegram.properties.BotProperties;
import org.example.weatherBot.telegram.services.LanguageService;
import org.example.weatherBot.telegram.services.MessageService;
import org.example.weatherBot.telegram.services.ReplyQueryService;
import org.example.weatherBot.telegram.services.ReplyTextService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final OpenWeatherRequester openWeatherRequester;
    private final BotProperties properties;
    private final UserService userService;
    private final MessageService messageService;
    private final CityService cityService;
    private final LanguageService languageService;

    private final ReplyTextService replyTextService;
    private final ReplyQueryService replyQueryService;


    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    @SneakyThrows
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                execute(replyTextService.process(update.getMessage()));
            } catch (UnsuitableResponseException e) {
                execute(replyTextService.process(update.getMessage(), e.getType()));
            }

        } else if (update.hasCallbackQuery()) {
          execute(replyQueryService.process(update.getCallbackQuery()));
        }
    }
}

