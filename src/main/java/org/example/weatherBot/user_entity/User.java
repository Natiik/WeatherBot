package org.example.weatherBot.user_entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class User {
    private String id;
    private Metrics metrics;
    private Language language;
    private int locId;
}
