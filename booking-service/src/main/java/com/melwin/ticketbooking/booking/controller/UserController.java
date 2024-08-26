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

import com.melwin.ticketbooking.booking.dto.ResponseBody;
import com.melwin.ticketbooking.booking.dto.UserDTO;
import com.melwin.ticketbooking.booking.exception.BookingServiceException;
import com.melwin.ticketbooking.booking.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "User APIs")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(summary = "Retrieve all Users")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "There are no users", content = {
					@Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/all")
	public ResponseEntity<ResponseBody> getAllUsers() {
		try {
			List<UserDTO> allUsers = userService.getAllUsers();
			return new ResponseEntity<>(ResponseBody.of(allUsers, HttpStatus.OK), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Retrieve a User by Id", description = "Get User Details by specifying its id. ")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/{id}")
	public ResponseEntity<ResponseBody> getUserById(@PathVariable Long id) {
		return new ResponseEntity<>(ResponseBody.of(userService.getUserById(id), HttpStatus.OK), HttpStatus.OK);

	}

	@Operation(summary = "Create a new User")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "User created", content = {
					@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping
	public ResponseEntity<ResponseBody> createUser(@RequestBody UserDTO userDTO) {
		try {
			UserDTO user = userService.createUser(userDTO);
			return new ResponseEntity<>(ResponseBody.of(user, HttpStatus.CREATED), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Delete a User by Id")
	@ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteUser(
			@Parameter(description = "ID of User to be deleted", required = true) @PathVariable Long id) {
		try {
			userService.deleteUser(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Operation(summary = "Update a User by Id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
	@PutMapping("/{id}")
	public ResponseEntity<ResponseBody> updateUser(
			@Parameter(description = "ID of user to be updated", required = true) @PathVariable Long id,
			@RequestBody UserDTO userDTO) {
		return new ResponseEntity<>(ResponseBody.of(userService.updateUser(id, userDTO), HttpStatus.OK), HttpStatus.OK);

	}

}
