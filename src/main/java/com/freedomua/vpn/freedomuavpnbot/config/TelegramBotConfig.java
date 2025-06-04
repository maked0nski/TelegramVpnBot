package com.freedomua.vpn.freedomuavpnbot.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot")
public class TelegramBotConfig {
    @Getter
    @Setter
    private String token;
//    private String username;
}
