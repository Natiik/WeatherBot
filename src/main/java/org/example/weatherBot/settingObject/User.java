package org.example.weatherBot.settingObject;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    String Id;
    Metrics metrics;
    Language language;
    int loc_id;

    public User(String id, Metrics metrics, Language language, int loc_id) {
        Id = id;
        this.metrics = metrics;
        this.language = language;
        this.loc_id = loc_id;
    }
}
