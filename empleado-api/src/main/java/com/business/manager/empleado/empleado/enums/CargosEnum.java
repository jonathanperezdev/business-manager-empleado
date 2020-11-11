package com.business.manager.empleado.empleado.enums;

public enum CargosEnum {
	OFICIAL("Oficial"),
	OPERARIO("Operario");
	
	private String nombre;
	
	CargosEnum(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
