package com.business.manager.empleado.empleado.enums;

public enum RiesgoLaboral {
    RIESGO_LABORAL_NIVEL_1("Nivel 1"),
    RIESGO_LABORAL_NIVEL_2("Nivel 2"),
    RIESGO_LABORAL_NIVEL_3("Nivel 3"),
    RIESGO_LABORAL_NIVEL_4("Nivel 4"),
    RIESGO_LABORAL_NIVEL_5("Nivel 5");

    private String descripcion;

    RiesgoLaboral(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
