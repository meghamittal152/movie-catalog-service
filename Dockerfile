FROM openjdk:11-jre

# copy the packaged jar file into our docker image
COPY lib/movie-catalog-service-0.0.1-SNAPSHOT.jar /movie-catalog-service.jar

# set the startup command to execute the jar
CMD ["java", "-jar", "/movie-catalog-service.jar"]