package com.business.manager.exception;

import com.business.manager.exception.error.ErrorEnum;

public class NoDataFoundException extends BaseException {
	
	public NoDataFoundException(ErrorEnum error, Object ... args) {
		super(error, args);
	}

}
