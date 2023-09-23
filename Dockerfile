FROM gradle:jdk11-alpine
RUN mkdir /home/gradle/source
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle bootJar --no-daemon

EXPOSE 8080
ENTRYPOINT ["java","-jar","build/libs/photographer.jar"]