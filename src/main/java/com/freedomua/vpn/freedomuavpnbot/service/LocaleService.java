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
    private final Map<String, Locale> userLocales = new ConcurrentHashMap<>();

    public void setUserLocale(String userId, Locale locale) {
        userLocales.put(userId, locale);
        log.info("User {} locale set to {}", userId, locale.getLanguage());
    }

    public Locale getSavedUserLocale(String userId) {
        return userLocales.getOrDefault(userId, Locale.ENGLISH);
    }

    public String getMessage(String code, String userId) {
        Locale locale = getSavedUserLocale(userId);
        return messageSource.getMessage(code, null, locale);
    }

    public String getMessage(String code, String userId, Object... args) {
        Locale locale = getSavedUserLocale(userId);
        return messageSource.getMessage(code, args, locale);
    }

//    public Locale resolveLocaleFromCode(String languageCode) {
//        if (languageCode == null || languageCode.isBlank()) {
//            log.warn("Language code is null or blank. Defaulting to English.");
//            return Locale.ENGLISH;
//        }
//
//        switch (languageCode.toLowerCase()) {
//            case "uk":
//            case "ua":
//                return new Locale("uk");
//            case "en":
//                return Locale.ENGLISH;
//            default:
//                log.warn("Unsupported language code: {}. Defaulting to English.", languageCode);
//                return Locale.ENGLISH;
//        }
//    }
}