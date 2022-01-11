package org.example.weatherBot.bot;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.weatherBot.utility.AnswerCreator;
import org.example.weatherBot.utility.ButtonUtil;
import org.example.weatherBot.utility.MessageUtil;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final WeatherRequester weatherRequester;
    private final SQL sql;

    @Override
    public String getBotUsername() {
        return "weather06_bot";
    }

    @Override
    public String getBotToken() {
        return "5068016198:AAHEfNgcmj3hCpiUAwHrJrd4T7kIvhuhfVw";
    }

    @Override
    @SneakyThrows
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String id = String.valueOf(update.getMessage().getChatId());
            SendMessage message;

            String text = update.getMessage().getText();
            if ("/get_weather".equals(text)) {
                message = MessageUtil.createMessage(id, AnswerCreator.writeWeather(weatherRequester.sendRequest()));
            } else if ("/start".equals(text)) {
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(List.of(List.of(
                        ButtonUtil.createButton("Change settings", "change_settings"),
                        ButtonUtil.createButton("Get weather", "get_weather"))));
                message = MessageUtil.createMessageWithButton(id, AnswerCreator.getGreeting(), markupInline);

                sql.insertDefault(id);
            } else {
                message = new SendMessage();
            }
            execute(message);

        } else if (update.hasCallbackQuery()) {
            String id = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            SendMessage message;
            String data = update.getCallbackQuery().getData();
            switch (data) {
                case "get_weather" -> message = MessageUtil.createMessage(id, AnswerCreator.writeWeather(weatherRequester.sendRequest()));
                case "change_settings" -> {
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                    lists.add(List.of(ButtonUtil.createButton("Metrics", "set_metrics"), ButtonUtil.createButton("Location", "set_location"), ButtonUtil.createButton("Language", "set_language")));
                    markup.setKeyboard(lists);
                    message = MessageUtil.createMessageWithButton(id, AnswerCreator.getSettingMessage(), markup);
                }
                case "set_metrics" -> {
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                    lists.add(List.of(ButtonUtil.createButton("Standard", "standard"), ButtonUtil.createButton("Metric", "metric"), ButtonUtil.createButton("Imperial", "imperial")));
                    markup.setKeyboard(lists);
                    message = MessageUtil.createMessageWithButton(id, "Temperature is measured in: \nStandard - Kelvin \nMetric - Celsius \nImperial - Fahrenheit", markup);// TODO
                }
                case "standard" -> {
                    message = MessageUtil.createMessage(id, "Metrics changed to Standard");
                    sql.update(id, "Metrics", "standard");
                }
                case "metric" -> {
                    message = MessageUtil.createMessage(id, "Metrics changed to Metric");
                    sql.update(id, "Metrics", "metric");
                }
                case "imperial" -> {
                    message = MessageUtil.createMessage(id, "Metrics changed to Imperial");
                    sql.update(id, "Metrics", "imperial");
                }
                default -> message = MessageUtil.createMessage(id, "working on this feature");
            }
            execute(message);
        }
    }
}

