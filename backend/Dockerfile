
# -------- STAGE 1: Builder con Maven + JDK --------
    FROM maven:3.9.6-eclipse-temurin-17 AS builder

    # Imposto la working directory dentro il container
    WORKDIR /app
    
    # Copio solo il pom.xml e scarico le dipendenze (Docker cache più efficiente)
    COPY pom.xml .
    RUN mvn dependency:go-offline -B
    
    # Copio il codice sorgente
    COPY src ./src
    
    # Compilo il progetto e creo il JAR
    RUN mvn clean package -DskipTests


    
    # -------- STAGE 2: Runtime leggero --------
    #FROM openjdk:17-slim
    FROM eclipse-temurin:17-jdk
    
    # Cartella di lavoro nel container runtime
    WORKDIR /app
    
    # Copio solo il JAR dallo stage builder
    COPY --from=builder /app/target/*.jar app.jar
    
    # Espongo la porta di Spring Boot (8080 di default)
    EXPOSE 8080
    
    # Comando di avvio dell'app
    #ENTRYPOINT ["java", "-jar", "/app/app.jar"]
    ENTRYPOINT ["java","-Djava.awt.headless=true","-jar","/app/app.jar"]
