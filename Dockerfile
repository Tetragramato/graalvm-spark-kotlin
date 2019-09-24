# Stage 1: build
FROM openjdk:8 as builder
WORKDIR /code
COPY . .
RUN ./gradlew clean build

# Stage 2 bis : run application with agent lib for reflexion
#FROM oracle/graalvm-ce:19.2.0.1
#RUN gu install native-image
#RUN mkdir /native-config
#EXPOSE 4567
#COPY --from=builder /code/build/libs/*-all.jar application.jar
#CMD ["java", "-agentlib:native-image-agent=config-output-dir=/native-config/", "-jar", "application.jar"]


# Stage 2: Build native with GraalVM image
FROM oracle/graalvm-ce:19.2.0.1 as imager
COPY --from=builder /code/build/libs/*-all.jar application.jar
RUN gu install native-image
RUN native-image --no-server \
                  #--no-fallback \
                  --allow-incomplete-classpath \
                  --initialize-at-build-time=org.eclipse.jetty.util.thread.TryExecutor \
                  -H:+ReportExceptionStackTraces \
                  -jar application.jar

# Stage 3 : Run application in dedicated image
FROM openjdk:8
EXPOSE 4567
COPY --from=imager . .
CMD ["./application"]
