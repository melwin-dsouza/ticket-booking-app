package com.melwin.ticketbooking.booking.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.melwin.ticketbooking.booking.dto.EventDetailsDTO;
import com.melwin.ticketbooking.booking.dto.ResponseBody;
import com.melwin.ticketbooking.booking.dto.TicketBulkDTO;
import com.melwin.ticketbooking.booking.dto.TicketDTO;
import com.melwin.ticketbooking.booking.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ticket", description = "Ticket APIs")
@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Operation(summary = "Retrieve all Tickets")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = TicketDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "There are no tickets", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/all")
	public ResponseEntity<ResponseBody> getAllTickets() {
		try {
			List<TicketDTO> allTickets = ticketService.getAllTickets();
			return new ResponseEntity<>(ResponseBody.of(allTickets, HttpStatus.OK), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Retrieve a Ticket by Id", description = "Get Ticket Details by specifying its id. ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = TicketDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{id}")
	public ResponseEntity<ResponseBody> getTicketById(
			@Parameter(description = "ID of Ticket to be retrived", required = true) @PathVariable Long id) {

		return new ResponseEntity<>(ResponseBody.of(ticketService.getTicketById(id), HttpStatus.OK), HttpStatus.OK);

	}

	@Operation(summary = "Create a new Ticket")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Ticket created", content = {
					@Content(schema = @Schema(implementation = TicketDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping
	public ResponseEntity<ResponseBody> createTicket(@RequestBody TicketDTO ticketDTO) {
		TicketDTO ticket = ticketService.createTicket(ticketDTO);
		return new ResponseEntity<>(ResponseBody.of(ticket, HttpStatus.CREATED), HttpStatus.CREATED);

	}

	@Operation(summary = "Create multiple Tickets")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Tickets created", content = {
					@Content(schema = @Schema(implementation = TicketDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping("/multiple")
	public ResponseEntity<ResponseBody> createMultipleTickets(@RequestBody TicketBulkDTO ticketDTO) {
		Integer count = ticketService.createMultipleTickets(ticketDTO);
		return new ResponseEntity<>(ResponseBody.of(count, HttpStatus.CREATED), HttpStatus.CREATED);

	}

	@Operation(summary = "Delete a Ticket by Id")
	@ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteTicket(
			@Parameter(description = "ID of Ticket to be deleted", required = true) @PathVariable Long id) {
		try {
			ticketService.deleteTicket(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Operation(summary = "Update a Ticket by Id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = TicketDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	@PutMapping("/{id}")
	public ResponseEntity<ResponseBody> updateTicket(
			@Parameter(description = "ID of user to be updated", required = true) @PathVariable Long id,
			@RequestBody TicketDTO ticketDTO) {
		return new ResponseEntity<>(ResponseBody.of(ticketService.updateTicket(id, ticketDTO), HttpStatus.OK),
				HttpStatus.OK);

	}

	
	@Operation(summary = "Retrieve Ticket details by event Id", description = "Get Available Ticket Details by specifying its event id. ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = EventDetailsDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{eventId}/details")
	public ResponseEntity<List<EventDetailsDTO>> getTicketAvailabilityDetails(
			@Parameter(description = "ID of event to be retrived", required = true) @PathVariable Long eventId) {
		return new ResponseEntity<>(ticketService.getTicketAvailabilityDetails(eventId), HttpStatus.OK);

	}
}
