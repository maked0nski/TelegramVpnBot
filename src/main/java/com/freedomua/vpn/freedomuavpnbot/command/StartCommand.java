package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.handler.CommandHandler;
import com.freedomua.vpn.freedomuavpnbot.model.UserEntity;
import com.freedomua.vpn.freedomuavpnbot.service.AsyncBotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import com.freedomua.vpn.freedomuavpnbot.service.UserService;
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
public class StartCommand implements CommandHandler {

    private final MessageSource messageSource;
    private final UserService userService;
    private final AsyncBotMessageService asyncBotMessageService;
    private final LocaleService localeService;

    @Override
    public void handle(Update update) {
        Message telegramMessage = update.getMessage();
        Long chatId = update.getMessage().getChatId();

        // Створення користувача
        UserEntity user = new UserEntity();
        user.setTelegramId(telegramMessage.getFrom().getId());
        user.setUsername(telegramMessage.getFrom().getUserName());
        user.setFirstName(telegramMessage.getFrom().getFirstName());
        user.setLastName(telegramMessage.getFrom().getLastName());
        user.setLanguageCode(telegramMessage.getFrom().getLanguageCode());

        userService.createIfNotExists(user);

        // Надсилання повідомлення
//        Locale locale = Locale.forLanguageTag(user.getLanguageCode());
//        String message = messageSource.getMessage("bot.message.start", null, locale);

        String text = localeService.getMessage("bot.message.start",chatId);
//        botMessageService.sendMarkdownMessage(chatId, message);
        asyncBotMessageService.sendMarkdownMessage(chatId, text);

    }
}