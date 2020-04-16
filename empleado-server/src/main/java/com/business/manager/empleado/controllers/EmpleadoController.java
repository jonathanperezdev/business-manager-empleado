package com.business.manager.empleado.controllers;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import com.business.manager.empleado.empleado.model.EmpleadoModel;
import com.business.manager.empleado.empleado.model.UpdateUbicacionRequest;
import com.business.manager.empleado.services.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empleado/v1/api")
class EmpleadoController {

    private static final Logger LOG = LoggerFactory.getLogger(EmpleadoController.class);
    private EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/empleado/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    EmpleadoModel getEmpleado(@PathVariable Long id) {
        return empleadoService.findEmpleado(id);
    }
    
    @GetMapping("/empleados/search")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<EmpleadoModel> getEmpleadoBySearchCriteria(
            @Valid @RequestParam Integer tipoDocumento,
            @Valid @RequestParam String numeroDocumento,
    		@Valid @RequestParam String nombres,
    		@Valid @RequestParam String apellidos) {
    	return empleadoService.search(tipoDocumento, numeroDocumento, nombres, apellidos);
    }
    
    @GetMapping("/empleados/searchWithTipoUbicacion")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<EmpleadoModel> getEmpleadoBySearchWithTipoUbicacion(
    		@Valid @RequestParam Integer idUbicacion,
            @Valid @RequestParam Integer tipoDocumento,
            @Valid @RequestParam String numeroDocumento,
    		@Valid @RequestParam String nombres, 
    		@Valid @RequestParam String apellidos) {
    	return empleadoService.searchWithTipoUbicacion(
    			idUbicacion,
    			tipoDocumento,
    			numeroDocumento,
    			nombres, 
    			apellidos);
    }
    
    @GetMapping("/empleados/{cargo}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<EmpleadoModel> getEmpleadosByCargo(@PathVariable String cargo) {
    	return empleadoService.searchByCargo(cargo);
    }
    
    @GetMapping("/empleado/ubicacion/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<EmpleadoModel> getEmpleadoBySearchCriteria(@PathVariable Integer id) {
    	return empleadoService.findByUbicacion(id);
    }
    
    @PostMapping("/empleado")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    EmpleadoModel createEmpleado(@Valid @RequestBody EmpleadoModel empleado) throws URISyntaxException {
        LOG.info("Request para crear un Empleado: {}", empleado);
        return empleadoService.upsertEmpleado(empleado);
    }
    
    @PutMapping("/empleados/ubicacion")
    @ResponseStatus(HttpStatus.OK)
    void updateUbicacion(@Valid @RequestBody UpdateUbicacionRequest request) {
        LOG.info("Actualizando empleados con ubicacion {}", request.getIdUbicacion());
        empleadoService.updateUbicacion(request.getIdEmpleados(), request.getIdUbicacion());
    }    

    @PutMapping("/empleado/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    EmpleadoModel updateEmpleado(@PathVariable Long id, @Valid @RequestBody EmpleadoModel empleado) {
        LOG.info("Request para actualizar un empleado: {}", empleado);
        return empleadoService.updateEmpleado(id, empleado);
    }

    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<?> deleteEmpleado(@PathVariable Long id) {
        LOG.info("Reuqest para eliminar un Empleado: {}", id);
        empleadoService.deleteEmpleado(id);
        return ResponseEntity.ok().build();
    }
}
