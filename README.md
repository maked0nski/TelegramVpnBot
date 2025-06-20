
# 📡 FreedomUAVPNBot

FreedomUAVPNBot — Telegram-бот для надання VPN-послуг через Outline VPN. Підтримує багатомовність (англійська / українська), завантаження клієнтів, зберігання користувачів у базі даних, та контейнеризацію через Docker.

---

## 🌐 English Description

FreedomUAVPNBot is a Telegram bot designed to provide easy access to VPN services, specifically utilizing Outline VPN. It supports user registration, offers download links for VPN clients, and supports multi-language interaction.

---

## 📋 Table of Contents / Зміст

- [Features / Функціонал](#features--функціонал)
- [Technologies Used / Використані Технології](#technologies-used--використані-технології)
- [Getting Started / Початок Роботи](#getting-started--початок-роботи)
- [Environment Variables / Змінні Середовища](#environment-variables--змінні-середовища)
- [Running with Docker / Запуск у Docker](#running-with-docker--запуск-у-docker)
- [Bot Commands / Команди Бота](#bot-commands--команди-бота)
- [API Endpoints / API Ендпоінти](#api-endpoints--api-ендпоінти)
- [Project Structure / Структура Проекту](#project-structure--структура-проекту)
- [Tests / Тести](#tests--тести)
- [Contributing / Участь у Розробці](#contributing--участь-у-розробці)
- [License / Ліцензія](#license--ліцензія)

---

## ✅ Features / Функціонал

- 🆕 Auto user registration on `/start`
- 🌍 Multi-language: English + Ukrainian
- 🔐 Outline VPN client download links for all platforms
- 🧠 Future-ready: Outline API integration (key management)
- 🗄️ PostgreSQL database integration

---

## 💻 Technologies Used / Використані Технології

- Java 17 / Spring Boot 3
- TelegramBots Java Library
- PostgreSQL / Docker Compose
- Apache HttpClient 5 / Lombok
- JUnit 5 / Mockito

---

## 🚀 Getting Started / Початок Роботи

### Requirements / Передумови

- Java 17+, Maven
- Docker & Docker Compose
- Telegram Bot Token
- (optional) Outline API URL

### Clone & Run / Клонування та Запуск

```bash
git clone https://github.com/your-username/FreedomUAVPNBot.git
cd FreedomUAVPNBot
mvn clean install
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

---

## 🧪 Environment Variables / Змінні Середовища

Створіть `.env` файл з такими змінними:

```ini
BOT_TOKEN=
BOT_USERNAME=
BOT_ADMIN_CHAT_ID=

POSTGRES_DB=freedomuavpnbot_db
POSTGRES_USER=freedomuavpnbot_user
POSTGRES_PASSWORD=your_db_password

SERVER_API_URL=
OUTLINE_SERVER_ID=
OUTLINE_SERVER_LOCATION=
OUTLINE_SERVER_ENABLED=true

OUTLINE_LINKS_WINDOWS=https://download.outline.com/windows
OUTLINE_LINKS_MACOS=https://download.outline.com/macos
OUTLINE_LINKS_LINUX=https://download.outline.com/linux
OUTLINE_LINKS_CHROMEOS=https://download.outline.com/chromeos
OUTLINE_LINKS_IOS=https://download.outline.com/ios
OUTLINE_LINKS_ANDROID=https://play.google.com/store/apps/details?id=org.outline.android.client
OUTLINE_LINKS_APK=https://download.outline.com/android/apk
```

---

## 🐳 Running with Docker / Запуск у Docker

```bash
docker build -t freedomuavpnbot .
docker-compose up --build
```

---

## 💬 Bot Commands / Команди Бота

- `/start` — Register / Зареєструватися
- `/help` — Help info / Допомога
- `/downloadvpn` — Get client links / Отримати VPN-клієнти
- `/setlang` — Choose language / Вибір мови

---

## 🔗 API Endpoints / API Ендпоінти

- `GET /api/users/{telegramId}` — Get user by Telegram ID
- `GET /api/outline/server` — Get Outline API server info

---

## 🗂️ Project Structure / Структура Проекту

```
src/main/java/com/freedomua/vpn/freedomuavpnbot/
│
├── bot/        -> Bot core & init
├── command/    -> Commands (/start, /help...)
├── service/    -> Business logic
├── controller/ -> REST API controllers
├── model/      -> JPA entities
├── repository/ -> DB repositories
├── config/     -> Spring + API configs
├── client/     -> External API clients
├── util/       -> Utils (MarkdownV2...)
```

---

## 🧪 Tests / Тести

Run tests:

```bash
mvn test
```

---

## 🤝 Contributing / Участь у Розробці

Feel free to contribute\! | Вітаються pull request-и та issue-и!

---

## 📄 License / Ліцензія

This project is licensed under the MIT License.
