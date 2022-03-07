package org.example.weatherBot.service;

import lombok.RequiredArgsConstructor;
import org.example.weatherBot.entities.UserEntity;
import org.example.weatherBot.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void insertDefault(Long id) {
        if (!userRepository.existsById(id)) {
            userRepository.save(new UserEntity(id, "metric", "en", 703448));
        }
    }

    public void update(Long id, String column, String value) {
        UserEntity user = userRepository.getById(id);
        switch (column) {
            case "metrics" -> user.setMetrics(value);
            case "language" -> user.setLanguage(value);
            case "location" -> user.setLocation(Integer.parseInt(value));
        }
        userRepository.save(user);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public void insertUpdates(UserEntity userEntity){
        userRepository.save(userEntity);
    }
}
