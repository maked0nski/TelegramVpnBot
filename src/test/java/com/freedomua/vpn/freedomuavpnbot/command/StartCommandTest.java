package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Locale;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartCommandTest {

    @Mock
    private BotMessageService messageService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private LocaleService localeService;

    @InjectMocks
    private StartCommand startCommand;

    private Update testUpdate;

    @BeforeEach
    void setUp() {
        Chat chat = new Chat();
        chat.setId(12345L);

        User user = new User();
        user.setId(67890L);
        user.setLanguageCode("en");

        Message message = new Message();
        message.setChat(chat);
        message.setFrom(user);
        message.setText("/start");

        testUpdate = new Update();
        testUpdate.setMessage(message);
    }

    @Test
    void handle_shouldSendWelcomeMessage() {
        when(localeService.getMessage(eq("bot.message.start"), anyString()))
                .thenReturn("Welcome to FreedomUAVpnBot!");
        when(localeService.getMessage(eq("bot.message.help_prompt"), anyString()))
                .thenReturn("Type /help for assistance.");

        startCommand.handle(testUpdate);

        verify(messageService).sendMarkdownMessage(eq(12345L),
                eq("Welcome to FreedomUAVpnBot!\n\nType /help for assistance."));
    }
}
