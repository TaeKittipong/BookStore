package com.kittipong.assignment.feature.user.controller;


import com.kittipong.assignment.common.datasource.entities.UserEntity;
import com.kittipong.assignment.feature.user.controller.domain.AuthenRequest;
import com.kittipong.assignment.feature.user.controller.domain.UserRequest;
import com.kittipong.assignment.feature.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Validated
public class UserController {

	@Autowired
	private UserService userDetailsService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthenRequest authenticationRequest) throws Exception {
		String token = userDetailsService.login(authenticationRequest);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", "Bearer "+token);
		return ResponseEntity.ok().headers(responseHeaders).build();

	}

	@PostMapping(value = "/users")
	public ResponseEntity<UserEntity> saveUser(@Valid @RequestBody UserRequest user) throws Exception {
		userDetailsService.save(user);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(value = "/users")
	public ResponseEntity<?> deleteUser(@Valid @RequestHeader("Authorization") String authHeader) throws Exception {
		userDetailsService.delete(authHeader);
		return ResponseEntity.ok().build();
	}

}