package com.melwin.ticketbooking.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.notification.converter.NotificationConverter;
import com.melwin.ticketbooking.notification.dto.NotificationDetails;
import com.melwin.ticketbooking.notification.entity.Notification;
import com.melwin.ticketbooking.notification.repository.NotificationRepository;

import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.Body;
import software.amazon.awssdk.services.sesv2.model.Content;
import software.amazon.awssdk.services.sesv2.model.Destination;
import software.amazon.awssdk.services.sesv2.model.EmailContent;
import software.amazon.awssdk.services.sesv2.model.Message;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SesV2Exception;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	SesV2Client sesClient;

	public void notifyUser(NotificationDetails details) {
		Notification notification = NotificationConverter.toEntity(details);
		StringBuilder str=new StringBuilder();
		if("SUCCESS".equals(details.getBookingStatus())){
			str.append("Booking is Successfull for the Event: ").append(details.getEventName());
		}else if("CANCELLED".equals(details.getBookingStatus())){
			str.append("Booking has been cancelled for the Event: ").append(details.getEventName());
		}else {
			str.append("Booking is Unsuccessfull for the Event: ").append(details.getEventName());
		}
		notification.setMessage(str.toString());
		Notification savedNotification = notificationRepository.save(notification);
		//send Notification
		publishToSes(savedNotification);
	}

	
	private void publishToSes(Notification notification) {
        String sender = "melwinvd1997@gmail.com";
        String recipient = "vishaldsouza18@gmail.com";//notification.getUserEmail();
        
        String subject = "Ticket Status for the Event `"+notification.getEventName()+"`";
        String bodyHTML = prepareEmailBody(notification);     
        
        try {
            send(sesClient, sender, recipient, subject, bodyHTML.toString());
            System.out.println("Done");

        } catch (MessagingException e) {
            e.getStackTrace();
        }
		

	}

	private String prepareEmailBody(Notification notification) {
		StringBuilder bodyHTML=new StringBuilder();
        bodyHTML.append("<html><head></head><body style=\"font-family: Arial, sans-serif;\"><h2>");
        bodyHTML.append(notification.getMessage());
        bodyHTML.append("</h2>");
        bodyHTML.append("<table style=\"width: 100%; border-collapse: collapse;\">\r\n"
        		+ "        <tr><td><strong>Event Name:</strong></td><td>");
        bodyHTML.append(notification.getEventName());
        bodyHTML.append("</td></tr><tr>\r\n"
        		+ "            <td><strong>Event Date:</strong></td><td>");
        bodyHTML.append(notification.getEventDate());
        bodyHTML.append("</td></tr><tr>\r\n"
        		+ " <td><strong>Event Location:</strong></td><td>");
        bodyHTML.append(notification.getEventLocation());
        bodyHTML.append("</td></tr><tr>\r\n"
        		+ " <td><strong>User Name:</strong></td><td>");
        bodyHTML.append(notification.getUserName());
        bodyHTML.append("</td></tr><tr>\r\n"
        		+ " <td><strong>Ticket Type:</strong></td><td>");
        bodyHTML.append(notification.getTicketType());
        bodyHTML.append("</td></tr><tr>\r\n"
        		+ "<td><strong>Number of Tickets:</strong></td><td>");
        bodyHTML.append(notification.getTicketQty());
        bodyHTML.append("</td></tr><tr>\r\n"
        		+ " <td><strong>Payment Amount:</strong></td><td>$");
        bodyHTML.append(notification.getPaymentAmount());
        bodyHTML.append("</td></tr></table>\r\n"
        		+ "</body></html>");
		return bodyHTML.toString();
	}
	
	public static void send(SesV2Client client,
            String sender,
            String recipient,
            String subject,
            String bodyHTML) throws MessagingException {

        Destination destination = Destination.builder()
                .toAddresses(recipient)
                .build();

        Content content = Content.builder()
                .data(bodyHTML)
                .build();

        Content sub = Content.builder()
                .data(subject)
                .build();

        Body body = Body.builder()
                .html(content)
                .build();

        Message msg = Message.builder()
                .subject(sub)
                .body(body)
                .build();
        EmailContent emailContent = EmailContent.builder()
                .simple(msg)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .content(emailContent)
                .fromEmailAddress(sender)
                .build();

        try {
            System.out.println("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
            client.sendEmail(emailRequest);

        } catch (SesV2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

}
