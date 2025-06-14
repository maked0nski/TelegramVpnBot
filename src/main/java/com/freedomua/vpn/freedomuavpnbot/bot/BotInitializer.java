package com.freedomua.vpn.freedomuavpnbot.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import com.freedomua.vpn.freedomuavpnbot.config.BotProperties;
import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.CommandService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotInitializer {

    private final CommandService commandService;
    private final BotMessageService botMessageService;
    private final BotProperties botProperties;

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            DefaultBotOptions options = new DefaultBotOptions();
            FreedomUaVpnBot bot = new FreedomUaVpnBot(options, commandService, botProperties);
            botsApi.registerBot(bot);
            botMessageService.setAbsSender(bot);
            log.info("✅ FreedomUaVpnBot registered and AbsSender injected successfully");
        } catch (Exception e) {
            log.error("❌ Failed to register bot", e);
        }
    }
}