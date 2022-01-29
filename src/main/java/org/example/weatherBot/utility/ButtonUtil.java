package org.example.weatherBot.utility;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

public class ButtonUtil {

    public static InlineKeyboardButton createInlineButton(String text, String callBack) {
        InlineKeyboardButton newButton = new InlineKeyboardButton();
        newButton.setText(text);
        newButton.setCallbackData(callBack);
        return newButton;
    }
}
