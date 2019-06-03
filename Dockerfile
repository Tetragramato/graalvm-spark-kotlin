FROM oracle/graalvm-ce:19.0.0
RUN gu install native-image

ARG NAME_APP

COPY $NAME_APP /app/application.jar
RUN ["chmod", "+x", "/app/application.jar"]
RUN native-image -jar /app/application.jar
ENTRYPOINT ["/application"]

#Run application in different container
#FROM scratch
#EXPOSE 4567
#COPY --from=0 /application /
#ENTRYPOINT ["/application"]