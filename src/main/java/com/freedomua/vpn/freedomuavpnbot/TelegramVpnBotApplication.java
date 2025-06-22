package com.freedomua.vpn.freedomuavpnbot;

import com.freedomua.vpn.freedomuavpnbot.config.OutlineApiProperties;
import com.freedomua.vpn.freedomuavpnbot.config.OutlineLinksProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties({OutlineApiProperties.class, OutlineLinksProperties.class})
@EnableAsync
public class TelegramVpnBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramVpnBotApplication.class, args);
    }
}
