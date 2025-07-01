# Imagem base com JDK 17
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copiar arquivos do Maven para cachear dependências
COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

RUN cp target/*.jar app.jar

# Copiar script wait-for-postgres para o container
COPY wait-for-postgres.sh ./wait-for-postgres.sh
RUN chmod +x ./wait-for-postgres.sh

EXPOSE 8080

# Entrypoint que espera o banco subir antes de iniciar a aplicação
ENTRYPOINT ["./wait-for-postgres.sh", "postgres", "5432", "--", "sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]