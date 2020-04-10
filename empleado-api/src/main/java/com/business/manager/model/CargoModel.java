package com.business.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CargoModel{
	@NonNull
    private Integer id;
	
	@NonNull
	private String nombre;
	
	private String descripcion;
	private String funciones;
}
