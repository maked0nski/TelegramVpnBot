package com.freedomua.vpn.freedomuavpnbot.bot;

import com.freedomua.vpn.freedomuavpnbot.config.BotProperties;
import com.freedomua.vpn.freedomuavpnbot.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class FreedomUaVpnBot extends TelegramLongPollingBot {
    private final CommandService commandService;
    private final BotProperties botProperties;

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        commandService.processUpdate(update);
    }
}

