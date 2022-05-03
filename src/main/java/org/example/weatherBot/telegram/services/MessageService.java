package org.example.weatherBot.telegram.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.response.OpenWeatherResponse;
import org.example.weatherBot.telegram.util.ButtonUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final PictureService pictureService;

    public SendMessage createMessage(Long id, String text) {
        return createMessage(id, text, false);
    }

    public SendMessage createMessageWithMarkDown(Long id, String text) {
        return createMessage(id, text, true);
    }

    private SendMessage createMessage(Long id, String text, boolean markDown) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.enableMarkdown(markDown);
        message.setChatId(String.valueOf(id));
        return message;
    }

    public SendMessage createMessageWithButton(Long id, String text, List<Map<String, String>> namesLists) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(String.valueOf(id));
        message.setReplyMarkup(createInlineMarkup(namesLists));
        return message;
    }

    public SendPhoto createPhotoMessage(Long id, OpenWeatherResponse openWeatherResponse, UserEntity user) {
        pictureService.createSVG(id, openWeatherResponse, user.getMetrics());
        pictureService.SVGtoJPG(id);
        return new SendPhoto(id.toString(), new InputFile(new File("%s.jpg".formatted(id.toString()))));
    }

    public SendMessage createMessageWithMenu(Long id, String text, int messageId, List<List<String>> names) {
        SendMessage message = new SendMessage();
        ReplyKeyboardMarkup markup = createReplyMarkup(names);
        markup.setOneTimeKeyboard(false);
        message.setChatId(id.toString());
        message.setText(text);
        message.setReplyToMessageId(messageId);
        message.setReplyMarkup(markup);
        return message;
    }


    private ReplyKeyboardMarkup createReplyMarkup(List<List<String>> nameLists) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        for (List<String> names : nameLists) {
            KeyboardRow row = new KeyboardRow();
            row.addAll(names);
            keyboardRows.add(row);
        }
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(keyboardRows);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        return markup;
    }

    private InlineKeyboardMarkup createInlineMarkup(List<Map<String, String>> namesLists) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (Map<String, String> names : namesLists) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            names.forEach((key, value) ->
                    row.add(ButtonUtil.createInlineButton(value, key)));
            rows.add(row);
        }
        return new InlineKeyboardMarkup(rows);
    }

    public SendMessage testButton(Long id, String text, int messageId) {
        SendMessage message = new SendMessage();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Location");
        keyboardButton.setRequestLocation(true);
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(List.of(new KeyboardRow(List.of(keyboardButton))));
        message.setChatId(id.toString());
        message.setText(text);
        message.setReplyToMessageId(messageId);
        message.setReplyMarkup(markup);
        return message;
    }
}
