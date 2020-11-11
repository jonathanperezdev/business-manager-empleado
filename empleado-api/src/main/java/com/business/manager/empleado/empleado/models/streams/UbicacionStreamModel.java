package com.business.manager.empleado.empleado.models.streams;

import com.business.manager.empleado.empleado.enums.StreamAction;
import com.business.manager.empleado.empleado.models.UbicacionModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class UbicacionStreamModel {
    private StreamAction action;
    private UbicacionModel ubicacion;
}
