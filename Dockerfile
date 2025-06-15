FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . /app

RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/telegramvpn-*.jar"]
