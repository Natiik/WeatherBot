package org.example.weatherBot.service;

import org.example.weatherBot.entities.UserEntity;
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
            userRepository.save(new UserEntity(id, "metric", "en", 703448L));
        }
    }

    public void update(Long id, String column, String value) {
        UserEntity user = userRepository.getById(id);
        switch (column) {
            case "metrics" -> {
                user.setMetrics(value);
            }
            case "language" -> {
                user.setLanguage(value);
            }
            case "location" -> {
                user.setLocation(Long.parseLong(value));
            }
        }
        userRepository.save(user);
    }

    public UserEntity getById(Long id){
        return userRepository.getById(id);
    }

    public boolean existById (Long id){
        return userRepository.existsById(id);
    }
}
