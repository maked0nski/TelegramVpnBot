package com.freedomua.vpn.freedomuavpnbot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class LocaleServiceTest {

    private LocaleService localeService;
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        messageSource = mock(MessageSource.class);
        localeService = new LocaleService(messageSource);
    }

    @Test
    void getSavedUserLocaleReturnsDefaultWhenNull() {
        Locale result = localeService.getSavedUserLocale(null);
        assertThat(result).isEqualTo(Locale.ENGLISH);
    }

    @Test
    void getSavedUserLocaleReturnsSetValue() {
        String userId = "user123";
        localeService.setUserLocale(userId, Locale.forLanguageTag("uk"));
        Locale locale = localeService.getSavedUserLocale(userId);
        assertThat(locale.getLanguage()).isEqualTo("uk");
    }

    @Test
    void getSavedUserLocaleFallsBackToEnglishForUnknownUser() {
        String userId = "user789";
        Locale locale = localeService.getSavedUserLocale(userId);
        assertThat(locale).isEqualTo(Locale.ENGLISH);
    }

    @Test
    void getMessageReturnsTranslatedMessage() {
        String userId = "user321";
        localeService.setUserLocale(userId, Locale.ENGLISH);

        when(messageSource.getMessage(eq("greeting.message"), any(), eq(Locale.ENGLISH)))
                .thenReturn("Hello!");

        String result = localeService.getMessage("greeting.message", userId);
        assertThat(result).isEqualTo("Hello!");
    }
}
