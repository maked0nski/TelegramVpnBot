package com.freedomua.vpn.freedomuavpnbot.service;

import com.freedomua.vpn.freedomuavpnbot.command.CommandController;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandService {

    private final BotSenderService botSenderService;
    private final List<CommandController> commands;
    private final MessageSource messageSource;
    private final LocaleService localeService; // Впроваджуємо LocaleService

    private Map<String, CommandController> commandMap;

    @PostConstruct
    public void init() {
        commandMap = commands.stream()
                .collect(Collectors.toMap(CommandController::getCommandIdentifier, Function.identity()));
    }

    public void processUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String text = message.getText();
            String chatId = message.getChatId().toString();

            Locale userLocale = localeService.getUserLocale(chatId); // Використовуємо LocaleService

            if (text.startsWith("/")) {
                String commandIdentifier = text.split(" ")[0];
                CommandController command = commandMap.get(commandIdentifier);

                if (command != null) {
                    SendMessage response = command.handle(update);
                    botSenderService.execute(response);
                } else {
                    SendMessage defaultResponse = SendMessage.builder()
                            .chatId(chatId)
                            .text(messageSource.getMessage("bot.message.unknown_command", null, userLocale))
                            .build();
                    botSenderService.execute(defaultResponse);
                }
            } else {
                SendMessage defaultResponse = SendMessage.builder()
                        .chatId(chatId)
                        .text(messageSource.getMessage("bot.message.received_text", new Object[]{text}, userLocale))
                        .build();
                botSenderService.execute(defaultResponse);
            }
        } else if (update.hasCallbackQuery()) {
            // Обробка CallbackQuery (для інлайн-кнопок)
            String callbackData = update.getCallbackQuery().getData();
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();

            if (callbackData.startsWith("lang_")) {
                String langCode = callbackData.substring("lang_".length());
                if (localeService.isValidLanguage(langCode)) {
                    Locale newLocale = new Locale(langCode);
                    localeService.setUserLocale(chatId, newLocale);
                    String successMessage = messageSource.getMessage("bot.message.language_changed", new Object[]{langCode}, newLocale);
                    botSenderService.execute(SendMessage.builder()
                            .chatId(chatId)
                            .text(successMessage)
                            .build());
                } else {
                    Locale userLocale = localeService.getUserLocale(chatId);
                    String invalidMessage = messageSource.getMessage("bot.message.invalid_language", null, userLocale);
                    botSenderService.execute(SendMessage.builder()
                            .chatId(chatId)
                            .text(invalidMessage)
                            .build());
                }
            }
            // Можна додати обробку інших типів callbackData
        }
    }
}