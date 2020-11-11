package com.business.manager.empleado.controllers;

import com.business.manager.empleado.empleado.models.TipoDocumentoModel;
import com.business.manager.empleado.services.TipoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.empleado.version}/${api.empleado.path}/")
public class TipoDocumentoController {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @GetMapping("/tipoDocumentos")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<TipoDocumentoModel> tipoDocumentos() {
        return tipoDocumentoService.getAllTipoDocumentos();
    }
}
