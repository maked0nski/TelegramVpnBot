package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Locale;

import static org.mockito.Mockito.*;

public class HelpCommandTest {

    @Test
    void handle_shouldSendHelpMessage() {
        BotMessageService messageService = mock(BotMessageService.class);
        MessageSource messageSource = mock(MessageSource.class);
        LocaleService localeService = mock(LocaleService.class);

        HelpCommand command = new HelpCommand(messageService, messageSource, localeService);

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.getMessage()).thenReturn(message);
        when(message.getFrom()).thenReturn(user);
        when(message.getChatId()).thenReturn(111L);
        when(user.getLanguageCode()).thenReturn("uk");

        when(messageSource.getMessage(eq("bot.message.help"), any(), eq(Locale.forLanguageTag("uk"))))
                .thenReturn("Допомога");

        command.handle(update);

        verify(messageService).sendMarkdownMessage(111L, "Допомога");
    }
}
