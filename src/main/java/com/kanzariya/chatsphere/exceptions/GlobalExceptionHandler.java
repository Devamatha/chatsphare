package com.kanzariya.chatsphere.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Map<String, Object> createErrorResponse(HttpStatus status, String message, String path) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);
		body.put("path", path);
		return body;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("error", "Not Found");
		body.put("message", "API URL not found");
		body.put("path", request.getRequestURI());

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex,
			HttpServletRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
		body.put("error", "Method Not Allowed");
		body.put("message", "This HTTP method is not allowed for the requested URL");
		body.put("path", request.getRequestURI());

		return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			WebRequest request) {
		Map<String, Object> body = createErrorResponse(HttpStatus.BAD_REQUEST,
				"Missing parameter: " + ex.getParameterName(), request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
		Map<String, Object> body = createErrorResponse(HttpStatus.BAD_REQUEST,
				"Validation error: " + ex.getBindingResult().getFieldError().getDefaultMessage(),
				request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, WebRequest request) {
		Map<String, Object> body = createErrorResponse(HttpStatus.BAD_REQUEST,
				"Missing path variable: " + ex.getVariableName(), request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
		Map<String, Object> body = createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(),
				request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> dataIntegirty(DataIntegrityViolationException dataIntegrityViolationException,
			HttpServletRequest httpServletRequest, WebRequest request) {
		Map<String, Object> response = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				"duplicate email or duplicate mobile number", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}


	

	@ExceptionHandler(Unauthorized.class)
	public ResponseEntity<Object> handleUnauthorizedException(Unauthorized ex, HttpServletRequest request,
			WebRequest webRequest) {

		Map<String, Object> response = createErrorResponse(HttpStatus.UNAUTHORIZED,
				"Unauthorized to access this resource", webRequest.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(OTPValidationException.class)
	public ResponseEntity<String> handleOTPException(OTPValidationException e) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

}

