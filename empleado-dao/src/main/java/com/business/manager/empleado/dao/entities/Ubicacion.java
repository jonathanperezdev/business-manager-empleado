package com.business.manager.empleado.dao.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "UBICACION")
public class Ubicacion {
	
	public enum UbicacionType{
		OBRA,
		OFICINA
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@NonNull
	private String nombre;
	
	@NonNull
	private String direccion;
	
	@NonNull
	@Enumerated(EnumType.STRING)
	private UbicacionType tipo;
	
	private String descripcion;
	
	private Long ingenieroACargo;
	private Long oficialACargo;
}
