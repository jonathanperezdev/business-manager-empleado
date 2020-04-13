package com.business.manager.services.implementations;

import com.business.manager.dao.entities.Cargo;
import com.business.manager.dao.entities.TipoDocumento;
import com.business.manager.dao.repositories.CargoRepository;
import com.business.manager.dao.repositories.EmpleadoRepository;
import com.business.manager.exception.NoDataFoundException;
import com.business.manager.exception.OperationNotPosibleException;
import com.business.manager.exception.error.ErrorEnum;
import com.business.manager.model.CargoModel;
import com.business.manager.model.TipoDocumentoModel;
import com.business.manager.services.CargoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
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
		List<CargoModel> listCargosModel = toModel(cargoRepository.findAll());
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
		Cargo cargo = cargoRepository.save(conversionService.convert(cargoModel, Cargo.class));
		return conversionService.convert(cargo, CargoModel.class);
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

	private List<CargoModel> toModel(List<Cargo> listCargos) {
		return listCargos
				.stream()
				.map(cargo -> conversionService.convert(cargo, CargoModel.class))
				.collect(Collectors.toList());
	}
}
