// src/test/java/com/freedomua/vpn/freedomuavpnbot/command/DownloadVpnCommandTest.java
package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DownloadVpnCommandTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private LocaleService localeService;

    @InjectMocks
    private DownloadVpnCommand downloadVpnCommand;

    private Update testUpdate;
    private Chat testChat;

    @BeforeEach
    void setUp() {
        testChat = new Chat();
        testChat.setId(12345L);

        Message testMessage = new Message();
        testMessage.setChat(testChat);
        testMessage.setText("/downloadvpn");

        testUpdate = new Update();
        testUpdate.setMessage(testMessage);
    }

    @Test
    void getCommandIdentifier_shouldReturnCorrectIdentifier() {
        assertEquals("/downloadvpn", downloadVpnCommand.getCommandIdentifier());
    }

    @Test
    void handle_shouldReturnSendMessageWithCorrectChatIdAndText() {
        // !!! НАЛАШТУВАННЯ MOCKITO ТУТ, ВКЛЮЧАЮЧИ localeService !!!
        when(localeService.getUserLocale(any(String.class))).thenReturn(Locale.ENGLISH); // ТЕПЕР ТУТ

        // Налаштовуємо поведінку mock messageSource для ключа bot.message.download_vpn
        when(messageSource.getMessage(
                eq("bot.message.download_vpn"),
                any(Object[].class),
                eq(Locale.ENGLISH)
        )).thenReturn(
                """
                        Here are the download links for the Outline VPN client:
                        
                        Windows: [Windows](https://s3.amazonaws.com/outline-releases/client/windows/stable/Outline-Client.exe)
                        macOS: [macOS](https://itunes.apple.com/us/app/outline-app/id1356178125)
                        Linux: [Linux](https://s3.amazonaws.com/outline-releases/client/linux/stable/Outline-Client.AppImage)
                        Chrome OS: [Chrome OS](https://play.google.com/store/apps/details?id=org.outline.android.client)
                        iOS: [iOS (iPhone)](https://itunes.apple.com/us/app/outline-app/id1356177741)
                        Android: [Android](https://play.google.com/store/apps/details?id=org.outline.android.client)
                        Android direct APK: [Android (alternative)](https://s3.amazonaws.com/outline-releases/client/android/stable/Outline-Client.apk)
                        
                        _Note: For macOS and iOS, you\\'ll be redirected to the App Store\\._"""
        );

        // Налаштовуємо поведінку mock messageSource для ключів платформ
        when(messageSource.getMessage(eq("platform.windows"), any(), eq(Locale.ENGLISH))).thenReturn("Windows");
        when(messageSource.getMessage(eq("platform.macos"), any(), eq(Locale.ENGLISH))).thenReturn("macOS");
        when(messageSource.getMessage(eq("platform.linux"), any(), eq(Locale.ENGLISH))).thenReturn("Linux");
        when(messageSource.getMessage(eq("platform.chromeos"), any(), eq(Locale.ENGLISH))).thenReturn("Chrome OS");
        when(messageSource.getMessage(eq("platform.ios"), any(), eq(Locale.ENGLISH))).thenReturn("iOS (iPhone)");
        when(messageSource.getMessage(eq("platform.android"), any(), eq(Locale.ENGLISH))).thenReturn("Android");
        when(messageSource.getMessage(eq("platform.android_apk"), any(), eq(Locale.ENGLISH))).thenReturn("Android (alternative)");

        SendMessage sendMessage = downloadVpnCommand.handle(testUpdate);

        assertNotNull(sendMessage);
        assertEquals(String.valueOf(testChat.getId()), sendMessage.getChatId());
        assertEquals("MarkdownV2", sendMessage.getParseMode());
        assertEquals(true, sendMessage.getDisableWebPagePreview());

        String expectedText = """
                Here are the download links for the Outline VPN client:
                
                Windows: [Windows](https://s3.amazonaws.com/outline-releases/client/windows/stable/Outline-Client.exe)
                macOS: [macOS](https://itunes.apple.com/us/app/outline-app/id1356178125)
                Linux: [Linux](https://s3.amazonaws.com/outline-releases/client/linux/stable/Outline-Client.AppImage)
                Chrome OS: [Chrome OS](https://play.google.com/store/apps/details?id=org.outline.android.client)
                iOS: [iOS (iPhone)](https://itunes.apple.com/us/app/outline-app/id1356177741)
                Android: [Android](https://play.google.com/store/apps/details?id=org.outline.android.client)
                Android direct APK: [Android (alternative)](https://s3.amazonaws.com/outline-releases/client/android/stable/Outline-Client.apk)
                
                _Note: For macOS and iOS, you\\'ll be redirected to the App Store\\._""";
        assertEquals(expectedText, sendMessage.getText());
    }

    @Test
    void escapeTextForMarkdownV2_shouldEscapeSpecialCharacters() {
        assertEquals("Test\\_text\\*", downloadVpnCommand.escapeTextForMarkdownV2("Test_text*"));
        assertEquals("Test\\(with\\)brackets", downloadVpnCommand.escapeTextForMarkdownV2("Test(with)brackets"));
        assertEquals("Test\\.dot\\!", downloadVpnCommand.escapeTextForMarkdownV2("Test.dot!"));
        assertEquals("Backslash\\\\", downloadVpnCommand.escapeTextForMarkdownV2("Backslash\\"));
        assertEquals("No special chars", downloadVpnCommand.escapeTextForMarkdownV2("No special chars"));
        assertEquals("", downloadVpnCommand.escapeTextForMarkdownV2(""));
        assertNull(downloadVpnCommand.escapeTextForMarkdownV2(null));
    }
}