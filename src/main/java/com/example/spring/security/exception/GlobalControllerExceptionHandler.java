/**
 * 
 */
package com.example.spring.security.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Ramesh_Mamidala
 *
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserInformationNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserInformationNotFoundException(UserInformationNotFoundException ex, WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getLocalizedMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
		
	}

}
