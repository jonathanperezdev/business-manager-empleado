package com.business.manager.empleado.empleado.models;

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
    private Integer tipoDocumento;

    @NonNull
    private String numeroDocumento;
    
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
    private Integer ubicacion;
    private String riesgoLaboral;

    //Properties not mapped with Entity
    private UbicacionModel ubicacionModel;
    private TipoDocumentoModel tipoDocumentoModel;

}
