package com.business.manager.empleado.services.implementations;

import com.business.manager.empleado.dao.entities.Cargo;
import com.business.manager.empleado.dao.repositories.CargoRepository;
import com.business.manager.empleado.dao.repositories.EmpleadoRepository;
import com.business.manager.empleado.empleado.models.CargoModel;
import com.business.manager.empleado.exceptions.NoDataFoundException;
import com.business.manager.empleado.exceptions.OperationNotPossibleException;
import com.business.manager.empleado.exceptions.errors.ErrorEnum;
import com.business.manager.empleado.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CargoServiceImpl implements CargoService {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	@Qualifier("customConversionService")
	private ConversionService conversionService;

	@Override
	public List<CargoModel> findAllCargos() {
		List<CargoModel> listCargosModel = toModel(cargoRepository.findAllByOrderByIdAsc());
		if(CollectionUtils.isEmpty(listCargosModel)) {
			throw new NoDataFoundException(ErrorEnum.CARGOS_NOT_FOUND);
		}
		return listCargosModel;
	}

	@Override
	public CargoModel findCargo(Integer id) {
		return conversionService.convert(cargoRepository.findById(id).get(), CargoModel.class);
	}

	@Override
	public CargoModel upsertCargo(CargoModel cargoModel) {
		Cargo cargo = cargoRepository.findByNombreIgnoreCase(cargoModel.getNombre());

		if(Objects.nonNull(cargo)) {
			throw new OperationNotPossibleException(ErrorEnum.CARGO_ALREADY_EXIST, cargoModel.getNombre());
		}

		cargo = cargoRepository.save(conversionService.convert(cargoModel, Cargo.class));
		return conversionService.convert(cargo, CargoModel.class);
	}

	@Override
	public CargoModel updateCargo(Integer id, CargoModel cargoModel) {
		cargoModel.setId(id);

		Cargo cargo = cargoRepository.save(conversionService.convert(cargoModel, Cargo.class));
		return conversionService.convert(cargo, CargoModel.class);
	}

	@Override
	public void deleteCargo(Integer id) {
		if(!CollectionUtils.isEmpty(empleadoRepository.findByCargoOrderByCargo(id))) {
			throw new OperationNotPossibleException(ErrorEnum.DELETE_EMPLEADO_NOT_POSIBLE, "El cargo");
		}

		cargoRepository.deleteById(id);
	}

	@Override
	public Cargo findByNombre(String nombre)	 {
		Cargo cargo = cargoRepository.findByNombreIgnoreCase(nombre);

		if(Objects.isNull(cargo)) {
			throw new NoDataFoundException(ErrorEnum.CARGO_BY_NAME_NOT_FOUND, nombre);
		}

		return cargo;
	}

	private List<CargoModel> toModel(List<Cargo> listCargos) {
		return listCargos
				.stream()
				.map(cargo -> conversionService.convert(cargo, CargoModel.class))
				.collect(Collectors.toList());
	}
}
