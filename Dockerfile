COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src ./src

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

RUN cp target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]