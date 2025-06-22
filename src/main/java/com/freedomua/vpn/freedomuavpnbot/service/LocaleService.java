package com.freedomua.vpn.freedomuavpnbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocaleService {

    private final MessageSource messageSource;
    private final Map<Long, Locale> userLocales = new ConcurrentHashMap<>();

    public void setUserLocale(Long chatId, Locale locale) {
        userLocales.put(chatId, locale);
        log.info("User {} locale set to {}", chatId, locale.getLanguage());
    }

    public Locale getSavedUserLocale(Long chatId) {
        return userLocales.getOrDefault(chatId, Locale.ENGLISH);
    }

//    public String getMessage(String code, Long chatId) {
//        Locale locale = getSavedUserLocale(chatId);
//        return messageSource.getMessage(code, null, locale);
//    }

    public String getMessage(String code, Long chatId, Object... args) {
        Locale locale = getSavedUserLocale(chatId);
        return messageSource.getMessage(code, args, locale);
    }
}