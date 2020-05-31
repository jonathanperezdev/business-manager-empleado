package com.business.manager.empleado.exceptions;

import com.business.manager.empleado.exceptions.errors.ErrorEnum;

public class NoDataFoundException extends BaseException {
	
	public NoDataFoundException(ErrorEnum error, Object ... args) {
		super(error, args);
	}

}
