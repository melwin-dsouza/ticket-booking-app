package com.melwin.ticketbooking.payment.exception;

public class PaymentServiceException extends RuntimeException {

    private static final long serialVersionUID = 7303744796795747314L;

    public PaymentServiceException(final String messageKey) {
        super(messageKey);
    }

    public PaymentServiceException(final String messageKey, final Throwable exception) {
        super(messageKey, exception);
    }

    public PaymentServiceException(final Throwable exception) {
        super(exception);
    }

}
