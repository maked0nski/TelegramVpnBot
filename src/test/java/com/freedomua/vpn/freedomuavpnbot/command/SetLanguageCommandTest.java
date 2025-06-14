
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
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SetLanguageCommandTest {

    @Mock
    private BotMessageService messageService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private LocaleService localeService;

    @InjectMocks
    private SetLanguageCommand setLanguageCommand;

    private Update updateWithMessage;
    private Update updateWithCallback;

    @BeforeEach
    void setUp() {
        // Update with message for language prompt
        User user = new User();
        user.setId(123L);
        user.setLanguageCode("en");

        Message message = new Message();
        message.setChat(new Chat(123L, "private"));
        message.setText("/setlang");
        message.setFrom(user);

        updateWithMessage = new Update();
        updateWithMessage.setMessage(message);

        // Update with callback for language selection
        Message callbackMessage = new Message();
        callbackMessage.setChat(new Chat(123L, "private"));

        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData("uk");
        callbackQuery.setMessage(callbackMessage);

        updateWithCallback = new Update();
        updateWithCallback.setCallbackQuery(callbackQuery);
    }

    @Test
    void shouldSendLanguageSelectionButtons() {
        when(localeService.getMessage(eq("bot.message.select.language"), eq("123"))).thenReturn("Оберіть мову:");

        setLanguageCommand.handle(updateWithMessage);

        verify(messageService, times(1)).sendMessageObject(argThat(sendMessage ->
                sendMessage.getChatId().equals(123L)
                        && sendMessage.getText().equals("Оберіть мову:")
                        && sendMessage.getReplyMarkup() instanceof InlineKeyboardMarkup
                        && ((InlineKeyboardMarkup) sendMessage.getReplyMarkup()).getKeyboard().get(0).size() == 2
        ));
    }

    @Test
    void shouldSetUserLanguageAndSendConfirmation() {
        when(messageSource.getMessage(eq("bot.message.language.set.uk"), any(), eq(Locale.forLanguageTag("uk"))))
                .thenReturn("Мову встановлено на українську.");

        setLanguageCommand.handle(updateWithCallback);

        verify(localeService, times(1)).setUserLocale(eq("123"), eq(Locale.forLanguageTag("uk")));
        verify(messageService, times(1)).sendMarkdownMessage(eq(123L), eq("Мову встановлено на українську."));
    }
}
