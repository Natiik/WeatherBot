package org.example.weatherBot.bot;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.weatherBot.service.CityService;
import org.example.weatherBot.service.MessageService;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.utility.AnswerCreator;
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
            if (("/get_weather".equals(text)) || ("Get weather").equals(text)) {
                execute(messageService.createMessageWithMenu(chatId, AnswerCreator.writeWeather(weatherRequester.sendRequest()), messageId, List.of(List.of("Get weather", "Get picture"),
                        List.of("Change settings"))));
            } else if ("/start".equals(text)) {
                userService.insertDefault(chatId);
                execute(messageService.createMessageWithMenu(chatId, AnswerCreator.getGreeting(), messageId, List.of(List.of("Get weather", "Get picture"),
                        List.of("Change settings"))));
            } else if (("/change_settings".equals(text)) || ("Change settings".equals(text))) {
                execute(messageService.createMessageWithMenu(chatId, AnswerCreator.getSettingMessage(), messageId, List.of(List.of("Language", "Metrics", "Location"),
                        List.of("Return to menu"))));
            } else if (("/picture".equals(text)) || ("Get picture").equals(text)) {
                execute(messageService.createPhotoMessage(chatId, weatherRequester.sendRequest()));
            } else if ("Return to menu".equals(text)) {
                execute(messageService.createMessageWithMenu(chatId, "Menu", messageId, List.of(List.of("Get weather", "Get picture"),
                        List.of("Change settings"))));
            } else if ("Language".equals(text)) {
                execute(messageService.createMessageWithButton(chatId,
                        "Choose one language from the list of available ones",
                        List.of(Map.of("english", "English", "russian", "Russian", "ukrainian", "Ukrainian"))));
            } else if ("Metrics".equals(text)) {
                // TODO make response in other languages
                execute(messageService.createMessageWithButton(chatId,
                        "Temperature is measured in: \nStandard - Kelvin \nMetric - Celsius \nImperial - Fahrenheit",
                        List.of(Map.of("standard", "Standard", "metric", "Metric", "imperial", "Imperial"))));
            } else if ("Location".equals(text)) {
                // TODO think of better way
                execute(messageService.createMessage(chatId, "Type your location starting with ="));
            } else if ((text != null) && (text.startsWith("="))) {
                Map<String, String> similarCities = cityService.findSimilarCities(text.substring(1));
                if (similarCities.size() > 5) {
                    execute(messageService.createMessage(chatId, "Found too many results. Specify your request"));
                } else {
                    execute(messageService.createMessageWithButton(
                            chatId,
                            "We found %d location by your request. Select yours:".formatted(similarCities.size()),
                            List.of(
                                    similarCities,
                                    Map.of("/none", "None of suggested"))
                    ));
                }
            } else {
                execute(messageService.createMessage(chatId, "¯\\_(ツ)_/"));
            }

        } else if (update.hasCallbackQuery()) {
            Long id = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            if ("standard".equals(data)) {
                userService.update(id, "metrics", "standard");
                execute(messageService.createMessage(id, "Metrics changed to Standard"));
            } else if ("metric".equals(data)) {
                userService.update(id, "metrics", "metric");
                execute(messageService.createMessage(id, "Metrics changed to Metric"));
            } else if ("imperial".equals(data)) {
                userService.update(id, "metrics", "imperial");
                execute(messageService.createMessage(id, "Metrics changed to Imperial"));
            } else if ("english".equals(data)) {
                userService.update(id, "language", "en");
                execute(messageService.createMessage(id, "Language changed to English"));
            } else if ("russian".equals(data)) {
                userService.update(id, "language", "ru");
                execute(messageService.createMessage(id, "Language changed to Russian"));
            } else if ("ukrainian".equals(data)) {
                userService.update(id, "language", "ukr");
                execute(messageService.createMessage(id, "Language changed to Ukrainian"));
            } else if ((data != null) && (data.startsWith("/ci_"))) {
                userService.update(id, "location", data.substring(4));
                execute(messageService.createMessage(
                        id,
                        "Location successfully changed to %s"
                                .formatted(cityService.getCityNameById(Integer.parseInt(data.substring(4))))
                ));
            } else if ("/none".equals(data)) {
                execute(messageService.createMessage(id, "Sorry not sorry"));
            } else {
                execute(messageService.createMessage(id, "working on this feature"));
            }
        }
    }
}

