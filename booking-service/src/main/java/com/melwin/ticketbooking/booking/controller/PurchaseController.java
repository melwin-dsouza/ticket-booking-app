package com.melwin.ticketbooking.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melwin.ticketbooking.booking.dto.PurchaseDTO;
import com.melwin.ticketbooking.booking.dto.PurchaseRequest;
import com.melwin.ticketbooking.booking.dto.ResponseBody;
import com.melwin.ticketbooking.booking.dto.TicketDTO;
import com.melwin.ticketbooking.booking.service.PurchaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Purchase", description = "Purchase APIs")
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	@Operation(summary = "Purchase a new Ticket")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Purchase order created", content = {
					@Content(schema = @Schema(implementation = Long.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping
	ResponseEntity<ResponseBody> purchaseTicket(@RequestBody PurchaseRequest request) {
		Long purchaseId = purchaseService.createPurchase(request);
		return new ResponseEntity<>(ResponseBody.of("Purchase Inprogress.", HttpStatus.CREATED, purchaseId),
				HttpStatus.CREATED);

	}

	@Operation(summary = "Retrieve a Purchase by Id", description = "Get Purchase order Details by specifying its id. ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = TicketDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping(path = "{purchaseId}")
	ResponseEntity<ResponseBody> getTicket(
			@Parameter(description = "ID of Purchase order to be retrived", required = true) @PathVariable("purchaseId") Long id) {
		PurchaseDTO purchase = purchaseService.getPurchase(id);
		return new ResponseEntity<>(
				ResponseBody.of("Purchase details successfully retrieved.", HttpStatus.OK, purchase), HttpStatus.OK);

	}

}
