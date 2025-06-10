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
public class StartCommand implements CommandController {

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
        String welcomeMessage = messageSource.getMessage("bot.message.start", null, userLocale);

        return SendMessage.builder()
                .chatId(chatId)
                .text(welcomeMessage)
                .build();
    }


//    private final BotSenderService botSenderService;
//    private final MessageSource messageSource;
//
//    @Override
//    public boolean canHandle(String command) {
//        return "/start".equals(command);
//    }
//
//    @Override
//    public void handle(Update update, BotSenderService ignoredBotSenderService) {
//        Locale locale = new Locale("uk");
//
//        String messageText = messageSource.getMessage("command.start.text", null, locale);
//
//        // ВИПРАВЛЕНО: Передаємо chatId та текст в конструктор SendMessage
//        SendMessage msg = new SendMessage(String.valueOf(update.getMessage().getChatId()), messageText);
//        botSenderService.sendMsg(msg);
//    }
}