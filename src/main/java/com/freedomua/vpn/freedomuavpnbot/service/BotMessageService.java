package com.freedomua.vpn.freedomuavpnbot.service;

import com.freedomua.vpn.freedomuavpnbot.util.MarkdownV2Util;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotMessageService {

    @Setter
    private AbsSender absSender;

    @Value("${bot.adminChatId}")
    private String adminChatId;


    public void sendMarkdownMessage(Long chatId, String text) {
        String escapedText = MarkdownV2Util.escape(text);
        sendRawMarkdownV2Message(chatId, escapedText);
    }

    @Retry(name = "telegram")
    @CircuitBreaker(name = "telegram", fallbackMethod = "fallbackSendMessage")
    public void sendRawMarkdownV2Message(Long chatId, String rawText) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(rawText)
                .parseMode("MarkdownV2")
                .build();

        try {
            absSender.execute(message); // основна спроба
        } catch (TelegramApiException e) {
            log.warn("❌ Telegram send failed for chat {}: {}", chatId, e.getMessage());
            throw new RuntimeException("Telegram send failed", e);
        }
    }

    private void fallbackSendMessage(Long chatId, String rawText, Throwable ex) {
        log.error("Fallback triggered for chat {}: {}", chatId, ex.getMessage());

        String alert = "❗️*Помилка при надсиланні повідомлення*\n\n"
                + "*Чат:* " + chatId + "\n"
                + "*Помилка:* " + ex.getClass().getSimpleName() + " — " + ex.getMessage();

        SendMessage alertMessage = SendMessage.builder()
                .chatId(adminChatId)
                .text(alert)
                .parseMode("MarkdownV2")
                .build();

        try {
            absSender.execute(alertMessage);
        } catch (Exception e) {
            log.error("‼️ Неможливо повідомити адміна: {}", e.getMessage());
        }
    }

}