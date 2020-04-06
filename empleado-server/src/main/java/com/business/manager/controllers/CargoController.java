package com.business.manager.controllers;

import com.business.manager.model.CargoModel;
import com.business.manager.services.CargoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/empleado/v1/api")
@Slf4j
class CargoController {

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
}
