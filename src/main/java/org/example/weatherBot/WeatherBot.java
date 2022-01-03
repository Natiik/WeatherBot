package org.example.weatherBot;


import lombok.SneakyThrows;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


class WeatherBot extends TelegramLongPollingCommandBot {

    private final WeatherRequest weatherRequest;

    public WeatherBot(WeatherRequest weatherRequest) {
        this.weatherRequest = weatherRequest;
    }


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

            if (update.getMessage().getText().equals("/get_weather")) {
                message = Message.createMessage(id, AnswerString.weatherCondition(weatherRequest.sendRequest()));
            }

            else if (update.getMessage().getText().equals("/start")) {
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
                buttonRows.add(List.of(Button.createButton("Change settings", "change_settings"),
                        Button.createButton("Get weather", "get_weather")));
                markupInline.setKeyboard(buttonRows);
                message = Message.createMessageButton(id, AnswerString.firstString(), markupInline);

                SQL.insertDefault(id);
            }

            else {
                message = new SendMessage();
            }
            execute(message);

        } else if (update.hasCallbackQuery()) {
            String id = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            SendMessage message;

            if (update.getCallbackQuery().getData().equals("get_weather")) {
                message = Message.createMessage(id, AnswerString.weatherCondition(weatherRequest.sendRequest()));
            }

            else if (update.getCallbackQuery().getData().equals("change_settings")) {
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                lists.add(List.of(Button.createButton("Metrics", "set_metrics"), Button.createButton("Location", "set_location"), Button.createButton("Language", "set_language")));
                markup.setKeyboard(lists);
                message = Message.createMessageButton(id, AnswerString.settingsString(), markup);
            }



            else {
                message = new SendMessage();
            }
            execute(message);
        }
    }
}

