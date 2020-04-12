package com.business.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UbicacionModel {

	@NonNull
	private Integer id;
	
	@NonNull
	private String nombre;
	
	@NonNull
	private String direccion;
	
	@NonNull
	private String tipo;
	
	private String descripcion;
	private Long ingenieroACargo;
	private Long oficialACargo;
	
	private EmpleadoModel ingeniero;
	private EmpleadoModel oficial;
}
