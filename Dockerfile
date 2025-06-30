# Usar uma imagem base oficial com JDK 17
FROM eclipse-temurin:17-jdk-jammy

# Definir diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo pom.xml e o diretório src para cachear dependências
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src ./src

# Baixar dependências e buildar o projeto
RUN ./mvnw clean package -DskipTests

# Copiar o jar gerado para o diretório de trabalho
RUN cp target/*.jar app.jar

# Expor a porta padrão do Spring (a porta será configurada via variável)
EXPOSE 8080

# Comando para rodar a aplicação, usando a variável de ambiente PORT (padrão 8080)
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]