FROM eclipse-temurin:qdo11.0.20.1_1-jdk
WORKDIR /app
COPY build/libs/* ./
COPY src ./src
COPY entrypoint.sh /bin/entrypoint.sh
RUN chmod +x /bin/entrypoint.sh
ENTRYPOINT ["/bin/entrypoint.sh"]
#CMD ["java", "-jar", "libs/user-service-1.0-SNAPSHOT.jar"]
