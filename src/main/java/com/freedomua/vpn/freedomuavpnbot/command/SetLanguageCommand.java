package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class SetLanguageCommand implements CommandController {

    private final MessageSource messageSource;
    private final LocaleService localeService;

    @Override
    public String getCommandIdentifier() {
        return "/setlang";
    }

    @Override
    public SendMessage handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Locale userLocale = localeService.getUserLocale(chatId); // Поточна локаль

        Message message = update.getMessage();
        String[] parts = message.getText().split(" "); // Розділяємо команду та можливий аргумент

        if (parts.length > 1) { // Якщо є аргумент (наприклад, "/setlang en")
            String langCode = parts[1].toLowerCase();
            if (localeService.isValidLanguage(langCode)) {
                localeService.setUserLocale(chatId, new Locale(langCode));
                String successMessage = messageSource.getMessage("bot.message.language_changed", new Object[]{langCode}, new Locale(langCode));
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(successMessage)
                        .build();
            } else {
                String invalidMessage = messageSource.getMessage("bot.message.invalid_language", null, userLocale);
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(invalidMessage + "\n" + getLanguageOptionsText())
                        .build();
            }
        } else {
            // Якщо аргументу немає, пропонуємо вибрати мову за допомогою інлайн-кнопок
            String chooseLanguageMessage = messageSource.getMessage("bot.message.choose_language", null, userLocale);
            SendMessage response = SendMessage.builder()
                    .chatId(chatId)
                    .text(chooseLanguageMessage)
                    .replyMarkup(getLanguageKeyboardMarkup())
                    .build();
            return response;
        }
    }

    private InlineKeyboardMarkup getLanguageKeyboardMarkup() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder().text("English \uD83C\uDDEC\uD83C\uDDE7").callbackData("lang_en").build());
        row1.add(InlineKeyboardButton.builder().text("Українська \uD83C\uDDFA\uD83C\uDDE6").callbackData("lang_uk").build());
        keyboard.add(row1);

        markup.setKeyboard(keyboard);
        return markup;
    }

    private String getLanguageOptionsText() {
        // Можна зробити це більш динамічним, використовуючи LocaleService.getSupportedLocales()
        return "Available options: en, uk";
    }
}
