package com.freedomua.vpn.freedomuavpnbot.service;

import com.freedomua.vpn.freedomuavpnbot.command.*;
import com.freedomua.vpn.freedomuavpnbot.handler.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class CommandService {

    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();
    private final CommandHandler unknownCommandHandler;
    private final BotMessageService messageService;
    private final MessageSource messageSource;

    public CommandService(
            StartCommand startCommand,
            HelpCommand helpCommand,
            DownloadVpnCommand downloadVpnCommand,
            SetLanguageCommand setLanguageCommand,
            UnknownCommand unknownCommand,
            BotMessageService messageService,
            MessageSource messageSource
    ) {
        this.unknownCommandHandler = unknownCommand;
        this.messageService = messageService;
        this.messageSource = messageSource;

        commandHandlers.put("/start", startCommand);
        commandHandlers.put("/help", helpCommand);
        commandHandlers.put("/downloadvpn", downloadVpnCommand);
        commandHandlers.put("/setlang", setLanguageCommand);
    }

    public void processUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            // Передати update без перевірок в обробник setlang
            CommandHandler setLangHandler = commandHandlers.get("/setlang");
            if (setLangHandler != null) {
                setLangHandler.handle(update);
            } else {
                log.warn("No handler found for /setlang");
            }
            return;
        }

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            log.warn("Update does not contain text message");
            return;
        }

        Long chatId = update.getMessage().getChatId();
        Locale locale = Locale.forLanguageTag(update.getMessage().getFrom().getLanguageCode());
        String message = messageSource.getMessage("bot.message.start", null, locale);

        String text = update.getMessage().getText().trim();
        String command = text.split(" ")[0];
        log.info("Processing command '{}' from user {}.", command, update.getMessage().getFrom().getId());

        CommandHandler handler = commandHandlers.getOrDefault(command, unknownCommandHandler);
        handler.handle(update);
    }

}
