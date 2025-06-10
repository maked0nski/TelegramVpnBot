package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DownloadVpnCommand implements CommandController {

    private final MessageSource messageSource;
    private final LocaleService localeService;

    private static final String WINDOWS_LINK = "https://s3.amazonaws.com/outline-releases/client/windows/stable/Outline-Client.exe";
    private static final String MACOS_LINK = "https://itunes.apple.com/us/app/outline-app/id1356178125";
    private static final String LINUX_LINK = "https://s3.amazonaws.com/outline-releases/client/linux/stable/Outline-Client.AppImage";
    private static final String CHROMEOS_LINK = "https://play.google.com/store/apps/details?id=org.outline.android.client";
    private static final String IOS_LINK = "https://itunes.apple.com/us/app/outline-app/id1356177741";
    private static final String ANDROID_LINK = "https://play.google.com/store/apps/details?id=org.outline.android.client";
    private static final String APK_LINK = "https://s3.amazonaws.com/outline-releases/client/android/stable/Outline-Client.apk";

    @Override
    public String getCommandIdentifier() {
        return "/downloadvpn";
    }

    @Override
    public SendMessage handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Locale userLocale = localeService.getUserLocale(chatId);

        String platformWindows = messageSource.getMessage("platform.windows", null, userLocale);
        String platformMacos = messageSource.getMessage("platform.macos", null, userLocale);
        String platformLinux = messageSource.getMessage("platform.linux", null, userLocale);
        String platformChromeos = messageSource.getMessage("platform.chromeos", null, userLocale);
        String platformIos = messageSource.getMessage("platform.ios", null, userLocale);
        String platformAndroid = messageSource.getMessage("platform.android", null, userLocale);
        String platformAndroidApk = messageSource.getMessage("platform.android_apk", null, userLocale);

        // Екрануємо тільки назви платформ, які можуть містити спеціальні символи
        String escapedPlatformWindows = escapeTextForMarkdownV2(platformWindows);
        String escapedPlatformMacos = escapeTextForMarkdownV2(platformMacos);
        String escapedPlatformLinux = escapeTextForMarkdownV2(platformLinux);
        String escapedPlatformChromeos = escapeTextForMarkdownV2(platformChromeos);
        String escapedPlatformIos = escapeTextForMarkdownV2(platformIos);
        String escapedPlatformAndroid = escapeTextForMarkdownV2(platformAndroid);
        String escapedPlatformAndroidApk = escapeTextForMarkdownV2(platformAndroidApk);

        // Також ЕКРАНУЙТЕ будь-який ЗВИЧАЙНИЙ ТЕКСТ, який не є URL або форматуванням
        // АЛЕ МІСТИТЬ СПЕЦСИМВОЛИ (як крапка в "App Store.")
        // Цей текст знаходиться в `bot.message.download_vpn` - останній рядок.
        // Це може бути причиною проблеми, якщо крапки в ньому не екрановані.
        // Якщо ви використовуєте `_текст_` для курсиву, то крапки в `текст` мають бути екрановані.

        // Якщо проблема все ще в крапці, то її потрібно екранувати в самому .properties файлі для примітки.
        // Наприклад: `_Note: For macOS and iOS, you\'ll be redirected to the App Store\._`
        // Додайте `\` перед останньою крапкою в `App Store.`

        String downloadMessage = messageSource.getMessage(
                "bot.message.download_vpn",
                new Object[]{
                        escapedPlatformWindows, WINDOWS_LINK,
                        escapedPlatformMacos, MACOS_LINK,
                        escapedPlatformLinux, LINUX_LINK,
                        escapedPlatformChromeos, CHROMEOS_LINK,
                        escapedPlatformIos, IOS_LINK,
                        escapedPlatformAndroid, ANDROID_LINK,
                        escapedPlatformAndroidApk, APK_LINK
                },
                userLocale
        );

        return SendMessage.builder()
                .chatId(chatId)
                .text(downloadMessage)
                .parseMode("MarkdownV2")
                .disableWebPagePreview(true)
                .build();
    }

    // Дуже надійний метод екранування для MarkdownV2 (для звичайного тексту, не для URL)
    private String escapeTextForMarkdownV2(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            switch (c) {
                // Всі ці символи є спеціальними у MarkdownV2
                case '_':
                case '*':
                case '[':
                case ']':
                case '(':
                case ')':
                case '~':
                case '`':
                case '>':
                case '#':
                case '+':
                case '-':
                case '=':
                case '|':
                case '{':
                case '}':
                case '.': // <-- Крапка!
                case '!':
                    sb.append('\\'); // Додаємо зворотний слеш для екранування
                    sb.append(c);
                    break;
                case '\\': // Якщо сам зворотний слеш, його також потрібно екранувати
                    sb.append('\\');
                    sb.append('\\');
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
}