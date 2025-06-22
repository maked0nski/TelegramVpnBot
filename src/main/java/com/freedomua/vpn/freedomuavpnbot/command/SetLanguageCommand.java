package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.handler.CommandHandler;
import com.freedomua.vpn.freedomuavpnbot.service.AsyncBotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetLanguageCommand implements CommandHandler {

    private final BotMessageService messageService;
    private final MessageSource messageSource;
    private final LocaleService localeService;
    private final AsyncBotMessageService asyncBotMessageService;

    @Override
    public void handle(Update update) {
        Long chatId;
        String userId;

        if (update.hasCallbackQuery()) {
            String selectedLang = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = String.valueOf(chatId);
            localeService.setUserLocale(chatId, Locale.forLanguageTag(selectedLang));

            String confirmation = messageSource.getMessage("bot.message.language_changed", null, Locale.forLanguageTag(selectedLang));
//            messageService.sendMarkdownMessage(chatId, confirmation);
            asyncBotMessageService.sendMarkdownMessage(chatId, confirmation);
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            chatId = message.getChatId();
            userId = String.valueOf(chatId);

            String prompt = localeService.getMessage("bot.message.choose_language", chatId);

            InlineKeyboardButton enButton = new InlineKeyboardButton("English");
            enButton.setCallbackData("en");

            InlineKeyboardButton ukButton = new InlineKeyboardButton("Українська");
            ukButton.setCallbackData("uk");

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
            keyboard.setKeyboard(List.of(List.of(enButton, ukButton)));

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(prompt);
            sendMessage.setReplyMarkup(keyboard);

//            messageService.sendMessageObject(sendMessage);
            asyncBotMessageService.sendMessageObject(sendMessage);
        }
    }
}

