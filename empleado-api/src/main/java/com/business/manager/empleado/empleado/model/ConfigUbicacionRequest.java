package com.business.manager.empleado.empleado.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfigUbicacionRequest{
	private Integer idUbicacion;
	private Long ingenieroACargo;
	private Long oficialACargo;
}
