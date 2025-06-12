package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class StartCommand implements CommandController {

    // Створюємо екземпляр логера для цього класу
    private static final Logger LOGGER = LoggerFactory.getLogger(StartCommand.class);

    private final MessageSource messageSource;
    private final LocaleService localeService;



    @Override
    public String getCommandIdentifier() {
        return "/start";
    }

    @Override
    public SendMessage handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Locale userLocale = localeService.getUserLocale(chatId);
        // Повідомлення беремо з messageSource
        String welcomeMessage = messageSource.getMessage("bot.message.start", null, userLocale);

        // Логуємо подію
        LOGGER.info("StartCommand executed successfully for user ID: {}", update.getMessage().getFrom().getId());

        return SendMessage.builder()
                .chatId(chatId)
                .text(welcomeMessage)
                .build();
    }

}