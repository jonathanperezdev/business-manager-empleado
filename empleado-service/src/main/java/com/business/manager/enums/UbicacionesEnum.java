package com.business.manager.enums;

public enum UbicacionesEnum {
	OBRA("OBRA");
	
	private String nombre;
	
	private UbicacionesEnum(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
