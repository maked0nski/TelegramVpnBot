package com.freedomua.vpn.freedomuavpnbot.bot;

import com.freedomua.vpn.freedomuavpnbot.config.BotProperties;
import com.freedomua.vpn.freedomuavpnbot.service.CommandService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.bots.DefaultBotOptions;


@Slf4j
public class FreedomUaVpnBot extends DefaultAbsSender implements LongPollingBot {

    private final CommandService commandService;
    private final BotProperties botProperties;

    public FreedomUaVpnBot(DefaultBotOptions options, CommandService commandService, BotProperties botProperties) {
        super(options);
        this.commandService = commandService;
        this.botProperties = botProperties;
    }

    @Override
    public void onUpdateReceived(Update update) {
        commandService.processUpdate(update);
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public void clearWebhook() {
        // No-op for long polling
    }
}