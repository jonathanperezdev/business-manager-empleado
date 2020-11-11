package com.business.manager.empleado.controllers;

import com.business.manager.empleado.empleado.models.CargoModel;
import com.business.manager.empleado.services.CargoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/${api.empleado.version}/${api.empleado.path}/cargo")
@Slf4j
public class CargoController {

	private CargoService cargoService;

	public CargoController(CargoService cargoService) {
		this.cargoService = cargoService;
	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody
	List<CargoModel> cargos() {
		return cargoService.findAllCargos();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	CargoModel getCargo(@PathVariable Integer id) {
		return cargoService.findCargo(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	CargoModel createCargo(@Valid @RequestBody CargoModel cargo) throws URISyntaxException {
		log.info("Request para crear un Cargo: {}", cargo);
		return cargoService.upsertCargo(cargo);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	CargoModel updateCargo(@PathVariable Integer id, @Valid @RequestBody CargoModel cargo) {
		log.info("Request para actualizar Cargo: {}", cargo);
		return cargoService.updateCargo(id,cargo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCargo(@PathVariable Integer id) {
		log.info("Request para eliminar un cargo: {}", id);
		cargoService.deleteCargo(id);
	}
}
