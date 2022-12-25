Book Catalog
======================

## About
A spring boot application which offers CRUD operations to add, update, delete and search books.


## Building
You need to install Docker and Java JDK 17 to build and run. Make sure to setting JDK 17 by setting JAVA_HOME before running gradle:

- `export JAVA_HOME=/PATH/TO/JDK/17`
- `cd  book-catalog`
- `./gradlew test jibDockerBuild`

after successful gradle command, there is a new docker image on your system (mimdal/book-catalog image). 
run following command to see local machine images
- `docker images`  

## How Run

### by docker compose
Open terminal and run following command:
- `cd book-catalog/app/docker`
- `docker compose up`, and for executing in background `docker compose up -d`


## swagger ui
to access swagger open a web browser and access  
[http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/) address.


## Technologies/Tools used
- hexagonal architecture 
- java 17
- SpringBoot, Spring Data JPA
- gradle: for automate build
- multi module project: api, app, domain, infrastructure
- swagger: for API document
- lombok: to reduce boilerplate code. (eg. getter-setter-builder-tostring)
- mapstruct
- database: postgreSQL  
- h2: in memory database for integration test
- liquibase: for applying database schema changes 
