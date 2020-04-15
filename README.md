# business-manager-empleado
Docker project with API to manage information of B&C employees

##Components
* Postgres Data base
* SpringMicroservice

##Build the application
sudo mvn clean install

##Start the application
docker-compose up -d

##Stop the application
docker-compose down

if you use the -v flag joined with docker-compose down, 
the volume will be deleted (delete the data base data and structure)

##Migration
The structure and some initial data is created using flywaydb dependency, the sql are located in empleado-server/src/main/resources/db.migration

##Releases

#Release 1
Microservice created to manage employees with docker-compose 