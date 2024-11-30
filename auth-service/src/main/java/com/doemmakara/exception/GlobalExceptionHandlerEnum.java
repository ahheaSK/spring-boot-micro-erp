package com.doemmakara.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandlerEnum {
	@ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Extracting the unrecognized value and the accepted values
        String errorMessage = ex.getMessage();
        String unrecognizedValue = extractUnrecognizedValue(errorMessage);
        String acceptedValues = extractAcceptedValues(errorMessage);

        // Building the custom response message
        String customMessage = String.format("Enum '%s' Not Found in %s", unrecognizedValue, acceptedValues);

        // Creating the response body
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "NOT_FOUND");
        errorResponse.put("message", customMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Method to extract the unrecognized value from the exception message
    private String extractUnrecognizedValue(String message) {
        Pattern pattern = Pattern.compile("from String \"(.*?)\"");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Unknown";
    }

    // Method to extract the accepted values from the exception message
    private String extractAcceptedValues(String message) {
        Pattern pattern = Pattern.compile("accepted for Enum class: \\[(.*?)\\]");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return "[" + matcher.group(1) + "]";
        }
        return "[]";
    }
}
