# 1. Maven build stage
FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Tomcat run stage
FROM tomcat:9.0
COPY --from=builder /app/target/TestRestApi.war /usr/local/tomcat/webapps/TestRestApi.war
