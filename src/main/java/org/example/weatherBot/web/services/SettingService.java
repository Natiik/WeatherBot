package org.example.weatherBot.web.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.web.dto.SettingObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final UserService userService;

    public void updateSettings(SettingObject settingObject) {
        userService.insertUpdates(new UserEntity(
                settingObject.getId(),
                settingObject.getMetrics(),
                settingObject.getLanguage(),
                settingObject.getLocation()));
    }

}
