package org.example.weatherBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeatherBot extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "weather06_bot";
    }

    @Override
    public String getBotToken() {
        return "5068016198:AAHEfNgcmj3hCpiUAwHrJrd4T7kIvhuhfVw";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText(update.getMessage().getText());
            InlineKeyboardMarkup inlineKey = new InlineKeyboardMarkup();
            //List<List<InlineKeyboardButton>> lists = new ArrayList<>();
            List<InlineKeyboardButton> list = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("Say text");
            button.setCallbackData("type");
            list.add(button);
            inlineKey.setKeyboard(Collections.singletonList(list));
            //inlineKey.setKeyboard(lists);
            message.setReplyMarkup(inlineKey);
            try {
                execute(message);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
