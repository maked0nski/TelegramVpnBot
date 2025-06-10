package com.freedomua.vpn.freedomuavpnbot.service;


import com.freedomua.vpn.freedomuavpnbot.bot.FreedomUaVpnBot; // Додайте цей імпорт
import org.springframework.context.annotation.Lazy; // Додайте цей імпорт
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// Видаляємо @RequiredArgsConstructor, оскільки ми будемо використовувати конструктор
@Service
public class BotSenderService {

    // Впроваджуємо FreedomUaVpnBot з @Lazy
    // Це дозволяє Spring створити BotSenderService до того, як FreedomUaVpnBot буде повністю ініціалізований,
    // розриваючи таким чином циклічну залежність під час початкового завантаження контексту.
    private final FreedomUaVpnBot freedomUaVpnBot;

    // Конструктор для впровадження FreedomUaVpnBot з @Lazy
    public BotSenderService(@Lazy FreedomUaVpnBot freedomUaVpnBot) {
        this.freedomUaVpnBot = freedomUaVpnBot;
    }

    public void execute(SendMessage message) {
        try {
            // Тепер freedomUaVpnBot буде ініціалізовано, коли ми вперше до нього звернемося
            freedomUaVpnBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Додайте логування помилок тут
        }
    }
}