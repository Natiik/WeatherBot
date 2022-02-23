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

    @PostMapping("/change")
    public void changeSettings(@RequestBody SettingObject settingObject) {
        settingService.changeSettings(settingObject);
    }

//    @GetMapping("/ensure")
//    public List<String> getAlikeCity(@RequestHeader String city) {
//        return settingService.getAlikeCity(city);
//    }

    @PostMapping("/update")
    public void updateSettings(@RequestBody UserEntity userEntity){
        settingService.updateSettings(userEntity);
    }
}
