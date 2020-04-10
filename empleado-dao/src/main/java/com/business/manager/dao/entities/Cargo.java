package com.business.manager.dao.entities;

import javax.persistence.Entity;
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
@Table(name = "CARGO")
public class Cargo{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@NonNull
	private String nombre;
	
	private String descripcion;
	private String funciones;
}
