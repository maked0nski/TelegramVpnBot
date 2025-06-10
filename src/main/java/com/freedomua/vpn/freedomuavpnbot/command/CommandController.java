package com.freedomua.vpn.freedomuavpnbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandController {
    String getCommandIdentifier();
    SendMessage handle(Update update);
}

