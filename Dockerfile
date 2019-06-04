FROM oracle/graalvm-ce:19.0.0
RUN gu install native-image

ARG NAME_APP

COPY $NAME_APP /app/application.jar
RUN ["chmod", "+x", "/app/application.jar"]
###############
#CMD ["java", "-jar", "/app/application.jar", "-agentlib:native-image-agent=config-output-dir=/native-config/"]
###############

###############
#-J-Xmx3G -J-Xms3G \
#--initialize-at-build-time=org.eclipse.jetty.util.thread.TryExecutor \
#--no-fallback \
#--static \
##############

##############
RUN native-image --no-server \
                 --initialize-at-build-time=org.eclipse.jetty.util.thread.TryExecutor \
                 -H:+ReportExceptionStackTraces \
                 -jar /app/application.jar
ENTRYPOINT ["/application"]
##############

##################
#Run application in different container
#FROM scratch
#EXPOSE 4567
#COPY --from=0 /application /
#ENTRYPOINT ["/application"]