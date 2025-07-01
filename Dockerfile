# Imagem base com JDK 17
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Instala netcat para o script wait-for-postgres funcionar
RUN apt-get update && apt-get install -y netcat && rm -rf /var/lib/apt/lists/*

# Copia o pom.xml e o wrapper para cachear dependências
COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x mvnw

# Baixa dependências offline
RUN ./mvnw dependency:go-offline

# Copia o código fonte
COPY src ./src

# Build da aplicação sem testes
RUN ./mvnw clean package -DskipTests

# Copia o jar para uma localização simples
RUN cp target/*.jar app.jar

# Copia o script de espera
COPY wait-for-postgres.sh .

RUN chmod +x wait-for-postgres.sh

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "./wait-for-postgres.sh postgres 5432 -- java -jar app.jar --server.port=8080"]
