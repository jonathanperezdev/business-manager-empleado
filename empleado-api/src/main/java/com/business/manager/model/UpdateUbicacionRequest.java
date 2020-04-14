package com.business.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateUbicacionRequest {
    private Integer idUbicacion;
    private List<Long> idEmpleados;
}
