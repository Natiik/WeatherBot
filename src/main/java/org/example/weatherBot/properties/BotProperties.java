package org.example.weatherBot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BotProperties {
    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

}
