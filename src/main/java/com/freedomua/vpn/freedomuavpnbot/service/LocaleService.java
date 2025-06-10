package com.freedomua.vpn.freedomuavpnbot.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LocaleService {

    // Зберігаємо локаль користувача (chatId -> Locale)
    private final Map<String, Locale> userLocales = new ConcurrentHashMap<>();

    // Отримання списку підтримуваних мов (для інформування користувача)
    // Підтримувані мови
    @Getter
    private final Set<String> supportedLanguages = Set.of("en", "uk"); // Можна зробити динамічно з properties

    // Метод для отримання локалі користувача
    public Locale getUserLocale(String chatId) {
        // Повертаємо збережену локаль або дефолтну (англійську), якщо її немає
        return userLocales.getOrDefault(chatId, Locale.ENGLISH);
    }

    // Метод для встановлення локалі користувача
    public void setUserLocale(String chatId, Locale locale) {
        userLocales.put(chatId, locale);
    }

    // Перевірка, чи підтримується мова
    public boolean isValidLanguage(String langCode) {
        return supportedLanguages.contains(langCode.toLowerCase());
    }

}