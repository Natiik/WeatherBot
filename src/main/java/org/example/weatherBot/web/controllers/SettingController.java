package org.example.weatherBot.web.controllers;

import org.example.weatherBot.web.dto.SettingObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettingController {

    @PostMapping("/change")
    public void changeSettings(@RequestBody SettingObject settingObject) {
        System.out.println(settingObject.toString());
    }
}
