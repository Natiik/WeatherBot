package org.example.weatherBot.web.services;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.service.CityService;
import org.example.weatherBot.service.CountryService;
import org.example.weatherBot.service.UserService;
import org.example.weatherBot.web.dto.CountryObject;
import org.example.weatherBot.web.dto.InitSettingObject;
import org.example.weatherBot.web.dto.UpdateSettingObject;
import org.example.weatherBot.web.dto.init.LanguageObject;
import org.example.weatherBot.web.dto.init.LocationObject;
import org.example.weatherBot.web.dto.init.MetricsObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final UserService userService;
    private final CountryService countryService;
    private final CityService cityService;

    public void updateSettings(UpdateSettingObject updateSettingObject) {
        userService.insertUpdates(new UserEntity(
                updateSettingObject.getId(),
                updateSettingObject.getMetrics(),
                updateSettingObject.getLanguage(),
                updateSettingObject.getLocation()));
    }


    public InitSettingObject getInitSettings(Long id) {
        UserEntity currentUser = userService.getUserById(id);
        return InitSettingObject.builder()
                .language(LanguageObject.builder()
                        .value(currentUser.getLanguage().toString())
                        .label(currentUser.getLanguage().getLabel())
                        .build())
                .location(LocationObject.builder()
                        .city(cityService.getCityObjectById(currentUser.getLocation()))
                        .country(getCountryObject(currentUser))
                        .build())
                .metrics(MetricsObject.builder()
                        .value(currentUser.getMetrics().toString())
                        .label(currentUser.getMetrics().getLabel())
                        .build())
                .build();
    }

    private CountryObject getCountryObject(UserEntity user) {
        return countryService.getCountryObjectByShortName(cityService.getCountryNameById(user.getLocation()));
    }
}
