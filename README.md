# TelegramVpnBot

## 🧠 Опис

`TelegramVpnBot` — це Spring Boot застосунок, який реалізує Telegram-бота для керування VPN-доступом через Outline Server API. Підтримує багатомовність, зберігання користувачів у PostgreSQL, інтерфейс REST для адміністратора і гнучке налаштування режимів (`local`, `prod`).

## 📂 Структура

```
├── src/
│   ├── main/java/com/freedomua/vpn/freedomuavpnbot/
│   │   ├── bot/              # TelegramBot implementation
│   │   ├── config/           # Spring + Bot + Locale конфігурації
│   │   ├── controller/       # REST API
│   │   ├── client/           # Outline REST API client
│   │   ├── handler/          # Telegram command handlers
│   │   ├── service/          # Business-логіка
│   │   └── model/            # DTO / Entity класи
│   └── resources/
│       ├── application.properties
│       ├── bot_messages_uk.properties
│       └── bot_messages_en.properties
└── Dockerfile / docker-compose.yml
```

## 🧪 Режими `local` / `prod`

Режим визначається через змінну середовища `SPRING_PROFILES_ACTIVE`:

* `local` — підключення до Outline API через `insecureRestTemplate` (SSL обхід).
* `prod` — повна перевірка SSL та безпечна робота з API.

## ⚙️ Запуск через Docker

```
docker compose up --build
```

> Використовуються `.env` змінні (див. `.env.example`).

## 🧪 Тестування локально

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## 📦 Побудова `.jar`

```
./mvnw clean package -DskipTests
```

## 🔐 Необхідні змінні `.env`

```
BOT_TOKEN=your_bot_token
BOT_USERNAME=freedomuavpn_bot
BOT_ADMIN_CHAT_ID=123456789
OUTLINE_API_URL=https://example.com:port/your-api-key
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/telegramvpn
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=yourpassword
SPRING_PROFILES_ACTIVE=prod
```

## 🛠 API для адміністратора

* `GET /api/outline/server` — отримати базову інформацію про Outline-сервер.

## 📜 Ліцензія

MIT (буде додано в LICENSE)

---

*З любов’ю до свободи інтернету 🇺🇦*
