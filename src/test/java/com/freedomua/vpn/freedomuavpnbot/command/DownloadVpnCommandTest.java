package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.config.OutlineLinksProperties;
import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.*;

public class DownloadVpnCommandTest {

    @Mock
    private BotMessageService botMessageService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private OutlineLinksProperties links;

    @Mock
    private LocaleService localeService;

    private DownloadVpnCommand command;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        command = new DownloadVpnCommand(botMessageService, messageSource, localeService, links);
    }

    @Test
    public void testHandleDownloadCommand() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(1L);
        when(message.getFrom()).thenReturn(user);
        when(user.getLanguageCode()).thenReturn("en");

        when(messageSource.getMessage(eq("bot.message.download_vpn"), any(), any())).thenReturn("Download links:");

        command.handle(update);

        verify(botMessageService).sendMarkdownMessage(eq(1L), eq("Download links:"));
    }
}
