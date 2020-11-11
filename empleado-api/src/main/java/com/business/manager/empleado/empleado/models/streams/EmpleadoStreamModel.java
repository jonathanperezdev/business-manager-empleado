package com.business.manager.empleado.empleado.models.streams;

import com.business.manager.empleado.empleado.enums.StreamAction;
import com.business.manager.empleado.empleado.models.EmpleadoModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class EmpleadoStreamModel {
    private StreamAction action;
    private EmpleadoModel empleado;
}
