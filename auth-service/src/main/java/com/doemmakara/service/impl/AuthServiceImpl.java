package com.doemmakara.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doemmakara.config.jwt.JwtUtils;
import com.doemmakara.config.security.AuthUser;
import com.doemmakara.dto.request.LoginRequest;
import com.doemmakara.dto.request.SignupRequest;
import com.doemmakara.dto.response.LoginResponse;
import com.doemmakara.entity.Role;
import com.doemmakara.entity.User;
import com.doemmakara.exception.ApiException;
import com.doemmakara.repository.RoleRepository;
import com.doemmakara.repository.UserRepository;
import com.doemmakara.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;
	
	private final JwtUtils jwtUtils;
	
	private final AuthenticationManager authenticationManager;


	@Override
	public User createUser(SignupRequest request) {
		// TODO Auto-generated method stub
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Username is already taken!");
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Email is already taken!");
		}
		
		// Create new user's account
				User user = new User(
						request.getUsername(), 
						request.getEmail(),
						passwordEncoder.encode(request.getPassword()),
						false);

				Set<String> strRoles = request.getRole();
				Set<Role> roles = new HashSet<>();

				if (strRoles == null) {
					Role userRole = roleRepository.findByName("ADMIN")
							.orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Role is not found!"));
					roles.add(userRole);
				} else {
					strRoles.forEach(role -> {
						Role adminRole = roleRepository.findByName(role)
								.orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, role + " Role is not found!"));
						roles.add(adminRole);
					});
				}

				user.setFirstName(request.getFirstName());
				user.setLastName(request.getLastName());
				user.setRoles(roles);
				User save = userRepository.save(user);
				return save;
	}


	@Override
	public LoginResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		AuthUser userPrincipal = (AuthUser) authentication.getPrincipal();

		// Fetch the user from the database with their Branches
		User user = userRepository.findByUsername(userPrincipal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		LoginResponse loginResponse = new LoginResponse();

		String generateJwtToken = jwtUtils.generateJwtToken(userPrincipal.getUsername());

		loginResponse.setJwt(generateJwtToken);
		loginResponse.setToken(generateJwtToken);
		loginResponse.setUserName(userPrincipal.getUsername());


		return loginResponse;

	}

}
