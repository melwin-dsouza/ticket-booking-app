package com.melwin.ticketbooking.event.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.melwin.ticketbooking.event.exception.ApiRequestException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
		case 400:
			return new ApiRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to Fetch Ticket details");
		case 404:
			throw new ApiRequestException(HttpStatus.NOT_FOUND, "Ticket Not found");
		default:
			return new ApiRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "Error");
		}
	}

}
