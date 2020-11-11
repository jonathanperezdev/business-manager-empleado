package com.business.manager.empleado.empleado.enums;

public enum UbicacionesEnum {
	OBRA("OBRA");
	
	private String nombre;
	
	UbicacionesEnum(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
