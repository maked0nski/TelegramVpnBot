package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.handler.CommandHandler;
import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelpCommand implements CommandHandler {

    private final BotMessageService botMessageService;
    private final MessageSource messageSource;
    private final LocaleService localeService;

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        Locale locale = Locale.forLanguageTag(update.getMessage().getFrom().getLanguageCode());
        String message = messageSource.getMessage("bot.message.help", null, locale);
        botMessageService.sendMarkdownMessage(chatId, message);
//        Message message = update.getMessage();
//        String userId = String.valueOf(chatId);
//
//        String response = localeService.getMessage("bot.message.help", userId);
//        botMessageService.sendMarkdownMessage(chatId, response);
    }

}