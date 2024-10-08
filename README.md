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
- Docker

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

Inorder to run the application, Run the following Docker commands on the root of the project,

```shell
docker-compose build
```

```shell
docker-compose up
```

There are 4 microservices in this application
- Event Service: will run on `localhost:8081`
- Booking Service: will run on `localhost:8082`
- Payment Service: will run on `localhost:8083`
- Notification Service: will run on `localhost:8084`

## THE ARCHITECTURE

![image](architecture-diagram.png)

## MAIN WORKFLOW

1. **Create Event**: Use the `http://localhost:8081/ticketbooking/event` to create the Create
2. **Create Event Tickets**: Using the `eventId`, create event Tickets using the `http://localhost:8082/ticketbooking/ticket/multiple` Api
3. **Check Ticket Availability**: Before starting the Booking process, Check Ticket availability using the `http://localhost:8081/ticketbooking/event/:eventId/details` Api
4. **Create User**: Use the `http://localhost:8082/ticketbooking/user` to create the Create
5. **Place Ticket Purchase Request**: Place the purchase request using the `http://localhost:8082/ticketbooking/ticket/purchase` Api
6. **Purchase Flow**: Purchase Request will check the Event validity, Checks if the requested number of tickets available and places a Payment trigger by calling **Payment-Service** using **RabbitMQ**
7. **Payment Service**: Recieves Payment request from RabbitMQ listener and processes it. Sends the Payment information back to **Booking-Service**
8. **Send Email Acknowledgement**: Based on the Response received from Payment Service, Booking-Service will call **Notification-Service** for Sending Email to User
9. **Notification Flow**: Notification-Service will send the email to User about Ticket Status using **AWS SES** service

### Testing

The services `Event`, `Booking`, `Payment` , and `Notification` are unit-tested with JUnit, Mockito, and MockMVC. All the 4 microservices have minimum 80% code coverage.


### API Documentation

The services below have their own OpenAPI specifications. To access the API Docs and Postman Collection, go to the following URLs:

| Service               | Postman Collection                                                                 | API Docs                                                        |
| --------------------- | ---------------------------------------------------------------------------------- | --------------------------------------------------------------- |
| Event Service         | [Postman Collection](event-service/docs/event-service-api.postman_collection.json) | [API Docs](event-service/docs/event-service-api-docs.json)      |
| Booking Service       | [Postman Collection](booking-service/docs/booking-service.postman_collection.json) | [API Docs](booking-service/docs/booking-service-api-docs.json)  |


