package org.example.weatherBot.web.controllers;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.web.dto.InitSettingObject;
import org.example.weatherBot.web.dto.UpdateSettingObject;
import org.example.weatherBot.web.services.SettingService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class SettingController {
    private final SettingService settingService;

    @PostMapping("/update")
    public void updateSettings(@RequestBody UpdateSettingObject updateSettingObject) {
        settingService.updateSettings(updateSettingObject);
    }

    @GetMapping("/init_settings/{id}")
    public InitSettingObject getInitSettings(@PathVariable Long id) {
        return settingService.getInitSettings(id);
    }
}
