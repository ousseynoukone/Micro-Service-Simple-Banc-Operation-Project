# Use an official Maven image as the base image
FROM maven:3.8.4-openjdk-17


# Set the working directory inside the container
WORKDIR /home/app

# Copy the source code and pom.xml to the container
COPY src /home/app/src
COPY pom.xml /home/app/pom.xml

# Expose the Spring Boot application port
EXPOSE 8082

# Command to run the Spring Boot application using Maven
CMD ["mvn", "spring-boot:run"]
