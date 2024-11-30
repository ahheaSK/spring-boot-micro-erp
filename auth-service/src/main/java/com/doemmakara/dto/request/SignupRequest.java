package com.doemmakara.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignupRequest {

	@NotBlank(message = "Username is requested")
	private String username;

	@NotBlank(message = "Password is requested")
	private String password;

	@NotBlank(message = "First Name is requested")
	private String firstName;

	@NotBlank(message = "Last Name is requested")
	private String lastName;

	@NotBlank(message = "email is requested")
	@Email(message = "Email should be valid")
	private String email;

	@NotEmpty(message = "Role must not be empty")
	private Set<String> role;

}
