# PAYMENT MICROSERVICE

_Responsible for Processing the Payment._

### Technologies

Payment Microservice uses a following tools and frameworks to work properly:

- Java 21, 
- Spring Boot - Web, Actuator
- Spring Data JPA, H2 database
- JUnit, Mockito, MockMVC
- RabbitMQ
- Swagger

## WORKFLOW

Payment Microservice is a backend system works in the following order:

- Recieves a Payment request from RabbitMQ listerner
- Creates the Payment record in the Database
- Waits for the payment to complete (Currently Payment provider is not intgerated due to time constraint)
- Sends the Payment confirmation back to Booking service using Rabbit MQ


## RUNNING THE APPLICATIONS

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.melwin.ticketbooking.payment.PaymentServiceApplication` class from your IDE.

Alternatively you can run the following command:

```shell
mvn spring-boot:run
```
Application will start on the port ``localhost:8083``
