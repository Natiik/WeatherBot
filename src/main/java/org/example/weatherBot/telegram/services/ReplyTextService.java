package org.example.weatherBot.telegram.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.user_entity_structure.Language;
import org.example.weatherBot.requesters.OpenWeatherRequester;
import org.example.weatherBot.service.CityService;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.telegram.exeptions.UnsuitableResponseException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Map;

import static org.example.weatherBot.telegram.services.MessageType.photo;

@RequiredArgsConstructor
@Service
public class ReplyTextService {
    private final UserService userService;
    private final MessageService messageService;
    private final LanguageService languageService;
    private final OpenWeatherRequester openWeatherRequester;
    private final CityService cityService;


    public SendMessage process(Message message) throws UnsuitableResponseException {
        Long chatId = message.getChatId();
        int messageId = message.getMessageId();
        String text = message.getText();
        userService.insertDefault(chatId);
        Language currentLang = userService.getUserById(chatId).getLanguage();

        if (("/get_weather".equals(text)) || languageService.getText(currentLang, "get_weather").equals(text)) {
            return processGetWeatherMsg(chatId, messageId, currentLang);
        } else if ("/start".equals(text)) {
            return processStartMsg(chatId, messageId, currentLang);
        } else if (("/change_settings".equals(text)) || (languageService.getText(currentLang, "change_settings").equals(text))) {
            return processChangeSettingsMsg(chatId, messageId, currentLang);
        } else if (("/picture".equals(text)) || (languageService.getText(currentLang, "get_picture").equals(text))) {
            throw new UnsuitableResponseException(photo);
        } else if ("/id".equals(text)) {
            return processIdMessage(chatId);
        } else if (languageService.getText(currentLang, "return_menu").equals(text)) {
            return processMenuMsg(chatId, messageId, currentLang);
        } else if (languageService.getText(currentLang, "language").equals(text)) {
            return processLanguageMsg(chatId, currentLang);
        } else if (languageService.getText(currentLang, "metrics").equals(text)) {
            return processMetricsMsg(chatId, currentLang);
        } else if (languageService.getText(currentLang, "location").equals(text)) {
            // TODO think of better way
            return processLocationMsg(chatId, currentLang);
        } else if ((text != null) && (text.startsWith("="))) {
            return processFindLocationMsg(chatId, currentLang, text);
        }
        return unknownMsg(chatId);
    }

    private SendMessage unknownMsg(Long chatId) {
        return messageService.createMessage(
                chatId,
                "¯\\_(ツ)_/¯");
    }

    private SendMessage processFindLocationMsg(Long chatId, Language currentLang, String name) {
        Map<String, String> similarCities = cityService.findSimilarCities(name.substring(1));
        if (similarCities.size() > 5) {
            return messageService.createMessage(
                    chatId,
                    languageService.getText(currentLang, "too_many_locations_found"));
        }
        return messageService.createMessageWithButton(
                chatId,
                languageService.getText(currentLang, "select_location").formatted(similarCities.size()),
                List.of(
                        similarCities,
                        languageService.getInlineButtonsNames(currentLang, List.of("/none")))
        );
    }

    private SendMessage processLocationMsg(Long chatId, Language currentLang) {
        return messageService.createMessage(
                chatId,
                languageService.getText(currentLang, "command_location_setting"));
    }

    private SendMessage processMetricsMsg(Long chatId, Language currentLang) {
        return messageService.createMessageWithButton(
                chatId,
                languageService.getText(currentLang, "metrics_settings"),
                List.of(languageService.getInlineButtonsNames(currentLang, List.of("standard", "metric", "imperial"))));
    }

    private SendMessage processLanguageMsg(Long chatId, Language currentLang) {
        return messageService.createMessageWithButton(
                chatId,
                languageService.getText(currentLang, "location_settings"),
                List.of(languageService.getInlineButtonsNames(currentLang, List.of("english", "russian", "ukrainian"))));
    }

    private SendMessage processMenuMsg(Long chatId, int messageId, Language currentLang) {
        return messageService.createMessageWithMenu(
                chatId,
                languageService.getText(currentLang, "menu"),
                messageId,
                List.of(
                        languageService.getMenuButtonsNames(currentLang, List.of("get_weather", "get_picture")),
                        List.of(languageService.getText(currentLang, "change_settings"))));
    }

    private SendMessage processIdMessage(Long chatId) {
        return messageService.createMessageWithMarkDown(chatId, "Your id `" + chatId + "`");
    }

    public SendPhoto process(Message message, MessageType type) {
        if (type == photo) {
            return processPictureMsg(message.getChatId());
        }
        return new SendPhoto(); // todo
    }

    private SendPhoto processPictureMsg(Long chatId) {
        return messageService.createPhotoMessage(
                chatId,
                openWeatherRequester.sendRequest(chatId),
                userService.getUserById(chatId)
        );
    }

    private SendMessage processChangeSettingsMsg(Long chatId, int messageId, Language currentLang) {
        return messageService.createMessageWithMenu(
                chatId,
                languageService.getText(currentLang, "settings"),
                messageId,
                List.of(
                        languageService.getMenuButtonsNames(currentLang, List.of("language", "metrics", "location")),
                        List.of(languageService.getText(currentLang, "return_menu"))
                ));
    }

    private SendMessage processStartMsg(Long chatId, int messageId, Language currentLang) {
        return messageService.createMessageWithMenu(
                chatId,
                languageService.getText(currentLang, "greeting"),
                messageId,
                List.of(
                        languageService.getMenuButtonsNames(currentLang, List.of("get_weather", "get_picture")),
                        List.of(languageService.getText(currentLang, "change_settings"))
                ));
    }

    private SendMessage processGetWeatherMsg(Long chatId, int messageId, Language currentLang) {
        return messageService.createMessageWithMenu(
                chatId,
                languageService.writeWeather(openWeatherRequester.sendRequest(chatId), userService.getUserById(chatId)),
                messageId,
                List.of(
                        languageService.getMenuButtonsNames(currentLang, List.of("get_weather", "get_picture")),
                        List.of(languageService.getText(currentLang, "change_settings"))
                ));
    }
}
