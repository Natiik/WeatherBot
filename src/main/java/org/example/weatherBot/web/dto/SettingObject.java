package org.example.weatherBot.web.dto;

import lombok.Value;

@Value
public class SettingObject {
    Long id;
    SettingComponent settingComponent;
    String value;
}