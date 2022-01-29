package org.example.weatherBot.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.response.Response;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.File;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final PictureService pictureUtil;

    public SendMessage createMessage(Long id, String text) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(String.valueOf(id));
        return message;
    }

    public SendMessage createMessageWithButton(Long id, String text, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(String.valueOf(id));
        message.setReplyMarkup(markup);
        return message;
    }

    public SendPhoto createPhotoMessage(Long id, Response response) {
        pictureUtil.createSVG(id, response);
        pictureUtil.SVGtoJPG(id);
        return new SendPhoto(id.toString(), new InputFile(new File("%s.jpg".formatted(id.toString()))));
    }
}
