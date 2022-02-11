package org.example.weatherBot.web.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.service.CityService;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.web.dto.SettingObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final UserService userService;
    private final CityService cityService;

    public void changeSettings(SettingObject settingObject) {
        switch (settingObject.getSettingComponent()) {
            case LANGUAGE -> {
                userService.update(
                        settingObject.getId(),
                        "language",
                        settingObject.getValue()
                );
            }
            case METRICS -> {
                userService.update(
                        settingObject.getId(),
                        "metrics",
                        settingObject.getValue());
            }
            case LOCATION -> {
                userService.update(
                        settingObject.getId(),
                        "location",
                        settingObject.getValue());
            }
        }
    }

    public void updateSettings(UserEntity userEntity){
        userService.insertUpdates(userEntity);
    }

    public List<String> getAlikeCity(String city) {
        return cityService.getAlikeCity(city);
    }
}
