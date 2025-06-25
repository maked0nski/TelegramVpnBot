package com.freedomua.vpn.freedomuavpnbot.controller;

import com.freedomua.vpn.freedomuavpnbot.model.UserEntity;
import com.freedomua.vpn.freedomuavpnbot.service.UserService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{telegramId}")
    public ResponseEntity<UserEntity> getUserByTelegramId(@PathVariable Long telegramId) {
        Optional<UserEntity> user = userService.findByTelegramId(telegramId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    @PermitAll
    public List<UserEntity> getAllUsers() {
        return userService.findAll();
    }
}
