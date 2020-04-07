package com.business.manager.exception;

import com.business.manager.exception.error.ErrorEnum;

public class OperationNotPosibleException extends BaseException {
	private static final long serialVersionUID = 1L;

	public OperationNotPosibleException(ErrorEnum error, Object ... args) {
		super(error, args);
	}
}
