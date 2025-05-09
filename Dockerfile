# Koristi oficijelni Tomcat image
FROM tomcat:9.0

# Obriši default aplikacije (nije obavezno)
RUN rm -rf /usr/local/tomcat/webapps/*

# Kopiraj tvoj .war fajl u webapps folder
COPY target/TestRestApi.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080
