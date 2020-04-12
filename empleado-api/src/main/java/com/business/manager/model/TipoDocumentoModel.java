package com.business.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TipoDocumentoModel {
    @NonNull
    private Integer id;

    @NonNull
    private String nombre;

    @NonNull
    private Integer orden;
}
