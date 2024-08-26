package com.melwin.ticketbooking.event.exception;

public class EventServiceException extends RuntimeException {

    private static final long serialVersionUID = 7303744796795747314L;

    public EventServiceException(final String messageKey) {
        super(messageKey);
    }

    public EventServiceException(final String messageKey, final Throwable exception) {
        super(messageKey, exception);
    }

    public EventServiceException(final Throwable exception) {
        super(exception);
    }

}
