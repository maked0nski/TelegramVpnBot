package com.freedomua.vpn.freedomuavpnbot.service;

import com.freedomua.vpn.freedomuavpnbot.command.*;
import com.freedomua.vpn.freedomuavpnbot.config.OutlineLinksProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CommandServiceTest {

    private CommandService commandService;
    private BotMessageService botMessageService;
    private MessageSource messageSource;
    private OutlineLinksProperties outlineLinksProperties;
    private LocaleService localeService;

    @BeforeEach
    void setUp() {
        botMessageService = mock(BotMessageService.class);
        messageSource = mock(MessageSource.class);
        outlineLinksProperties = mock(OutlineLinksProperties.class);
        localeService = mock(LocaleService.class);

        commandService = new CommandService(
                new StartCommand(botMessageService, localeService, messageSource),
                new HelpCommand(botMessageService, messageSource, localeService),
                new DownloadVpnCommand(botMessageService, messageSource, localeService, outlineLinksProperties),
                new SetLanguageCommand(botMessageService, messageSource, localeService),
                new UnknownCommand(botMessageService, localeService),


                botMessageService,
                messageSource
        );
    }

    private Update mockTextUpdate(String text, long chatId) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);
        when(user.getId()).thenReturn(chatId);
        when(user.getLanguageCode()).thenReturn("en");

        when(message.getChatId()).thenReturn(chatId);
        when(message.getText()).thenReturn(text);
        when(message.getFrom()).thenReturn(user);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        return update;
    }

    private Update mockCallbackUpdate(String data, long chatId) {
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(chat.getId()).thenReturn(chatId);
        when(message.getChat()).thenReturn(chat);
        when(callbackQuery.getData()).thenReturn(data);
        when(callbackQuery.getMessage()).thenReturn(message);

        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        return update;
    }

    @Test
    void testStartCommandRouting() {
        Update update = mockTextUpdate("/start", 123L);
        commandService.processUpdate(update);
        verify(botMessageService, atLeastOnce()).sendMarkdownMessage(eq(123L), anyString());
    }

    @Test
    void testHelpCommandRouting() {
        Update update = mockTextUpdate("/help", 123L);
        commandService.processUpdate(update);
        verify(botMessageService, atLeastOnce()).sendMarkdownMessage(eq(123L), anyString());
    }

    @Test
    void testUnknownCommandRouting() {
        Update update = mockTextUpdate("/notfound", 123L);
        commandService.processUpdate(update);
        verify(botMessageService, atLeastOnce()).sendMarkdownMessage(eq(123L), anyString());
    }

    @Test
    void testCallbackLanguageSelection() {
        Update update = mockCallbackUpdate("setlang:en", 789L);
        commandService.processUpdate(update);

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(botMessageService, atLeastOnce()).sendRawMarkdownV2Message(eq(789L), anyString());
    }
}