package org.example.weatherBot.entities.user_entity_structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Language {
    EN("English"),
    RU("Русский"),
    UA("Українська");
    private final String label;
}
