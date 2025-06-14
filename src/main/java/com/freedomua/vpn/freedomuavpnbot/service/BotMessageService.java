package com.freedomua.vpn.freedomuavpnbot.service;

import com.freedomua.vpn.freedomuavpnbot.util.MarkdownV2Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotMessageService {

    private final MessageSource messageSource;
    private AbsSender absSender;

    public void setAbsSender(AbsSender absSender) {
        this.absSender = absSender;
    }

    public void sendMarkdownMessage(Long chatId, String text) {
        String escapedText = MarkdownV2Util.escape(text);
        sendRawMarkdownV2Message(chatId, escapedText);
    }

    public void sendRawMarkdownV2Message(Long chatId, String rawText) {
        try {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId.toString())
                    .text(rawText)
                    .parseMode("MarkdownV2")
                    .build();
            absSender.execute(message);
        } catch (Exception e) {
            log.error("Failed to send message to chat ID {}: {}", chatId, e.getMessage(), e);
        }
    }

    public void sendMessageObject(SendMessage message) {
        try {
            absSender.execute(message);
        } catch (Exception e) {
            log.error("Failed to send message", e);
        }
    }
}