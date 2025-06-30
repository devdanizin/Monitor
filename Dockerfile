# Imagem base com JDK 17
FROM eclipse-temurin:17-jdk-jammy

# Diretório de trabalho no container
WORKDIR /app

# Copiar arquivos de build e wrapper do Maven para cachear dependências
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Permissão de execução para o mvnw
RUN chmod +x ./mvnw

# Baixar dependências offline para acelerar build subsequentes
RUN ./mvnw dependency:go-offline

# Copiar o código fonte
COPY src ./src

# Build do projeto sem executar testes
RUN ./mvnw clean package -DskipTests

# Copiar o jar gerado para uma localização simples
RUN cp target/*.jar app.jar

# Expor porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação, usando variável PORT com fallback para 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]