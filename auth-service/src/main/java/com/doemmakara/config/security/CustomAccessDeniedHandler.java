package com.doemmakara.config.security;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper objectMapper;

	public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// Reset the response to ensure no default error message is sent
		response.reset();
		// Set status and content type
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");

		// Create response body
		Map<String, Object> body = new HashMap<>();
		body.put("status", HttpStatus.FORBIDDEN.value());
		body.put("error", "Forbidden");
		body.put("message", "You do not have permission to perform this action.");
		body.put("path", request.getRequestURI());
		body.put("method", request.getMethod());
		// Write response body
		objectMapper.writeValue(response.getOutputStream(), body);
		response.flushBuffer(); // Ensure response is sent immediately
	}
}