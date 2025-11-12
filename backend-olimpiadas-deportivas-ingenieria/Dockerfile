# ====== STAGE 1: Build ======
FROM eclipse-temurin:21-jdk AS build
WORKDIR /src

# Copia solo lo necesario para cachear dependencias
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./

# Permisos + convertir CRLF a LF (evita exit code 126 en Windows)
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Descarga dependencias
RUN ./mvnw -q -DskipTests dependency:go-offline

# Copia el código y compila
COPY src ./src
RUN ./mvnw -q -DskipTests package

# ====== STAGE 2: Runtime ======
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia el jar compilado
COPY --from=build /src/target/*.jar /app/app.jar

# Render inyecta $PORT
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","/app/app.jar"]

