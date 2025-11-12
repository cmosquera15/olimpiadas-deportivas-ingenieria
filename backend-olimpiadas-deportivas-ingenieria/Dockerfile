# ====== STAGE 1: Build (usa Maven wrapper; cambia a Gradle si corresponde) ======
FROM eclipse-temurin:21-jdk AS build
WORKDIR /src

# Cache de dependencias
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
RUN ./mvnw -q -DskipTests dependency:go-offline

# Copia del código y build
COPY src ./src
RUN ./mvnw -q -DskipTests package

# ====== STAGE 2: Run (runtime mínimo sin root) ======
FROM gcr.io/distroless/java21:nonroot
WORKDIR /app

# Copia el jar final
COPY --from=build /src/target/*.jar /app/app.jar

# Render asigna el puerto en $PORT; default 8080 para local
ENV PORT=8080
# Ajustes “container-aware” de la JVM
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:InitialRAMPercentage=10 -XX:+UseG1GC -XX:+ExitOnOutOfMemoryError"

# Exponer para local (Render usa routing interno, no requiere EXPOSE)
EXPOSE 8080

# Distroless ya corre como usuario no-root (nonroot)
ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","/app/app.jar"]
