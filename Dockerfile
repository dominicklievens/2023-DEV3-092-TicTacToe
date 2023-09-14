FROM eclipse-temurin:17-jdk-focal

COPY . /app
WORKDIR /app
RUN ./mvnw clean package

CMD ["./mvnw", "spring-boot:run"]