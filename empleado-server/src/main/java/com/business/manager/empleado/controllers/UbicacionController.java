package com.business.manager.empleado.controllers;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import com.business.manager.empleado.empleado.models.UbicacionModel;
import com.business.manager.empleado.empleado.models.ConfigUbicacionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.business.manager.empleado.services.UbicacionService;

@RestController
@RequestMapping("/${api.empleado.version}/${api.empleado.path}/ubicacion")
class UbicacionController {

    private static final Logger LOG = LoggerFactory.getLogger(UbicacionController.class);
    private UbicacionService ubicacionService;

    public UbicacionController(UbicacionService ubicacionService) {
        this.ubicacionService = ubicacionService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<UbicacionModel> ubicaciones() {
        return ubicacionService.findAllUbicaciones();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel getUbicacion(@PathVariable Integer id) {
        return ubicacionService.findUbicacion(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel createUbicacion(@Valid @RequestBody UbicacionModel ubicacion) throws URISyntaxException {
        LOG.info("Request para crear una Ubicacion: {}", ubicacion);
        return ubicacionService.upsertUbicacion(ubicacion);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UbicacionModel updateUbicacion(@PathVariable Integer id, @Valid @RequestBody UbicacionModel ubicacion) {
        LOG.info("Request para actualizar Ubicacion: {}", ubicacion);
        return ubicacionService.updateUbicacion(id,ubicacion);
    }
    
    @PutMapping("/configuracion")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    void configuraUbicacion(@Valid @RequestBody ConfigUbicacionRequest confUbicReq) {
        LOG.info("Request para configurar Ubicacion: {}", confUbicReq);
        ubicacionService.configuraUbicacion(confUbicReq.getIdUbicacion(), 
        		confUbicReq.getIngenieroACargo(), 
        		confUbicReq.getOficialACargo());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUbicacion(@PathVariable Integer id) {
        LOG.info("Request para eliminar un Ubicacion: {}", id);
        ubicacionService.deleteUbicacion(id);
    }
}
