# --- Stage 1: Build with Maven ---
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- Stage 2: Run with JDK only ---
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Копіюємо JAR
COPY --from=builder /app/target/*.jar app.jar

# >>> Додаємо сертифікат
COPY outline.crt /usr/local/share/ca-certificates/outline.crt

COPY outline.crt /usr/local/share/ca-certificates/outline.crt
RUN update-ca-certificates && \
    keytool -importcert -noprompt -trustcacerts \
    -alias outline_server_cert \
    -file /usr/local/share/ca-certificates/outline.crt \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit

ENTRYPOINT ["java", "-jar", "app.jar"]