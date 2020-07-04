FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 3000
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.edg=file:/dev/./urandom -jar /app.jar" ]