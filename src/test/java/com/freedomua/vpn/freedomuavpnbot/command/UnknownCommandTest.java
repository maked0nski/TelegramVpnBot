package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnknownCommandTest {

    @Mock
    private BotMessageService botMessageService;

    @Mock
    private LocaleService localeService;

    @InjectMocks
    private UnknownCommand unknownCommand;

    private Update update;
    private Message message;
    private Chat chat;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(12345L);
        user.setLanguageCode("uk");

        chat = new Chat();
        chat.setId(67890L);

        message = new Message();
        message.setChat(chat);
        message.setText("/unknown");
        message.setFrom(user);

        update = new Update();
        update.setMessage(message);
    }

    @Test
    void handle_shouldSendUnknownCommandMessageWithMarkdownV2() {
        String userId = String.valueOf(chat.getId());
        String expectedMessage = "Невідома команда. Спробуйте /help.";

        when(localeService.getMessage("bot.message.unknown.command", userId)).thenReturn(expectedMessage);

        unknownCommand.handle(update);

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(botMessageService, times(1)).sendMessageObject(captor.capture());

        SendMessage actualMessage = captor.getValue();
        assertThat(actualMessage).isNotNull();
        assertThat(actualMessage.getChatId()).isEqualTo(String.valueOf(chat.getId()));
        assertThat(actualMessage.getText()).isEqualTo(expectedMessage);
        assertThat(actualMessage.getParseMode()).isEqualTo("MarkdownV2");
    }
}
