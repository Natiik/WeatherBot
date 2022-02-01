package org.example.weatherBot.bot;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.weatherBot.entities.user_entity_structure.Language;
import org.example.weatherBot.properties.BotProperties;
import org.example.weatherBot.service.CityService;
import org.example.weatherBot.service.MessageService;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.utility.LanguageService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final WeatherRequester weatherRequester;
    private final BotProperties properties;
    private final UserService userService;
    private final MessageService messageService;
    private final CityService cityService;
    private final LanguageService languageService;


    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    @SneakyThrows
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            int messageId = update.getMessage().getMessageId();
            String text = update.getMessage().getText();
            Language currentLang;
            if (userService.existById(chatId)) {
                 currentLang = userService.getUserById(chatId).getLanguage();
            } else{
                 currentLang= Language.EN;
            }

            if (("/get_weather".equals(text)) || languageService.getText(currentLang, "get_weather").equals(text)) {
                execute(messageService.createMessageWithMenu(
                        chatId,
                        languageService.writeWeather(weatherRequester.sendRequest(chatId), userService.getUserById(chatId)),
                        messageId,
                        List.of(
                                languageService.getMenuButtonsNames(currentLang, List.of("get_weather", "get_picture")),
                                List.of(languageService.getText(currentLang, "change_settings"))
                        )));
            } else if ("/start".equals(text)) {
                userService.insertDefault(chatId);
                execute(messageService.createMessageWithMenu(
                        chatId,
                        languageService.getText(currentLang, "greeting"),
                        messageId,
                        List.of(
                                languageService.getMenuButtonsNames(currentLang, List.of("get_weather", "get_picture")),
                                List.of(languageService.getText(currentLang, "change_settings"))
                        )));
            } else if (("/change_settings".equals(text)) || (languageService.getText(currentLang, "change_settings").equals(text))) {
                execute(messageService.createMessageWithMenu(
                        chatId,
                        languageService.getText(currentLang, "settings"),
                        messageId,
                        List.of(
                                languageService.getMenuButtonsNames(currentLang, List.of("language", "metrics", "location")),
                                List.of(languageService.getText(currentLang, "return_menu"))
                        )));
            } else if (("/picture".equals(text)) || (languageService.getText(currentLang, "get_picture").equals(text))) {
                execute(messageService.createPhotoMessage(
                        chatId,
                        weatherRequester.sendRequest(chatId)));
            } else if (languageService.getText(currentLang, "return_menu").equals(text)) {
                execute(messageService.createMessageWithMenu(
                        chatId,
                        languageService.getText(currentLang, "menu"),
                        messageId,
                        List.of(
                                languageService.getMenuButtonsNames(currentLang, List.of("get_weather", "get_picture")),
                                List.of(languageService.getText(currentLang, "change_settings"))
                        )));
            } else if (languageService.getText(currentLang, "language").equals(text)) {
                execute(messageService.createMessageWithButton(
                        chatId,
                        languageService.getText(currentLang, "location_settings"),
                        List.of(languageService.getInlineButtonsNames(currentLang, List.of("english", "russian", "ukrainian"))))
                );
            } else if (languageService.getText(currentLang, "metrics").equals(text)) {
                execute(messageService.createMessageWithButton(
                        chatId,
                        languageService.getText(currentLang, "metrics_settings"),
                        List.of(languageService.getInlineButtonsNames(currentLang, List.of("standard", "metric", "imperial"))))
                );
            } else if (languageService.getText(currentLang, "location").equals(text)) {
                // TODO think of better way
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "command_location_setting"))
                );
            } else if ((text != null) && (text.startsWith("="))) {
                Map<String, String> similarCities = cityService.findSimilarCities(text.substring(1));
                if (similarCities.size() > 5) {
                    execute(messageService.createMessage(
                            chatId,
                            languageService.getText(currentLang, "too_many_locations_found"))
                    );
                } else {
                    execute(messageService.createMessageWithButton(
                            chatId,
                            languageService.getText(currentLang, "select_location").formatted(similarCities.size()),
                            List.of(
                                    similarCities,
                                    languageService.getInlineButtonsNames(currentLang, List.of("/none")))
                    ));
                }
            } else {
                execute(messageService.createMessage(
                        chatId,
                        "¯\\_(ツ)_/")
                );
            }

        } else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            Language currentLang = userService.getUserById(chatId).getLanguage();

            if ("standard".equals(data)) {
                userService.update(chatId, "metrics", "standard");
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "success_change_standard"))
                );
            } else if ("metric".equals(data)) {
                userService.update(chatId, "metrics", "metric");
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "success_change_metric"))
                );
            } else if ("imperial".equals(data)) {
                userService.update(chatId, "metrics", "imperial");
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "success_change_imperial"))
                );
            } else if ("english".equals(data)) {
                userService.update(chatId, "language", "en");
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "success_change_en"))
                );
            } else if ("russian".equals(data)) {
                userService.update(chatId, "language", "ru");
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "success_change_ru"))
                );
            } else if ("ukrainian".equals(data)) {
                userService.update(chatId, "language", "ua");
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "success_change_ua"))
                );
            } else if ((data != null) && (data.startsWith("/ci_"))) {
                userService.update(chatId, "location", data.substring(4));
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "success_change_location")
                                .formatted(cityService.getCityNameById(Integer.parseInt(data.substring(4))))
                ));
            } else if ("/none".equals(data)) {
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "none_ans"))
                );
            } else {
                execute(messageService.createMessage(
                        chatId,
                        languageService.getText(currentLang, "working"))
                );
            }
        }
    }
}

