package org.example.weatherBot.service;

import org.example.weatherBot.entities.Setting;
import org.example.weatherBot.repository.UserRepository;
import org.example.weatherBot.user_entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class UserService  {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertDefault (Long id){
        if(!userRepository.existsById(id)){
        userRepository.save (new Setting(id, "metric", "ru", 703448L));}
    }
    public void update(Long id,String column, String value) {
        Setting user = userRepository.getById(id);
        switch (column){
            case "metrics"-> {user.setMetrics(value);}
            case "language"->{user.setLanguage(value);}
            case "location"->{user.setLocation(Long.parseLong(value));}
        }
        userRepository.deleteById(id);
        userRepository.save(user);
    }
}
