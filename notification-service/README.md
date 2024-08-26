# NOTIFICATION MICROSERVICE

_Responsible for Sending Ticket Confirmation Emails to User._

### Technologies

Notification service Microservice uses a following tools and frameworks to work properly:

- Java 21, 
- Spring Boot - Web, Actuator
- Spring Data JPA, H2 database
- JUnit, Mockito, MockMVC
- RabbitMQ
- AWS SES
- Swagger

## Features

Notification Service Microservice is a backend system that lets you:

- Recieves a Notification request from RabbitMQ listerner
- Creates the Email template based n the request
- Sends an Email to the End user about the Booking status
- Uses AWS SES to send the Email


## RUNNING THE APPLICATIONS

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.melwin.ticketbooking.notification.NotificationServiceApplication` class from your IDE.

Alternatively you can run the following command:

```shell
mvn spring-boot:run
```
Application will start on the port ``localhost:8084``


### Notification Details

Following Details will be send to the user

- Event Date
- Event Location
- User Name
- Ticket Type
- Number of Tickets
- Payment Amount


