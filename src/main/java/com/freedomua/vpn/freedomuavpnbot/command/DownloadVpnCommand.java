package com.freedomua.vpn.freedomuavpnbot.command;

import com.freedomua.vpn.freedomuavpnbot.config.OutlineLinksProperties;
import com.freedomua.vpn.freedomuavpnbot.handler.CommandHandler;
import com.freedomua.vpn.freedomuavpnbot.service.AsyncBotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.BotMessageService;
import com.freedomua.vpn.freedomuavpnbot.service.LocaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadVpnCommand implements CommandHandler {

    private final BotMessageService botMessageService;
    private final MessageSource messageSource;
    private final LocaleService localeService;
    private final OutlineLinksProperties outlineLinks;
    private final AsyncBotMessageService asyncBotMessageService;

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String userId = String.valueOf(chatId);

        Locale locale = Locale.forLanguageTag(update.getMessage().getFrom().getLanguageCode());

        String message = messageSource.getMessage(
                "bot.message.download_vpn",
                new Object[] {
                        "Windows", outlineLinks.getWindows(),
                        "macOS", outlineLinks.getMacos(),
                        "Linux", outlineLinks.getLinux(),
                        "Chrome OS", outlineLinks.getChromeos(),
                        "iOS", outlineLinks.getIos(),
                        "Android", outlineLinks.getAndroid(),
                        "Android APK", outlineLinks.getApk()
                },
                locale
        );

//        botMessageService.sendMarkdownMessage(chatId, message);
        asyncBotMessageService.sendMarkdownMessage(chatId, message);
    }
}