FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Instalar netcat para o wait-for-postgres.sh funcionar
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

RUN cp target/*.jar app.jar

COPY wait-for-postgres.sh ./wait-for-postgres.sh
RUN chmod +x ./wait-for-postgres.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-postgres.sh", "postgres", "5432", "--", "sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
