package com.freedomua.vpn.freedomuavpnbot;

import com.freedomua.vpn.freedomuavpnbot.config.TelegramBotConfig;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;

    public UpdateConsumer(TelegramBotConfig telegramBotConfig) {
        this.telegramClient = new OkHttpTelegramClient(
                telegramBotConfig.getToken());
    }

    @SneakyThrows
    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (text.equals("/start")) {
                sendStartMessage(chatId);
            } else {
                sendMessage(chatId, "Привіт. Я тебе не зрозумів");
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        var data = callbackQuery.getData();
        var chatId = callbackQuery.getFrom().getId();
        var user = callbackQuery.getFrom();
        switch (data) {
            case "my_name" -> sendMyName(chatId, user);
            case "language" -> sendLanguage(chatId, user);
            case "button3" -> sendButton3(chatId);
            default -> sendMessage(chatId, "Невідома команда. ");
        }
    }

    private void sendButton3(Long chatId) {
        sendMessage(chatId, "Button3");
    }

    @SneakyThrows
    private void sendMessage(Long chatId, String messageText) {
        SendMessage message = SendMessage.builder()
                .text(messageText)
                .chatId(chatId)
                .build();
        telegramClient.execute(message);
    }

    private void sendLanguage(Long chatId, User user) {
        String language = user.getLanguageCode();

        sendMessage(chatId, "Обрана мова: " + language);
    }

    private void sendMyName(Long chatId, User user) {
        var text = "Тебе звати: %s\nТвій нік: @%s"
                .formatted(
                        user.getFirstName() + " " + user.getLastName(),
                        user.getUserName()
                );
        sendMessage(chatId, text);
    }

    @SneakyThrows
    private void sendStartMessage(Long chatId) {
        SendMessage message = SendMessage.builder()
                .text("Зробіть вибір: ")
                .chatId(chatId)
                .build();

        var button1 = InlineKeyboardButton.builder()
                .text("Моє Ім'я")
                .callbackData("my_name")
                .build();
        var button2 = InlineKeyboardButton.builder()
                .text("Вибрана мова")
                .callbackData("language")
                .build();
        var button3 = InlineKeyboardButton.builder()
                .text("Кнопка №3")
                .callbackData("button3")
                .build();

        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(button1),
                new InlineKeyboardRow(button2),
                new InlineKeyboardRow(button3)
        );

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(keyboardRows);

        message.setReplyMarkup(markup);
        telegramClient.execute(message);
    }
}
