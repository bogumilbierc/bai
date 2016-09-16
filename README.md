# bai
###Bezpieczeństwo Aplikacji Internetowych
###eng. Internet applications security.
###Project for one of the classes at Bialystok Technical University.


## Technologies:
- Java 8
- MySQL 5.5
- SpringBoot 
- Hibernate 5
- Freemarker
- BootstrapCSS 3
- Maven 3
- QueryDSL


## Environment and tools:
- IntelliJ IDEA
- MySQL Workbench
- GitHub

## Running the application

### Migrating the database
1. Script **dbscript.sql** needs to be run agains database

### Starting the app
Server can be started using spring-boot plugin for maven:

- Build the project  **mvn clean install**
- Run using Maven plugin  **mvn spring-boot:run**

Plugin uses embedded Tomcat server

### Application is running on port 8085. Database must have user ```dbuser``` with password ```dbpass```
