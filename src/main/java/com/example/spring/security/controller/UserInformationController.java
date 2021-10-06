/**
 * 
 */
package com.example.spring.security.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.security.model.UserData;
import com.example.spring.security.service.UserInformationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ramesh_Mamidala
 *
 */
@RestController
@RequestMapping("api/v1")
@Slf4j
@Tag(name = "Users Information.", description = "Users Information API.")
@SecurityRequirement(name = "bearerAuth")
public class UserInformationController {

	private UserInformationService userInformationService;

	public UserInformationController(UserInformationService userInformationService) {
		this.userInformationService = userInformationService;
	}

	@Operation(summary = "Retrieve available active Users Information.", description = "Fetches the active users information.", tags = {
			"Users Information." })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the users information.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserData.class)))),
			@ApiResponse(responseCode = "404", description = "No Users information found.") })
	@GetMapping("/users")
	public ResponseEntity<List<UserData>> getAllUsersInformation(
			@Parameter(description = "To read the httpheaders inforation.") @RequestHeader HttpHeaders httpHeaders) {

		log.info("Fetching all the active users information...");
		List<UserData> usersData = userInformationService.getAllActiveUsersInformation();

		if (usersData == null || usersData.isEmpty()) {
			log.error("No Users Information found..");
			return new ResponseEntity<List<UserData>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserData>>(usersData, HttpStatus.OK);
	}

	@Operation(summary = "Retrieve the User Information by an Id", description = "Retrieve the user information for the requested userId.", tags = {"Users Information."})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the specific user information.", content = @Content(schema = @Schema(implementation = UserData.class))),
			@ApiResponse(responseCode = "404", description = "User Information not found.")
	})
	@GetMapping("/users/{id}")
	public ResponseEntity<UserData> getUserInformationById(
			@Parameter(description = "User Information Id from which User Information object will retrieve. Cannot be empty.", required = true) @PathVariable("id") Long id) {
		
		log.info("Fetching the requested User Information with id {}", id);
		UserData userData = userInformationService.getUserInformationById(id);
		return new ResponseEntity<UserData>(userData, HttpStatus.OK);

	}

	@Operation(summary = "Adds User's Information.", description = "Add user's information and persist in the database table.", tags = {
			"Users Information." })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Successfully created users information.", content = @Content(schema = @Schema(implementation = UserData.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input."),
			@ApiResponse(responseCode = "409", description = "User information already exists.") })
	@PostMapping("/user")
	public ResponseEntity<String> addUserInformation(
			@Parameter(description = "User information to add. Cannot be null or empty.", required = true, schema = @Schema(implementation = UserData.class)) @Valid @RequestBody UserData userData) {

		log.info("Adding user information with the following details {}", userData);
		userInformationService.addUserInformation(userData);
		return new ResponseEntity<String>("New User Information is created successfully.", HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update an existing user information by passing an Id.", description = "Update an existing user information for the requested userId.", tags = {"Users Information."})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the user information."),
			@ApiResponse(responseCode = "400", description = "Invalid input."),
			@ApiResponse(responseCode = "404", description = "User Information not found."),
			@ApiResponse(responseCode = "405", description = "Validation exception.")
	})
	@PutMapping("/users/{id}")
	public ResponseEntity<String> updateUserInformation(
			@Parameter(description = "Id of the user information to update. Cannot be null or empty.", required = true) @PathVariable("id") Long id,
			@Parameter(description = "User information to update. Cannot be null or empty.", required = true, schema = @Schema(implementation = UserData.class)) @Valid @RequestBody UserData userData) {
		
		log.info("Fetching and updating user information with id {}", id);
		userInformationService.updateUserInformation(id, userData);
		return new ResponseEntity<String>("User information is updated successfully for the requested id:::"+id, HttpStatus.OK);
		
	}
	
	@Operation(summary = "Update an existing user's password by passing an Id.", description = "Update an existing user's password for the requested userId.", tags = {"Users Information."})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the user's password."),
			@ApiResponse(responseCode = "400", description = "Invalid input."),
			@ApiResponse(responseCode = "404", description = "User Information not found."),
			@ApiResponse(responseCode = "405", description = "Validation exception.")
	})
	@PatchMapping("/users/password/{id}")
	public ResponseEntity<String> updateUserPassword(
			@Parameter(description = "Id of the user's information to update it's password. Cannot be null or empty.", required = true) @PathVariable("id") Long id,
			@Parameter(description = "User information to update it's password. Cannot be null or empty.", required = true, schema = @Schema(implementation = UserData.class)) @Valid @RequestBody UserData userData
			) {
		log.info("Fetching and updating user' password for the requested id {}", id);
		userInformationService.updateUserPassword(id, userData);
		return new ResponseEntity<String>("User's password is updated successfully for the requested id:::"+id, HttpStatus.OK);
		
	}
	
	@Operation(summary = "Update an existing user's role by passing an Id.", description = "Update an existing user's role for the requested userId.", tags = {"Users Information."})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the user's role."),
			@ApiResponse(responseCode = "400", description = "Invalid input."),
			@ApiResponse(responseCode = "404", description = "User Information not found."),
			@ApiResponse(responseCode = "405", description = "Validation exception.")
	})
	@PatchMapping("/users/role/{id}")
	public ResponseEntity<String> updateUserRole(
			@Parameter(description = "Id of the user's information to update it's role. Cannot be null or empty.", required = true) @PathVariable("id") Long id,
			@Parameter(description = "User information to update it's role. Cannot be null or empty.", required = true, schema = @Schema(implementation = UserData.class)) @Valid @RequestBody UserData userData
			) {
		log.info("Fetching and updating user's role for the requested id {}", id);
		userInformationService.updateUserRole(id, userData);
		return new ResponseEntity<String>("User's role is updated successfully for the requested id:::"+id, HttpStatus.OK);
		
	}
	
	@Operation(summary = "Deletes the User Information by an Id", description = "Deletes the user information for the requested userId.", tags = {"Users Information."})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted the requested user information."),
			@ApiResponse(responseCode = "404", description = "User Information not found.")
	})
	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUserInformationById(@Parameter(description = "User Information Id from which User Information object will delete. Cannot be empty.", required = true) @PathVariable("id") Long id) {
		
		log.info("Deleting the requested user information with id {}", id);
		userInformationService.deleteUserInformation(id);
		return new ResponseEntity<String>("User information is deleted successfully for the requested id:::"+id, HttpStatus.OK);
		
	}

}
