package com.business.manager.empleado.exceptions;

import com.business.manager.empleado.exceptions.errors.ErrorEnum;

public class OperationNotPossibleException extends BaseException {
	private static final long serialVersionUID = 1L;

	public OperationNotPossibleException(ErrorEnum error, Object ... args) {
		super(error, args);
	}
}
