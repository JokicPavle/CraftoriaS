# Koristi zvaničnu Tomcat sliku sa Docker Hub-a
FROM tomcat:9.0

# Kopiraj .war fajl u direktorijum u Tomcat-u
COPY target/TestRestApi.war /usr/local/tomcat/webapps/

# Otvori port 8080 na kojem će aplikacija biti dostupna
EXPOSE 8080

# Pokreni Tomcat
CMD ["catalina.sh", "run"]
