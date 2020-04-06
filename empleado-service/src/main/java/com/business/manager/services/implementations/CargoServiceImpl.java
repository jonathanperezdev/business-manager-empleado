package com.business.manager.services.implementations;

import com.business.manager.dao.entities.Cargo;
import com.business.manager.dao.repositories.CargoRepository;
import com.business.manager.model.CargoModel;
import com.business.manager.services.CargoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class CargoServiceImpl implements CargoService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CargoRepository cargoRepository;

	@Override
	public CargoModel toModel(Cargo cargo) {
		return modelMapper.map(cargo, CargoModel.class);
	}

	@Override
	public JpaRepository<Cargo, Long> getRepository() {
		return cargoRepository;
	}
}
