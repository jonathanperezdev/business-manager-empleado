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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CargoServiceImpl implements CargoService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private EmpleadoRepository empleadoRepository;

	private Function<Cargo, CargoModel> toCargoModel = cargo -> modelMapper.map(cargo, CargoModel.class);
	private Function<CargoModel, Cargo> toCargoEntity = cargo -> modelMapper.map(cargo, Cargo.class);

	@Override
	public List<CargoModel> findAllCargos() {
		List<CargoModel> listCargosModel = toModel(cargoRepository.findAll());
		if(CollectionUtils.isEmpty(listCargosModel)) {
			throw new NoDataFoundException(ErrorEnum.CARGOS_NOT_FOUND);
		}
		return listCargosModel;
	}

	@Override
	public CargoModel findCargo(Integer id) {
		return toCargoModel.apply(cargoRepository.findById(id).get());
	}

	@Override
	public CargoModel upsertCargo(CargoModel cargoModel) {
		Cargo cargo = cargoRepository.save(toCargoEntity.apply(cargoModel));
		return toCargoModel.apply(cargo);
	}

	@Override
	public CargoModel updateCargo(Integer id, CargoModel cargoModel) {
		cargoModel.setId(id);
		return upsertCargo(cargoModel);
	}

	@Override
	public void deleteCargo(Integer id) {
		if(!CollectionUtils.isEmpty(empleadoRepository.findByCargoOrderByCargo(id))) {
			throw new OperationNotPosibleException(ErrorEnum.DELETE_EMPLEADO_NOT_POSIBLE, "El cargo");
		}

		cargoRepository.deleteById(id);
	}

	@Override
	public Cargo findByNombre(String nombre)	 {
		Cargo cargo = cargoRepository.findByNombre(nombre);

		if(Objects.isNull(cargo)) {
			throw new NoDataFoundException(ErrorEnum.CARGO_BY_NAME_NOT_FOUND, nombre);
		}

		return cargo;
	}

	private List<CargoModel> toModel(List<Cargo> cargos) {
		return cargos
				.stream()
				.map(toCargoModel)
				.collect(Collectors.toList());
	}
}
