package com.business.manager.empleado.exception;

import com.business.manager.empleado.exception.error.ErrorEnum;

public class OperationNotPosibleException extends BaseException {
	private static final long serialVersionUID = 1L;

	public OperationNotPosibleException(ErrorEnum error, Object ... args) {
		super(error, args);
	}
}
