package org.example.weatherBot.utility;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class MessageUtil {

    public static SendMessage createMessage(Long id, String text) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(String.valueOf(id));
        return message;
    }

    public static SendMessage createMessageWithButton(Long id, String text, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(String.valueOf(id));
        message.setReplyMarkup(markup);
        return message;
    }
}