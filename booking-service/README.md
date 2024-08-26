# BOOKING MICROSERVICE

_Create, Search and manage EVENTS._

### Technologies

Booking service Microservice uses a following tools and frameworks to work properly:

- Java 21, 
- Spring Boot - Web, Actuator
- Spring Data JPA, H2 database
- JUnit, Mockito, MockMVC
- RabbitMQ
- FeignClient
- Swagger

## Features

Booking Service Microservice is a backend system that lets you:

- Create, update, and delete Tickets for an Event
- Create, update, and delete Users
- Create Multiple tickets for an Event
- Create a Purchase Request
- Get the status of the Purchase request


## RUNNING THE APPLICATIONS

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.melwin.ticketbooking.booking.BookingServiceApplication` class from your IDE.

Alternatively you can run the following command:

```shell
mvn spring-boot:run
```
Application will start on the port ``localhost:8082``


### API Documentation

To access the API Docs, go to the following URLs:

| Swagger UI                                                  | API Docs                                                              |
| ------------------------------------------------------------| ----------------------------------------------------------------------|
| `http://localhost:8082/ticketbooking/swagger-ui/index.html` | `http://localhost:8082/ticketbooking/v3/api-docs/ticket-booking-apis` |

[API Docs](docs/booking-service-api-docs.json)

[Postman Collection](docs/booking-service.postman_collection.json)

