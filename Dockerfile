FROM openjdk:11

EXPOSE 8090

ADD /target/*.jar /app/application.jar

ENTRYPOINT exec java $JAVA_OPTS -jar /app/application.jar
