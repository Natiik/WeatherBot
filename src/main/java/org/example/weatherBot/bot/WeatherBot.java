package org.example.weatherBot.bot;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.utility.AnswerCreator;
import org.example.weatherBot.utility.ButtonUtil;
import org.example.weatherBot.utility.MessageUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherBot extends TelegramLongPollingCommandBot {

    private final WeatherRequester weatherRequester;
    private final SQL sql;
    private final BotProperties properties;
    private final UserService userService;


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
            Long id = update.getMessage().getChatId();
            SendMessage message;

            String text = update.getMessage().getText();
            if ("/get_weather".equals(text)) {
                message = MessageUtil.createMessage(id, AnswerCreator.writeWeather(weatherRequester.sendRequest()));
            } else if ("/start".equals(text)) {
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(List.of(List.of(
                        ButtonUtil.createButton("Change settings", "change_settings"),
                        ButtonUtil.createButton("Get weather", "get_weather"))));
                message = MessageUtil.createMessageWithButton(id, AnswerCreator.getGreeting(), markupInline);

                userService.insertDefault(id);
            } else {
                message = new SendMessage();
            }
            execute(message);

        } else if (update.hasCallbackQuery()) {
            Long id = update.getCallbackQuery().getMessage().getChatId();
            SendMessage message;
            String data = update.getCallbackQuery().getData();
            switch (data) {
                case "get_weather" -> message = MessageUtil.createMessage(id, AnswerCreator.writeWeather(weatherRequester.sendRequest()));
                case "change_settings" -> {
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                    lists.add(List.of(ButtonUtil.createButton("Metrics", "set_metrics"), ButtonUtil.createButton("Location", "set_location"), ButtonUtil.createButton("Language", "set_language")));
                    markup.setKeyboard(lists);
                    message = MessageUtil.createMessageWithButton(id, AnswerCreator.getSettingMessage(), markup);
                }
                case "set_metrics" -> {
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                    lists.add(List.of(ButtonUtil.createButton("Standard", "standard"), ButtonUtil.createButton("Metric", "metric"), ButtonUtil.createButton("Imperial", "imperial")));
                    markup.setKeyboard(lists);
                    message = MessageUtil.createMessageWithButton(id, "Temperature is measured in: \nStandard - Kelvin \nMetric - Celsius \nImperial - Fahrenheit", markup);// TODO
                }
                case "standard" -> {
                    message = MessageUtil.createMessage(id, "Metrics changed to Standard");
                    userService.update(id,"metrics","standard");
                }
                case "metric" -> {
                    message = MessageUtil.createMessage(id, "Metrics changed to Metric");
                    userService.update(id,"metrics","metric");
                }
                case "imperial" -> {
                    message = MessageUtil.createMessage(id, "Metrics changed to Imperial");
                    userService.update(id,"metrics","imperial");
                }
                default -> message = MessageUtil.createMessage(id, "working on this feature");
            }
            execute(message);
        }else {
            Long chatId = update.getMessage().getChatId();
            execute(MessageUtil.createMessage(chatId,"¯\\_(ツ)_/"));
        }
    }
}
