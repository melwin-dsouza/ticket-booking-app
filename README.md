# EVENT TICKET BOOKING 

_Create, Search and Manage Events._
_Book and Cancel Tickets._
_Process Payments._
_Send Booking confirmation Via Email Notification._

### Technologies

Event ticket Booking application uses a following tools and frameworks to work properly:

- Java 21, 
- Spring Boot - Web, Actuator
- Spring Data JPA, H2 database
- JUnit, Mockito, MockMVC
- RabbitMQ
- FeignClient
- AWS SES
- Swagger

## Features

Event ticket Booking application is a backend system that lets you:

- Create, update, and delete events
- Retrive all the events based on Event name
- Check if the Event is Active and Valid
- Create, update, and delete Tickets for an Event
- Create, update, and delete Users
- Create Multiple tickets for an Event
- Create a Ticket Purchase Request
- Get the status of the Purchase request
- Process the payment for booked and Cancelled tickets
- Send Email Notification about Ticket status


## RUNNING THE APPLICATIONS

There are 4 microservices in this application
- Event Service
- Booking Service
- Payment Service
- Notification Service

Inorder to run the application Run Individual application by following command,

```shell
mvn spring-boot:run
```

## MAIN WORKFLOW

1. `Create Event`: Use the `http://localhost:8081/ticketbooking/events` to create the Create
2. `Create Tickets`: Using the Event Id create the Tickets using the `http://localhost:8082/ticketbooking/ticket/multiple` Api
3. `Check Ticket Availability`: Before starting the Booking process, Check Ticket availability using the `http://localhost:8082/ticketbooking/ticket/:eventId/details` Api
4. `Place Ticket Purchase Request`: Place the purchase request using the `http://localhost:8082/ticketbooking/ticket/purchase` Api
5. `Purchase Flow`: Purchase Request will check the Event validity, Checks if the requested number of tickets available and places a Payment trigger by calling `Payment-Service` using `RabbitMQ`
6. `Payment Flow`: Recieves Payment request from RabbitMQ listener and processes it. Sends the Payment information back to `Booking-Service`
7. `Send Email Acknowledgement`: Based on the Response received from Payment Service, Booking-Service will call `Notification-Service` for Sending Email to User
8. `Notification Flow`: Notification-Service will send the email to User about Ticket Status using `AWS SES` service




### API Documentation

The services below have their own OpenAPI specifications. To access the API Docs and Postman Collection, go to the following URLs:

| Service               | Postman Collection                                                                 | API Docs                                                        |
| --------------------- | ---------------------------------------------------------------------------------- | --------------------------------------------------------------- |
| Event Service         | [Postman Collection](event-service/docs/event-service-api.postman_collection.json) | [API Docs](event-service/docs/event-service-api-docs.json)      |
| Booking Service       | [Postman Collection](booking-service/docs/booking-service.postman_collection.json) | [API Docs](booking-service/docs/booking-service-api-docs.json)  |


