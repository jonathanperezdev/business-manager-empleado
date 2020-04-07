package com.business.manager.controllers;

import com.business.manager.model.CargoModel;
import com.business.manager.services.CargoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/empleado/v1/api")
@Slf4j
public class CargoController {

	private CargoService cargoService;

	public CargoController(CargoService cargoService) {
		this.cargoService = cargoService;
	}

	@GetMapping("/cargos")
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody
	List<CargoModel> cargos() {
		return cargoService.findAll();
	}

	@GetMapping("/cargo/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	CargoModel getCargo(@PathVariable Long id) {
		return cargoService.findById(id);
	}

	@PostMapping("/cargo")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	CargoModel createCargo(@Valid @RequestBody CargoModel cargo) throws URISyntaxException {
		log.info("Request para crear un Cargo: {}", cargo);
		return cargoService.create(cargo);
	}

	@PutMapping("/cargo/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	CargoModel updateCargo(@PathVariable Long id, @Valid @RequestBody CargoModel cargo) {
		log.info("Request para actualizar Cargo: {}", cargo);
		return cargoService.update(id,cargo);
	}

	@DeleteMapping("/cargo/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCargo(@PathVariable Long id) {
		log.info("Request para eliminar un cargo: {}", id);
		cargoService.deleteCargo(id);
	}
}
