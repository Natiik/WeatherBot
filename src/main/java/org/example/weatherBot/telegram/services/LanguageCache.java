package org.example.weatherBot.telegram.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.weatherBot.entities.user_entity_structure.Language;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LanguageCache {
    private final Map<Language, Map<String, String>> languages = new HashMap<>();

    private static final String FILE_TEMPLATE = "src/main/resources/message_strings/%s.json";

    public Map<String, String> getLanguage(Language language) {
        if (languages.get(language) != null) {
            return languages.get(language);
        }
        addNew(language);
        return languages.get(language);
    }

    private void addNew(Language language) {
        try {
            Map<String, String> currentLanguage = new ObjectMapper().readValue(new File(FILE_TEMPLATE.formatted(language.toString().toLowerCase())),
                    new TypeReference<>() {
                    });
            languages.put(language, currentLanguage);
        } catch (Exception e) {
            log.error("FAILED TO READ LANGUAGE FILE {}", language, e);
            throw new RuntimeException(e);
        }
    }

    public String getTitleByLanguageAndKey(Language language, String key) {
        String title = getLanguage(language).get(key);
        return title != null ? title : "";
    }

    public List<String> getTitlesByKey(String key) {
        return Language.getAll()
                .stream()
                .map(language -> getTitleByLanguageAndKey(language, key))
                .toList();
    }
}
