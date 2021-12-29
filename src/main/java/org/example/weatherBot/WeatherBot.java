package org.example.weatherBot;


import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

//import static java.lang.Math.toIntExact;

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
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String id = String.valueOf(update.getMessage().getChatId());
            if (update.getMessage().getText().equals("/get_weather")) {
                try {
                    execute(Message.createMessage(id, AnswerString.weatherCondition(weatherRequest.sendRequest())));

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (update.getMessage().getText().equals("/start")) {
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> buttonRows = new ArrayList<>();
                buttonRows.add(List.of(Button.createButton("Change settings", "change_settings"),
                                       Button.createButton("Get weather", "get_weather")));
                markupInline.setKeyboard(buttonRows);

                try {
                    execute(Message.createMessageButton(id, AnswerString.firstString(), markupInline));

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            String id = String.valueOf(update.getCallbackQuery().getMessage().getChatId());

            if (update.getCallbackQuery().getData().equals("get_weather")) {
                try {
                    execute(Message.createMessage(id, AnswerString.weatherCondition(weatherRequest.sendRequest())));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (update.getCallbackQuery().getData().equals("change_settings")) {

                try {
                    execute(Message.createMessage(id, AnswerString.weatherCondition(weatherRequest.sendRequest())));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

