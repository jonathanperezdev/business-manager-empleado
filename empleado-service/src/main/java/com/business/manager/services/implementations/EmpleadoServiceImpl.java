package com.business.manager.services.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.business.manager.dao.entities.Cargo;
import com.business.manager.dao.entities.Empleado;
import com.business.manager.dao.entities.Ubicacion;
import com.business.manager.dao.repositories.CargoRepository;
import com.business.manager.dao.repositories.EmpleadoRepository;
import com.business.manager.dao.repositories.TipoDocumentoRepository;
import com.business.manager.dao.repositories.UbicacionRepository;
import com.business.manager.enums.CargosEnum;
import com.business.manager.enums.UbicacionesEnum;
import com.business.manager.exception.NoDataFoundException;
import com.business.manager.exception.OperationNotPosibleException;
import com.business.manager.exception.error.ErrorEnum;
import com.business.manager.model.EmpleadoModel;
import com.business.manager.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;

	@Autowired
	private UbicacionRepository ubicacionRepository;

	@Autowired
	@Qualifier("customConversionService")
	private ConversionService conversionService;

	@Override
	public EmpleadoModel findEmpleado(Long id) {
		EmpleadoModel empleado = conversionService.convert(empleadoRepository.findById(id).get(), EmpleadoModel.class);

		return empleado;
	}

	@Override
	public EmpleadoModel upsertEmpleado(EmpleadoModel empleadoModel) {
		Empleado empleado = empleadoRepository.
				findByTipoDocumentoAndNumeroDocumento(
						empleadoModel.getTipoDocumento(),
						empleadoModel.getNumeroDocumento());

		if(Objects.nonNull(empleado)) {
			throw new OperationNotPosibleException(ErrorEnum.EMPLEADO_DUPLICATE_TIPO_AND_NUMBER_DOC,
					tipoDocumentoRepository.findById(empleadoModel.getTipoDocumento()).get().getNombre(),
					empleadoModel.getNumeroDocumento());
		}

		empleado = empleadoRepository.save(conversionService.convert(empleadoModel, Empleado.class));
		return conversionService.convert(empleado, EmpleadoModel.class);
	}

	@Override
	public EmpleadoModel updateEmpleado(Long id, EmpleadoModel empleadoModel) {
		empleadoModel.setId(id);
		return upsertEmpleado(empleadoModel);
	}

	@Override
	public void deleteEmpleado(Long id) {
		empleadoRepository.deleteById(id);
	}

	@Override
	public List<EmpleadoModel> search(final Integer tipoDocumento,
									  final String numeroDocumento,
									  final String nombres,
									  final String apellidos){
    	
    	List<Empleado> listEmpleados = new ArrayList<>();
    	
    	if(numeroDocumento.isEmpty()){
    		listEmpleados = empleadoRepository
					.findByNombresContainingIgnoreCaseAndApellidosContainingIgnoreCase(nombres, apellidos);
    		
    		if(CollectionUtils.isEmpty(listEmpleados)) {
    			throw new NoDataFoundException(ErrorEnum.EMPLEADOS_NOT_FOUND, nombres, apellidos);
    		}
    	}else {
			Empleado empleado = findByTipoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    		listEmpleados.add(empleado);
    	}

    	return toModel(listEmpleados);
    }
	
	@Override
	public List<EmpleadoModel> searchWithTipoUbicacion(
			final Integer idUbicacion,
			final Integer tipoDocumento,
			final String numeroDocumento,
			final String nombres, 
			final String apellidos){

		Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion).get();
		List<Empleado> listEmpleados = new ArrayList<>();
    	
    	if(numeroDocumento.isEmpty()){
    		List<Integer> idCargos = new ArrayList<>();
    		idCargos.add(cargoRepository.findByNombreIgnoreCase(CargosEnum.OPERARIO.getNombre()).getId());
    		idCargos.add(cargoRepository.findByNombreIgnoreCase(CargosEnum.OFICIAL.getNombre()).getId());

    		listEmpleados = (UbicacionesEnum.OBRA.name().equalsIgnoreCase(ubicacion.getTipo().name()))
    				? empleadoRepository.findEmpleadosWithoutUbicacion(idCargos,
					nombres,
					apellidos,
					ubicacion.getOficialACargo())
    				:empleadoRepository
					.findByUbicacionIsNullAndCargoNotInAndNombresContainingIgnoreCaseAndApellidosContainingIgnoreCaseOrderByCargoAscNombresAsc(
							idCargos,
							nombres, 
							apellidos);
    		
    		if(CollectionUtils.isEmpty(listEmpleados)) {
    			throw new NoDataFoundException(ErrorEnum.EMPLEADOS_NOT_FOUND, nombres, apellidos);
    		}
    	}else {
			Empleado empleado = findByTipoAndNumeroDocumento(tipoDocumento, numeroDocumento);
			validateAddEmpleadoToUbicacion(ubicacion, empleado);
    		listEmpleados.add(empleado);
    	}
    	
    	return toModel(listEmpleados);
	}

	private void validateAddEmpleadoToUbicacion(Ubicacion ubicacion, Empleado empleado){

		if(empleado.getId().equals(ubicacion.getIngenieroACargo())){
			throw new OperationNotPosibleException(ErrorEnum.EMPLEADO_ALREADY_ASSIGNED,
					"ingeniero a cargo");
		}

		if(empleado.getId().equals(ubicacion.getOficialACargo())) {
			throw new OperationNotPosibleException(ErrorEnum.EMPLEADO_ALREADY_ASSIGNED,
					"oficial a cargo");
		}

		if(UbicacionesEnum.OBRA.name().equalsIgnoreCase(ubicacion.getTipo().name())
				&& !isCargoInOperarioOrOficial(empleado.getCargo())){
			throw new OperationNotPosibleException(ErrorEnum.EMPLEADO_OBRA);
		}

		if(!UbicacionesEnum.OBRA.name().equalsIgnoreCase(ubicacion.getTipo().name()) &&
				isCargoInOperarioOrOficial(empleado.getCargo())) {
			throw new OperationNotPosibleException(ErrorEnum.EMPLEADO_OFICINA);
		}

	}

	private boolean isCargoInOperarioOrOficial(Integer idCargo) {
		String cargo = cargoRepository.findById(idCargo).get().getNombre();

		return StringUtils.equalsAnyIgnoreCase(cargo,
				CargosEnum.OPERARIO.getNombre(),
				CargosEnum.OFICIAL.getNombre());
	}

	private Empleado findByTipoAndNumeroDocumento(Integer tipoDocumento, String numeroDocumento) {
		Empleado empleado =
				empleadoRepository
						.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
		if(Objects.isNull(empleado)) {
			throw new NoDataFoundException(ErrorEnum.EMPLEADO_NOT_FOUND,
					tipoDocumentoRepository.findById(tipoDocumento).get().getNombre(),
					numeroDocumento);
		}

		return empleado;
	}
    
    @Override
    public List<EmpleadoModel> searchByCargo(String cargoNombre){
    	Cargo cargo = cargoRepository.findByNombreIgnoreCase(cargoNombre);
    	List<EmpleadoModel> listEmpleados = toModel(empleadoRepository.findByCargoOrderByCargo(cargo.getId()));
    	
    	if(CollectionUtils.isEmpty(listEmpleados)) {
			throw new NoDataFoundException(ErrorEnum.EMPLEADOS_NOT_FOUND_BY_CARGO, cargoNombre);
		}
    	
    	return listEmpleados;
    }

    @Override
    public List<EmpleadoModel> findByUbicacion(Integer idUbicacion) {
    	List<Empleado> listEmpleados = empleadoRepository.findByUbicacionOrderByCargoAscNombresAsc(idUbicacion);
    	
    	return toModel(listEmpleados);
    }
    
    @Override
    public void updateUbicacion(List<Long> idEmpleados, Integer idUbicacion) {
    	idEmpleados.forEach(item-> {
    		Empleado empleado = empleadoRepository.findById(item).get();
    		empleado.setUbicacion(idUbicacion);
    		empleadoRepository.save(empleado);
    	});
    }

    private List<EmpleadoModel> toModel(List<Empleado> listEmpleados) {
		return listEmpleados
				.stream()
				.map(empleado -> conversionService.convert(empleado, EmpleadoModel.class))
				.collect(Collectors.toList());
	}
}
