# EVENT MICROSERVICE

_Create, Search and manage EVENTS._

### Technologies

Event service Microservice uses a following tools and frameworks to work properly:

- Java 21, 
- Spring Boot - Web, Actuator
- Spring Data JPA, H2 database
- JUnit, Mockito, MockMVC
- Swagger

## Features

Event Service Microservice is a backend system that lets you:

- Create, update, and delete events
- Retrive all the events based on Event name
- Check if the Event is Active and Valid


## RUNNING THE APPLICATIONS

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.melwin.ticketbooking.event.EventServiceApplication` class from your IDE.

Alternatively you can run the following command:

```shell
mvn spring-boot:run
```
Application will start on the port ``localhost:8081``


### API Documentation

To access the API Docs, go to the following URLs:

| Swagger UI                                                  | API Docs                                                              |
| ------------------------------------------------------------| ----------------------------------------------------------------------|
| `http://localhost:8081/ticketbooking/swagger-ui/index.html` | `http://localhost:8081/ticketbooking/v3/api-docs/ticket-booking-apis` |

[API Docs] (Docs/event-service-api-docs.json)

[Postman Collection] (Docs/Event Service API.postman_collection.json)


