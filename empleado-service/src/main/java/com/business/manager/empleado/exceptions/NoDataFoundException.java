package com.business.manager.empleado.exception;

import com.business.manager.empleado.exception.error.ErrorEnum;

public class NoDataFoundException extends BaseException {
	
	public NoDataFoundException(ErrorEnum error, Object ... args) {
		super(error, args);
	}

}
