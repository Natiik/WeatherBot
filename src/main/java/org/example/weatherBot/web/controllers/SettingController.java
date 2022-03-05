package org.example.weatherBot.web.controllers;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.web.dto.SettingObject;
import org.example.weatherBot.web.services.SettingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SettingController {
    private final SettingService settingService;

    @PostMapping("/update")
    public void updateSettings(@RequestBody SettingObject settingObject){
        settingService.updateSettings(settingObject);
    }
}
