package com.doemmakara.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@Data
@RequiredArgsConstructor
public class ApiException extends RuntimeException {

	private final HttpStatus status;

	private final String message;

}
