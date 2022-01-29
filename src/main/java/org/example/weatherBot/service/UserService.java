package org.example.weatherBot.service;

import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.entities.entity_structure.Language;
import org.example.weatherBot.entities.entity_structure.Metrics;
import org.example.weatherBot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertDefault(Long id) {
        if (!userRepository.existsById(id)) {
            userRepository.save(new UserEntity(id, "metric", "ru", 703448L));
        }
    }

    public void update(Long id, String column, String value) {
        UserEntity user = userRepository.getById(id);
        switch (column) {
            case "metrics" -> {
                user.setMetrics(Metrics.valueOf(value.toUpperCase()));
            }
            case "language" -> {
                user.setLanguage(Language.valueOf(value.toUpperCase()));
            }
            case "location" -> {
                user.setLocation(Long.parseLong(value));
            }
        }
        userRepository.save(user);
    }
}
