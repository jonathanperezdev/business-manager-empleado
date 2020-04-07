package com.business.manager.services.implementations;

import com.business.manager.dao.entities.Cargo;
import com.business.manager.dao.repositories.CargoRepository;
import com.business.manager.dao.repositories.EmpleadoRepository;
import com.business.manager.exception.NoDataFoundException;
import com.business.manager.exception.OperationNotPosibleException;
import com.business.manager.exception.error.ErrorEnum;
import com.business.manager.model.CargoModel;
import com.business.manager.services.CargoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Objects;


@Service
public class CargoServiceImpl implements CargoService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Override
	public void deleteCargo(Long id) {
		if(!CollectionUtils.isEmpty(empleadoRepository.findByCargoOrderByCargo(id))) {
			throw new OperationNotPosibleException(ErrorEnum.DELETE_EMPLEADO_NOT_POSIBLE, "El cargo");
		}

		delete(id);
	}

	@Override
	public Cargo findByNombre(String nombre)	 {
		Cargo cargo = cargoRepository.findByNombre(nombre);

		if(Objects.isNull(cargo)) {
			throw new NoDataFoundException(ErrorEnum.CARGO_BY_NAME_NOT_FOUND, nombre);
		}

		return cargo;
	}

	@Override
	public JpaRepository<Cargo, Long> getRepository() {
		return cargoRepository;
	}

	@Override
	public CargoModel toModel(Cargo cargo) {
		return modelMapper.map(cargo, CargoModel.class);
	}

	@Override
	public Cargo toEntity(CargoModel model) {
		return modelMapper.map(model, Cargo.class);
	}
}
