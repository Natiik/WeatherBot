package org.example.weatherBot.bot;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.weatherBot.service.MessageService;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.utility.AnswerCreator;
import org.example.weatherBot.utility.ButtonUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final WeatherRequester weatherRequester;
    private final BotProperties properties;
    private final UserService userService;
    private final MessageService messageUtil;


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
            Long id = update.getMessage().getChatId();
            SendMessage message;

            String text = update.getMessage().getText();
            if ("/get_weather".equals(text)) {
                message = messageUtil.createMessage(id, AnswerCreator.writeWeather(weatherRequester.sendRequest()));
                execute(message);
            } else if ("/start".equals(text)) {
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(List.of(List.of(
                        ButtonUtil.createButton("Change settings", "change_settings"),
                        ButtonUtil.createButton("Get weather", "get_weather"))));
                message = messageUtil.createMessageWithButton(id, AnswerCreator.getGreeting(), markupInline);
                userService.insertDefault(id);
                execute(message);
            } else if ("/change_settings".equals(text)) {
                message = messageUtil.createMessageWithButton(id, AnswerCreator.getSettingMessage(), new InlineKeyboardMarkup(List.of(List.of(
                        ButtonUtil.createButton("Metrics", "set_metrics"),
                        ButtonUtil.createButton("Location", "set_location"),
                        ButtonUtil.createButton("Language", "set_language")))));
                execute(message);
            } else if ("/picture".equals(text)) {
                execute(messageUtil.createPhotoMessage(id, weatherRequester.sendRequest()));
            } else {
                message = messageUtil.createMessage(id, "¯\\_(ツ)_/");
                execute(message);
            }

        } else if (update.hasCallbackQuery()) {
            Long id = update.getCallbackQuery().getMessage().getChatId();
            SendMessage message;
            String data = update.getCallbackQuery().getData();
            switch (data) {
                case "get_weather" -> message = messageUtil.createMessage(id, AnswerCreator.writeWeather(weatherRequester.sendRequest()));
                case "change_settings" -> {
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                    lists.add(List.of(ButtonUtil.createButton("Metrics", "set_metrics"), ButtonUtil.createButton("Location", "set_location"), ButtonUtil.createButton("Language", "set_language")));
                    markup.setKeyboard(lists);
                    message = messageUtil.createMessageWithButton(id, AnswerCreator.getSettingMessage(), markup);
                }
                case "set_metrics" -> {
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                    lists.add(List.of(ButtonUtil.createButton("Standard", "standard"), ButtonUtil.createButton("Metric", "metric"), ButtonUtil.createButton("Imperial", "imperial")));
                    markup.setKeyboard(lists);
                    message = messageUtil.createMessageWithButton(id, "Temperature is measured in: \nStandard - Kelvin \nMetric - Celsius \nImperial - Fahrenheit", markup);// TODO
                }
                case "standard" -> {
                    message = messageUtil.createMessage(id, "Metrics changed to Standard");
                    userService.update(id, "metrics", "standard");
                }
                case "metric" -> {
                    message = messageUtil.createMessage(id, "Metrics changed to Metric");
                    userService.update(id, "metrics", "metric");
                }
                case "imperial" -> {
                    message = messageUtil.createMessage(id, "Metrics changed to Imperial");
                    userService.update(id, "metrics", "imperial");
                }
                case "set_language" -> {
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                    lists.add(List.of(ButtonUtil.createButton("English", "english"), ButtonUtil.createButton("Russian", "russian"), ButtonUtil.createButton("Ukrainian", "ukrainian")));
                    markup.setKeyboard(lists);
                    message = messageUtil.createMessageWithButton(id, "Choose one language from the list of available ones", markup);
                }
                case "english" -> {
                    message = messageUtil.createMessage(id, "Language changed to English");
                    userService.update(id, "language", "en");
                }
                case "russian" -> {
                    message = messageUtil.createMessage(id, "Language changed to Russian");
                    userService.update(id, "language", "ru");
                }
                case "ukrainian" -> {
                    message = messageUtil.createMessage(id, "Language changed to Ukrainian");
                    userService.update(id, "language", "ukr");
                }
                default -> message = messageUtil.createMessage(id, "working on this feature");
            }
            execute(message);
        }
    }
}

