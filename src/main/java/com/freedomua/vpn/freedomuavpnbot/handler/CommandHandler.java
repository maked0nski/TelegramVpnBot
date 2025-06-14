package com.freedomua.vpn.freedomuavpnbot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    void handle(Update update);
}