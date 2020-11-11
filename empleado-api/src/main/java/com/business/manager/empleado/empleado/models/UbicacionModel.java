package com.business.manager.empleado.empleado.models;

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

	//Properties not mapped with Entity
	private EmpleadoModel ingeniero;
	private EmpleadoModel oficial;
}
