package com.freedomua.vpn.freedomuavpnbot.controller;

import com.freedomua.vpn.freedomuavpnbot.client.OutlineApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/outline")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class OutlineController {

    private final OutlineApiClient outlineApiClient;

    @GetMapping("/server")
    public ResponseEntity<String> getServerInfo() {
        System.out.println(">>> server API URL = " + outlineApiClient.getServerApiUrl());
        return outlineApiClient.getServerApiUrl();
    }
}
