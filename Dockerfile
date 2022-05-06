FROM java:8
COPY /target/mongodb-0.0.1.jar /app.jar

CMD ["--server.port=8080"]

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build -t mongo-jar:1.0 .