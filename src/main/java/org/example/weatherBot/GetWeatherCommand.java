package org.example.weatherBot;

import lombok.NonNull;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

class GetWeatherCommand extends BotCommand implements IBotCommand {
    final String identifier;

    public GetWeatherCommand(@NonNull String identifier, @NonNull String description){
        super(identifier, description);
        this.identifier =identifier;
    }

    @Override
    public String getCommandIdentifier() {
        return identifier;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {

    }
}
