
# -------- STAGE 1: Container per il building con Maven + JDK --------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Imposto la working directory dentro il container
WORKDIR /build

# Copio solo il pom.xml e scarico le dipendenze 
# Docker cache più efficiente: se il pom.xml non cambia, Docker riusa il layer precedente e non scarica di nuovo le dipendenze
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copio il codice sorgente
COPY src ./src

# Compilo il progetto e creo il JAR
RUN mvn clean package -DskipTests




# -------- STAGE 2: Container per il runtime --------
FROM eclipse-temurin:17-jdk

# Imposto la working directory dentro il container
WORKDIR /app

# Copio solo il JAR dallo stage builder
COPY --from=builder /build/target/*.jar app.jar

# Espongo la porta di Spring Boot (8080 di default)
EXPOSE 8080

# Comando di avvio dell'app
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "/app/app.jar"]