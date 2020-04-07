package com.business.manager.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfigUbicacionRequest{
	private Long idUbicacion;
	private Long ingenieroACargo;
	private Long oficialACargo;
	private List<Long> idEmpleados;
}
