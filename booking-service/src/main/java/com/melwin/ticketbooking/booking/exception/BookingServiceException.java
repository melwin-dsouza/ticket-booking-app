package com.melwin.ticketbooking.booking.exception;

public class BookingServiceException extends RuntimeException {

	private static final long serialVersionUID = 7303744796795747314L;

	public BookingServiceException(final String messageKey) {
		super(messageKey);
	}

	public BookingServiceException(final String messageKey, final Throwable exception) {
		super(messageKey, exception);
	}

	public BookingServiceException(final Throwable exception) {
		super(exception);
	}

}
