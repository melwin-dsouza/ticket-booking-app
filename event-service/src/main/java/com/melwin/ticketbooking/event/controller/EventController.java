package com.melwin.ticketbooking.event.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.melwin.ticketbooking.event.dto.EventDTO;
import com.melwin.ticketbooking.event.exception.EventServiceException;
import com.melwin.ticketbooking.event.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Event", description = "Event service APIs")
@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@Operation(summary = "Retrieve all Events Or Filter by event name")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = EventDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "There are no events", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping
	public ResponseEntity<List<EventDTO>> getAllEvents(
			@Parameter(description = "Name of event to be retrieved", required = false) @RequestParam(required = false) String name) {
		try {
			List<EventDTO> allEvents = eventService.getAllEvents(name);
			return new ResponseEntity<>(allEvents, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Retrieve a Event by Id", description = "Get a Event object by specifying its id. The response is Event object with id, name, date, location, perfomer and event status.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = EventDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{id}")
	public ResponseEntity<EventDTO> getEventById(
			@Parameter(description = "ID of event to be retrieved", required = true) @PathVariable Long id) {
		try {
			EventDTO event = eventService.getEventById(id);
			if (event != null)
				return new ResponseEntity<>(event, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Check if the event is Active", description = "Checks if the requested Event is active and return the boolean value.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{id}/isActive")
	public ResponseEntity<Boolean> isEventActive(
			@Parameter(description = "ID of event to be retrieved", required = true) @PathVariable Long id) {
		return new ResponseEntity<>(eventService.isEventActive(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a new Event")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Event created", content = {
					@Content(schema = @Schema(implementation = EventDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping
	public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
		try {
			EventDTO event = eventService.createEvent(eventDTO);
			return new ResponseEntity<>(event, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Delete a Event by Id")
	@ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteEvent(
			@Parameter(description = "ID of event to be deleted", required = true) @PathVariable Long id) {
		try {
			eventService.deleteEvent(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Update a Event by Id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = EventDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateEvent(
			@Parameter(description = "ID of event to be updated", required = true) @PathVariable Long id,
			@RequestBody EventDTO eventDTO) {
		return new ResponseEntity<>(eventService.updateEvent(id, eventDTO), HttpStatus.OK);

	}
	
	@Operation(summary = "Check if the event is Valid", description = "Checks if the requested Event is Present and return the boolean value.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{id}/isValid")
	public ResponseEntity<Boolean> isEventValid(
			@Parameter(description = "ID of event to be checked", required = true) @PathVariable Long id) {
		return new ResponseEntity<>(eventService.isEventValid(id), HttpStatus.OK);
	}

}
