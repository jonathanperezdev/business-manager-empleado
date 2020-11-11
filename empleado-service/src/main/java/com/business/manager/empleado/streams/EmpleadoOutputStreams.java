package com.business.manager.empleado.streams;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EmpleadoOutputStreams {

    String EMPLEADO_OUTPUT = "empleados-out";
    @Output(EMPLEADO_OUTPUT)
    MessageChannel outboundEmpleados();

    String UBICACION_OUTPUT = "ubicaciones-out";
    @Output(UBICACION_OUTPUT)
    MessageChannel outboundUbicaciones();
}
