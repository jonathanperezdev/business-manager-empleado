package com.business.manager.exception.error;

public enum ErrorEnum {
	//Empleado
	EMPLEADO_NOT_FOUND("No se encontro el empleado con el tipo [%s] y numero [%s] de documento"),
	EMPLEADOS_NOT_FOUND("No se encontraron empleados con  nombres[%s] y apellidos[%s] o ya estan asignados a la ubicacion"),
	DELETE_EMPLEADO_NOT_POSIBLE("%s no puede ser eliminado, tiene empleados asociados "),
	EMPLEADO_OFICINA("El empleado tiene un cargo de Operario u Oficial, no puede ser asociado a una oficina"),
	EMPLEADO_OBRA("Un Operario u Oficial solo puede ser asociado a una obra"),
	EMPLEADOS_NOT_FOUND_BY_CARGO("No se encontraron empleados con el cargo [%s] "),
	EMPLEADO_DUPLICATE_TIPO_AND_NUMBER_DOC("Ya existe un empleado con tipo [%s] y numero [%s] de documento"),
	EMPLEADO_ALREADY_ASSIGNED("El empleado ya esta asignado como %s"),
	
	//Cargo
	CARGO_BY_NAME_NOT_FOUND("No existe un cargo con el nombre %s"),
	CARGOS_NOT_FOUND("No existen cargos"),
	
	//Ubicaciones
	UBICACIONES_NOT_FOUND("No existen ubicaciones"),

	//Tipo Documento
	TIPO_DOCUMENTOS_NOT_FOUND("No existen tipos de docuemntos")
	;
	
	private String message;
	
	private ErrorEnum(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
