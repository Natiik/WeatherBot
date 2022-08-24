package org.example.weatherBot.entities.user_entity_structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Language {
    EN("English"),
    RU("Русский"),
    UA("Українська");
    private final String label;

    public static List<Language> getAll() {
        return Arrays.stream(Language.UA.getDeclaringClass().getEnumConstants()).toList();
    }
}
