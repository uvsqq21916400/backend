# --- Build (Maven + JDK 21) ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn -B -DskipTests package

# --- Runtime (JRE 21) ---
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV SPRING_PROFILES_ACTIVE=ping
ENV PORT=8080
# copie l'unique jar construit par Maven
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
CMD ["sh","-c","java -jar /app/app.jar --server.port=${PORT} --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
