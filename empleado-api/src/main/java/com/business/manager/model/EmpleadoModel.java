package com.business.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class EmpleadoModel {
	
	@NonNull
    private Long id;
    
    @NonNull
    private String nombres;
    
    @NonNull
    private String apellidos;
    
    @NonNull
    private BigDecimal salario;
    
    private String direccion;
    private String numeroCelular;
    private String telefono;
    
    private String contactoEmergenciaNombres;
    private String contactoEmergenciaApellidos;
    private String contactoEmergenciaTelefono;
    
    private Integer cargo;
    private Integer idUbicacion;
    
    private UbicacionModel ubicacion;

}
