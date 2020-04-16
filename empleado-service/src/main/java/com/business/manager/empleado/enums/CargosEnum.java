package com.business.manager.empleado.enums;

public enum CargosEnum {
	OFICIAL("Oficial"),
	OPERARIO("Operario");
	
	private String nombre;
	
	private CargosEnum(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
