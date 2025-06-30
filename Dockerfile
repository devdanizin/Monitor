# Use imagem base com JDK 17
FROM eclipse-temurin:17-jdk-jammy

# Defina diretório de trabalho dentro do container
WORKDIR /app

# Copie o pom.xml, mvnw e pasta .mvn primeiro para aproveitar cache do Docker
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Dê permissão de execução para o mvnw
RUN chmod +x ./mvnw

# Baixe dependências (primeiro para cache) e build do projeto (sem testes)
RUN ./mvnw dependency:go-offline
RUN ./mvnw clean package -DskipTests

# Copie o código-fonte após baixar dependências para evitar recompilar do zero toda vez que código mudar
COPY src ./src

# Copie o jar gerado para o local correto
RUN cp target/*.jar app.jar

# Exponha a porta 8080 (usada pela aplicação)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]