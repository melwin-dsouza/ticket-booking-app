package com.melwin.ticketbooking.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter @Setter
public class ResponseBody {

    private String message;
    private HttpStatus status;
    private Object data;
    private ZonedDateTime timestamp;

    public static ResponseBody of(String message, HttpStatus status, Object data) {
        return new ResponseBody(message, status, data, ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
    }
    
    public static ResponseBody of(Object data,HttpStatus status) {
    	return new ResponseBodyBuilder().data(data).status(status).timestamp(ZonedDateTime.now(ZoneId.of("Asia/Kolkata"))).build();
    }
}
