package com.freedomua.vpn.freedomuavpnbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotProperties {
    private String token;
    private String username;
    private Long adminChatId;
    private String defaultServerId;
}