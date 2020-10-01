
package com.cashcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException{
	private static final long serialVersionUID = 2131318742231475555L;

	public ApplicationException(String message) {
	        super(message);
	    }

	public ApplicationException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
