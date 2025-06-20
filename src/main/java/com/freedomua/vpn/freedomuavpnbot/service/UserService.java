package com.freedomua.vpn.freedomuavpnbot.service;

import com.freedomua.vpn.freedomuavpnbot.model.UserEntity;
import com.freedomua.vpn.freedomuavpnbot.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity createIfNotExists(UserEntity user) {
        return userRepository.findByTelegramId(user.getTelegramId())
                .orElseGet(() -> userRepository.save(user));
    }

    public Optional<UserEntity> findByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId);
    }
}
