package com.freedomua.vpn.freedomuavpnbot.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncBotMessageService {

    private final BotMessageService botMessageService;

    @Async
    public void sendMarkdownMessage(Long chatId, String text) {
        botMessageService.sendMarkdownMessage(chatId, text);
    }

    @Async
    public void sendRawMarkdownV2Message(Long chatId, String rawText) {
        botMessageService.sendRawMarkdownV2Message(chatId, rawText);
    }

    @Async
    public void sendMessageObject(org.telegram.telegrambots.meta.api.methods.send.SendMessage message) {
        botMessageService.sendMessageObject(message);
    }
}
