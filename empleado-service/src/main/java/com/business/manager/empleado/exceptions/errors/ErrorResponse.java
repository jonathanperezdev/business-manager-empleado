package com.business.manager.empleado.exceptions.errors;

import com.business.manager.empleado.exceptions.BaseException;
import lombok.Getter;

@Getter
public class ErrorResponse {
	private String message;
	private String key;
	
	private ErrorResponse(String message, String key) {
		this.message = message;
		this.key = key;
	}
	
	public static ErrorResponse newErrorResponse(BaseException ex) {
		return new ErrorResponse(ex.getMessage(), ex.getError().name());
	}

}
