package com.doemmakara.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doemmakara.dto.request.LoginRequest;
import com.doemmakara.dto.request.SignupRequest;
import com.doemmakara.dto.response.LoginResponse;
import com.doemmakara.entity.User;
import com.doemmakara.service.AuthService;

//import io.github.classgraph.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		LoginResponse authenticateUser = authService.authenticateUser(loginRequest);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", "Bearer " + authenticateUser.getJwt());
		responseHeaders.set("Access-Control-Expose-Headers", "Authorization");

		

		// Return the response with headers
		// return ResponseEntity.ok().headers(responseHeaders).build();
		return ResponseEntity.ok(authenticateUser);
	}

	@PostMapping("register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

//		String jwt = authService.createUser(signUpRequest);
//
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.set("Authorization", "Bearer " + jwt);
//
//		return ResponseEntity.ok().headers(responseHeaders).build();
		User user = authService.createUser(signUpRequest);

		return ResponseEntity.ok(user);

	}

}
