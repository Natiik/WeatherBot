package org.example.weatherBot.telegram.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.user_entity_structure.Language;
import org.example.weatherBot.service.CityService;
import org.example.weatherBot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

import static org.example.weatherBot.entities.user_entity_structure.Language.*;

@Service
@RequiredArgsConstructor
public class ReplyQueryService {
    private final UserService userService;
    private final LanguageService languageService;
    private final MessageService messageService;
    private final CityService cityService;

    public SendMessage process(CallbackQuery query) {
        Long chatId = query.getMessage().getChatId();
        Integer messageId = query.getMessage().getMessageId();
        String data = query.getData();
        Language currentLang = userService.getUserById(chatId).getLanguage();

        if ("standard".equals(data)) {
            userService.update(chatId, "metrics", "standard");
            return changeMetricsMsg(chatId, currentLang, "success_change_standard");
        } else if ("metric".equals(data)) {
            userService.update(chatId, "metrics", "metric");
            return changeMetricsMsg(chatId, currentLang, "success_change_metric");
        } else if ("imperial".equals(data)) {
            userService.update(chatId, "metrics", "imperial");
            return changeMetricsMsg(chatId, currentLang, "success_change_imperial");
        } else if ("english".equals(data)) {
            userService.update(chatId, "language", "en");
            return changeLanguageMsg(chatId, messageId, EN, "success_change_en");
        } else if ("russian".equals(data)) {
            userService.update(chatId, "language", "ru");
            return changeLanguageMsg(chatId, messageId, RU, "success_change_ru");
        } else if ("ukrainian".equals(data)) {
            userService.update(chatId, "language", "ua");
            return changeLanguageMsg(chatId, messageId, UA, "success_change_ua");
        } else if ((data != null) && (data.startsWith("/ci_"))) {
            userService.update(chatId, "location", data.substring(4));
            return changeLocationMsg(chatId, currentLang, data);
        } else if ("/none".equals(data)) {
            return noneMsg(chatId, currentLang);
        } else {
            return workingMsg(chatId, currentLang);
        }
    }

    private SendMessage workingMsg(Long chatId, Language currentLang) {
        return messageService.createMessage(
                chatId,
                languageService.getText(currentLang, "working"));
    }


    private SendMessage noneMsg(Long chatId, Language currentLang) {
        return messageService.createMessage(
                chatId,
                languageService.getText(currentLang, "none_ans"));
    }

    private SendMessage changeLocationMsg(Long chatId, Language currentLang, String data) {
        return messageService.createMessage(
                chatId,
                languageService.getText(currentLang, "success_change_location")
                        .formatted(cityService.getCityNameById(Integer.parseInt(data.substring(4))))
        );
    }

    private SendMessage changeLanguageMsg(Long chatId, Integer messageId, Language language, String message) {
        return messageService.createMessageWithMenu(
                chatId,
                languageService.getText(language, message),
                messageId,
                List.of(
                        languageService.getMenuButtonsNames(language, List.of("get_weather", "get_picture")),
                        List.of(languageService.getText(language, "change_settings"))
                ));
    }

    private SendMessage changeMetricsMsg(Long chatId, Language currentLang, String settings) {
        return messageService.createMessage(
                chatId,
                languageService.getText(currentLang, settings));
    }
}
