package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.model.UserEntity;
import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import com.freedomua.vpn.freedomuavpnbot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartCommandTest {

    @Mock
    private BotMessageService messageService;

    @Mock
    private LocaleService localeService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private UserService userService;

    private StartCommand startCommand;

    private Update testUpdate;

    @BeforeEach
    void setUp() {
        // Ручне створення замість @InjectMocks
        startCommand = new StartCommand(messageService, localeService, messageSource, userService);

        Chat chat = new Chat();
        chat.setId(12345L);

        User user = new User();
        user.setId(67890L);
        user.setUserName("test_user");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setLanguageCode("en");

        Message message = new Message();
        message.setChat(chat);
        message.setFrom(user);
        message.setText("/start");

        testUpdate = new Update();
        testUpdate.setMessage(message);
    }

    @Test
    void handle_shouldCreateUserAndSendWelcomeMessage() {
        when(messageSource.getMessage(eq("bot.message.start"), any(), any()))
                .thenReturn("Welcome to FreedomUAVpnBot!");

        when(userService.createIfNotExists(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        startCommand.handle(testUpdate);

        verify(userService, times(1)).createIfNotExists(any(UserEntity.class));
        verify(messageService, times(1)).sendMarkdownMessage(eq(12345L), eq("Welcome to FreedomUAVpnBot!"));
    }
}
