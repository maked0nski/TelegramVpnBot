package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class HelpCommand implements CommandController {

    private final MessageSource messageSource;
    private final LocaleService localeService;

    @Override
    public String getCommandIdentifier() {
        return "/help";
    }

    @Override
    public SendMessage handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Locale userLocale = localeService.getUserLocale(chatId);
        String helpMessage = messageSource.getMessage("bot.message.help", null, userLocale);

        return SendMessage.builder()
                .chatId(chatId)
                .text(helpMessage)
                .build();
    }
}