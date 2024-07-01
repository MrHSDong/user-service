FROM amazoncorretto:11.0.23
WORKDIR /app
COPY build/libs ./libs
COPY src ./src
EXPOSE 8081
# RUN sudo useradd -m app
# RUN ll /bin
# USER app
CMD ["java", "-jar", "libs/user-service-1.0-SNAPSHOT.jar"]
# CMD ["ls" ,"/bin"]
# CMD ["ls", "-alh", "libs"]
# CMD ["uname"]
