package org.example.weatherBot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

class Button {

    protected static InlineKeyboardButton createButton(String text, String callBack) {
        InlineKeyboardButton newButton = new InlineKeyboardButton();
        newButton.setText(text);
        newButton.setCallbackData(callBack);
        return newButton;
    }

}
