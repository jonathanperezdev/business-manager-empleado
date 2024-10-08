package com.business.manager.empleado.dao.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.business.manager.empleado.empleado.enums.RiesgoLaboral;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@Entity
@Table(name = "EMPLEADO")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NonNull
    @Enumerated(EnumType.STRING)
    private RiesgoLaboral riesgoLaboral;
    
    private String direccion;
    private String numeroCelular;
    private String telefono;
    
    private String contactoEmergenciaNombres;
    private String contactoEmergenciaApellidos;
    private String contactoEmergenciaTelefono;
    
    private Integer cargo;
    private Integer ubicacion;
        
}

