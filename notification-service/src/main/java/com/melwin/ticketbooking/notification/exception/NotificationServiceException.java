package com.melwin.ticketbooking.notification.exception;

public class NotificationServiceException extends RuntimeException {

    private static final long serialVersionUID = 7303744796795747314L;

    public NotificationServiceException(final String messageKey) {
        super(messageKey);
    }

    public NotificationServiceException(final String messageKey, final Throwable exception) {
        super(messageKey, exception);
    }

    public NotificationServiceException(final Throwable exception) {
        super(exception);
    }

}
