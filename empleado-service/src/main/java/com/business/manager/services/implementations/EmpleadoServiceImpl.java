package com.business.manager.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.business.manager.dao.entities.Cargo;
import com.business.manager.dao.entities.Empleado;
import com.business.manager.dao.entities.Ubicacion;
import com.business.manager.dao.repositories.CargoRepository;
import com.business.manager.dao.repositories.EmpleadoRepository;
import com.business.manager.dao.repositories.UbicacionRepository;
import com.business.manager.enums.CargosEnum;
import com.business.manager.enums.UbicacionesEnum;
import com.business.manager.exception.NoDataFoundException;
import com.business.manager.exception.OperationNotPosibleException;
import com.business.manager.exception.error.ErrorEnum;
import com.business.manager.model.EmpleadoModel;
import com.business.manager.model.UbicacionModel;
import com.business.manager.services.EmpleadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private UbicacionRepository ubicacionRepository;

	private Function<Empleado, EmpleadoModel> toEmpleadoModel = empleado -> modelMapper.map(empleado, EmpleadoModel.class);
	private Function<EmpleadoModel, Empleado> toEmpleadoEntity = empleado -> modelMapper.map(empleado, Empleado.class);

	@Override
	public EmpleadoModel findEmpleado(Long id) {
		Ubicacion ubicacion;
		EmpleadoModel empleado = toEmpleadoModel.apply(empleadoRepository.findById(id).get());

		if(empleado.getIdUbicacion() != null) {
			ubicacion = ubicacionRepository.findById(empleado.getIdUbicacion()).get();
			empleado.setUbicacion(modelMapper.map(ubicacion, UbicacionModel.class));
		}

		return empleado;
	}

	@Override
	public EmpleadoModel upsertEmpleado(EmpleadoModel empleadoModel) {
		Empleado empleado = empleadoRepository.save(toEmpleadoEntity.apply(empleadoModel));
		return toEmpleadoModel.apply(empleado);
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
	public List<EmpleadoModel> search(final Long id,
									  final String nombres,
									  final String apellidos){
    	
    	List<Empleado> listEmpleados = new ArrayList<>();
    	
    	if(Objects.isNull(id)){
    		listEmpleados = empleadoRepository.findByNombresContainingAndApellidosContaining(nombres, apellidos);
    		
    		if(CollectionUtils.isEmpty(listEmpleados)) {
    			throw new NoDataFoundException(ErrorEnum.EMPLEADOS_NOT_FOUND, nombres, apellidos);
    		}
    	}else {
    		Optional<Empleado> optEmpleado = empleadoRepository.findById(id);
    		if(!optEmpleado.isPresent()) {
    			throw new NoDataFoundException(ErrorEnum.EMPLEADO_NOT_FOUND, id);
    		}
    		
    		listEmpleados.add(optEmpleado.get());
    	}
    	
    	return toModel(listEmpleados);
    }
	
	@Override
	public List<EmpleadoModel> searchWithTipoUbicacion(final String tipoUbicacion,
			final Long id,
			final String nombres, 
			final String apellidos){
		
		List<Empleado> listEmpleados = new ArrayList<>();
    	
    	if(Objects.isNull(id)){
    		List<Integer> idCargos = new ArrayList<>();
    		idCargos.add(cargoRepository.findByNombre(CargosEnum.OPERARIO.getNombre()).getId());
    		idCargos.add(cargoRepository.findByNombre(CargosEnum.OFICIAL.getNombre()).getId());
    		
    		
    		listEmpleados = (UbicacionesEnum.OBRA.getNombre().toUpperCase().equals(tipoUbicacion))
    				? empleadoRepository
    						.findByUbicacionAndCargoInAndNombresContainingAndApellidosContainingOrderByCargoAscNombresAsc(
    								null, 
    								idCargos, 
    								nombres, 
    								apellidos)
    				:empleadoRepository
					.findByUbicacionAndCargoNotInAndNombresContainingAndApellidosContainingOrderByCargoAscNombresAsc(
							null, 
							idCargos, 
							nombres, 
							apellidos);
    		
    		if(CollectionUtils.isEmpty(listEmpleados)) {
    			throw new NoDataFoundException(ErrorEnum.EMPLEADOS_NOT_FOUND, nombres, apellidos);
    		}
    	}else {
    		Optional<Empleado> optEmpleado = empleadoRepository.findById(id);
    		if(!optEmpleado.isPresent()) {
    			throw new NoDataFoundException(ErrorEnum.EMPLEADO_NOT_FOUND, id);
    		}
    		
    		String cargo = cargoRepository.findById(optEmpleado.get().getCargo()).get().getNombre();
    		
    		if(UbicacionesEnum.OBRA.getNombre().toUpperCase().equals(tipoUbicacion)
    				&& !cargo.toUpperCase().equals(CargosEnum.OPERARIO.getNombre().toUpperCase())
    				&& !cargo.toUpperCase().equals(CargosEnum.OFICIAL.getNombre().toUpperCase())) {
    			throw new OperationNotPosibleException(ErrorEnum.EMPLEADO_OBRA);
    		}else if(cargo.toUpperCase().equals(CargosEnum.OPERARIO.getNombre().toUpperCase()) 
    				|| cargo.toUpperCase().equals(CargosEnum.OFICIAL.getNombre().toUpperCase())) {
    			throw new OperationNotPosibleException(ErrorEnum.EMPLEADO_OFICINA);
    		}
    		
    		listEmpleados.add(optEmpleado.get());
    	}
    	
    	return toModel(listEmpleados);
    	
	}
    
    @Override
    public List<EmpleadoModel> searchByCargo(String cargoNombre){
    	Cargo cargo = cargoRepository.findByNombre(cargoNombre);
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

    private List<EmpleadoModel>  toModel(List<Empleado> listEmpleados) {
		return listEmpleados.stream().map(toEmpleadoModel).collect(Collectors.toList());
	}
}
