package com.business.manager.exception.error;

public enum ErrorEnum {
	//Empleado
	EMPLEADO_NOT_FOUND("No se encontro el empleado con el id[%d]"),
	EMPLEADOS_NOT_FOUND("No se encontraron empleados con  nombres[%s] y apellidos[%s]"),
	DELETE_EMPLEADO_NOT_POSIBLE("%s no puede ser eliminado, tiene empleados asociados "),
	EMPLEADO_OFICINA("El empleado tiene un cargo diferente a Operario u Oficial, no puede ser asociado a una obra"),
	EMPLEADO_OBRA("Un Operario u Oficial solo puede ser asociado a una obra"),
	EMPLEADOS_NOT_FOUND_BY_CARGO("No se encontraron empleados con el cargo [%s] "),
	
	//Cargo
	CARGO_BY_NAME_NOT_FOUND("No existe un cargo con el nombre %s"),
	
	//Ubicaciones
	UBICACIONES_NOT_FOUND("No existen ubicaciones")
	;
	
	private String message;
	
	private ErrorEnum(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
