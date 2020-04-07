package com.business.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class EmpleadoModel implements BaseModel{
	
	@NonNull
    private Long id;
    
    @NonNull
    private String nombres;
    
    @NonNull
    private String apellidos;
    
    @NonNull
    private Long salario;
    
    private String direccion;
    private String numeroCelular;
    private String telefono;
    
    private String contactoEmergenciaNombres;
    private String contactoEmergenciaApellidos;
    private String contactoEmergenciaTelefono;
    
    private Long cargo;
    private Long idUbicacion;
    
    private UbicacionModel ubicacion;

}
