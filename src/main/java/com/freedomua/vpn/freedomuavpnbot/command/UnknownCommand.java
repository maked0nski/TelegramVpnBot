package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.handler.CommandHandler;
import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnknownCommand implements CommandHandler {

    private final BotMessageService botMessageService;
    private final LocaleService localeService;

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String userId = String.valueOf(chatId);
        String message = localeService.getMessage("bot.message.unknown", userId);
        botMessageService.sendMarkdownMessage(chatId, message);
    }
}

