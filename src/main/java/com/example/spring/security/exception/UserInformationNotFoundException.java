/**
 * 
 */
package com.example.spring.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ramesh_Mamidala
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserInformationNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -1252081506210481952L;

	public UserInformationNotFoundException(String message) {
		super(message);
	}

}
