package org.example.weatherBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

class Message {

     static SendMessage createMessage(String id, String text) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(id);
        return message;
    }

     static SendMessage createMessageButton(String id, String text, InlineKeyboardMarkup markup) {
         SendMessage message = new SendMessage();
         message.setText(text);
         message.setChatId(id);
         message.setReplyMarkup(markup);
         return message;
     }
}
