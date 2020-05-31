package com.business.manager.empleado.exceptions.handlers;

import com.business.manager.empleado.exceptions.NoDataFoundException;
import com.business.manager.empleado.exceptions.OperationNotPossibleException;
import com.business.manager.empleado.exceptions.errors.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmpleadoExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmpleadoExceptionHandler.class);

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoDataFoundException.class)
	@ResponseBody
	public ErrorResponse noDataFoundException(NoDataFoundException ex) {
		LOGGER.info("Handling exception class={}", ex.getClass());

		return ErrorResponse.newErrorResponse(ex);
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(OperationNotPossibleException.class)
	@ResponseBody
	public ErrorResponse deleteNotPosibleException(OperationNotPossibleException ex) {
		LOGGER.info("Handling exception class={}", ex.getClass());

		return ErrorResponse.newErrorResponse(ex);
	}
}
