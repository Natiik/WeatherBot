package org.example.weatherBot.bot;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.weatherBot.service.MessageService;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.utility.AnswerCreator;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final WeatherRequester weatherRequester;
    private final BotProperties properties;
    private final UserService userService;
    private final MessageService messageService;


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
            Long chatId = update.getMessage().getChatId();
            int messageId = update.getMessage().getMessageId();

            String text = update.getMessage().getText();
            if (("/get_weather".equals(text)) || ("Get weather").equals(text)) {
                execute(messageService.createMessageWithMenu(chatId, AnswerCreator.writeWeather(weatherRequester.sendRequest()), messageId, List.of(List.of("Get weather", "Get picture"),
                        List.of("Change settings"))));
            } else if ("/start".equals(text)) {
                userService.insertDefault(chatId);
                execute(messageService.createMessageWithMenu(chatId, AnswerCreator.getGreeting(), messageId, List.of(List.of("Get weather", "Get picture"),
                        List.of("Change settings"))));
            } else if (("/change_settings".equals(text)) || ("Change settings".equals(text))) {
                execute(messageService.createMessageWithMenu(chatId, AnswerCreator.getSettingMessage(), messageId, List.of(List.of("Language", "Metrics", "Location"),
                        List.of("Return to menu"))));
            } else if (("/picture".equals(text)) || ("Get picture").equals(text)) {
                execute(messageService.createPhotoMessage(chatId, weatherRequester.sendRequest()));
            } else if ("Return to menu".equals(text)) {
                execute(messageService.createMessageWithMenu(chatId, "Menu", messageId, List.of(List.of("Get weather", "Get picture"),
                        List.of("Change settings"))));
            } else if ("Language".equals(text)) {
                execute(messageService.createMessageWithButton(chatId,
                        "Choose one language from the list of available ones",
                        List.of(Map.of("english", "English", "russian", "Russian", "ukrainian", "Ukrainian"))));
            } else if ("Metrics".equals(text)) {
                // TODO make response in other languages
                execute(messageService.createMessageWithButton(chatId,
                        "Temperature is measured in: \nStandard - Kelvin \nMetric - Celsius \nImperial - Fahrenheit",
                        List.of(Map.of("standard", "Standard", "metric", "Metric", "imperial", "Imperial"))));
            } else {
                execute(messageService.createMessage(chatId, "¯\\_(ツ)_/"));
            }

        } else if (update.hasCallbackQuery()) {
            Long id = update.getCallbackQuery().getMessage().getChatId();
            SendMessage message;
            String data = update.getCallbackQuery().getData();
            switch (data) {
                case "standard" -> {
                    message = messageService.createMessage(id, "Metrics changed to Standard");
                    userService.update(id, "metrics", "standard");
                }
                case "metric" -> {
                    message = messageService.createMessage(id, "Metrics changed to Metric");
                    userService.update(id, "metrics", "metric");
                }
                case "imperial" -> {
                    message = messageService.createMessage(id, "Metrics changed to Imperial");
                    userService.update(id, "metrics", "imperial");
                }
                case "english" -> {
                    message = messageService.createMessage(id, "Language changed to English");
                    userService.update(id, "language", "en");
                }
                case "russian" -> {
                    message = messageService.createMessage(id, "Language changed to Russian");
                    userService.update(id, "language", "ru");
                }
                case "ukrainian" -> {
                    message = messageService.createMessage(id, "Language changed to Ukrainian");
                    userService.update(id, "language", "ukr");
                }
                default -> message = messageService.createMessage(id, "working on this feature");
            }
            execute(message);
        }
    }
}

