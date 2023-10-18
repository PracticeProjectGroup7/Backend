FROM eclipse-temurin:17-jdk-jammy

WORKDIR /hms/

COPY mvnw /hms/
COPY pom.xml /hms/
ADD .mvn/wrapper /hms/.mvn/wrapper

RUN [ "./mvnw", "-B", "-ntp", "-q", "dependency:go-offline"]

COPY docker_entrypoint.sh /hms/
RUN chmod +x /hms/docker_entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/hms/docker_entrypoint.sh"]
