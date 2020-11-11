package com.business.manager.empleado.services.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.business.manager.empleado.dao.entities.Cargo;
import com.business.manager.empleado.dao.entities.Empleado;
import com.business.manager.empleado.dao.entities.Ubicacion;
import com.business.manager.empleado.dao.repositories.CargoRepository;
import com.business.manager.empleado.dao.repositories.EmpleadoRepository;
import com.business.manager.empleado.dao.repositories.TipoDocumentoRepository;
import com.business.manager.empleado.dao.repositories.UbicacionRepository;
import com.business.manager.empleado.empleado.enums.RiesgoLaboral;
import com.business.manager.empleado.empleado.enums.StreamAction;
import com.business.manager.empleado.empleado.models.EmpleadoModel;
import com.business.manager.empleado.empleado.enums.CargosEnum;
import com.business.manager.empleado.exceptions.NoDataFoundException;
import com.business.manager.empleado.exceptions.OperationNotPossibleException;
import com.business.manager.empleado.exceptions.errors.ErrorEnum;
import com.business.manager.empleado.empleado.enums.UbicacionesEnum;
import com.business.manager.empleado.services.EmpleadoService;
import com.business.manager.empleado.streams.EmpleadoOutputStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MimeTypeUtils;
import com.business.manager.empleado.empleado.models.streams.EmpleadoStreamModel;

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
	private EmpleadoOutputStreams empleadoOutputStreams;

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
			throw new OperationNotPossibleException(ErrorEnum.EMPLEADO_DUPLICATE_TIPO_AND_NUMBER_DOC,
					tipoDocumentoRepository.findById(empleadoModel.getTipoDocumento()).get().getNombre(),
					empleadoModel.getNumeroDocumento());
		}

		empleado = empleadoRepository.save(conversionService.convert(empleadoModel, Empleado.class));
		empleadoModel = conversionService.convert(empleado, EmpleadoModel.class);
		sendEmpleadoMessage(EmpleadoStreamModel.of(StreamAction.UPSERT, empleadoModel));
		return empleadoModel;
	}

	@Override
	public EmpleadoModel updateEmpleado(Long id, EmpleadoModel empleadoModel) {
		empleadoModel.setId(id);

		Empleado empleado = empleadoRepository.save(conversionService.convert(empleadoModel, Empleado.class));
		sendEmpleadoMessage(EmpleadoStreamModel.of(StreamAction.UPSERT, empleadoModel));
		return conversionService.convert(empleado, EmpleadoModel.class);
	}

	@Override
	public void deleteEmpleado(Long id) {
		Empleado empleado = empleadoRepository.findById(id).get();
		empleadoRepository.deleteById(id);
		sendEmpleadoMessage(EmpleadoStreamModel.of(StreamAction.UPSERT, conversionService.convert(empleado, EmpleadoModel.class)));
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
			throw new OperationNotPossibleException(ErrorEnum.EMPLEADO_ALREADY_ASSIGNED,
					"ingeniero a cargo");
		}

		if(empleado.getId().equals(ubicacion.getOficialACargo())) {
			throw new OperationNotPossibleException(ErrorEnum.EMPLEADO_ALREADY_ASSIGNED,
					"oficial a cargo");
		}

		if(UbicacionesEnum.OBRA.name().equalsIgnoreCase(ubicacion.getTipo().name())
				&& !isCargoInOperarioOrOficial(empleado.getCargo())){
			throw new OperationNotPossibleException(ErrorEnum.EMPLEADO_OBRA);
		}

		if(!UbicacionesEnum.OBRA.name().equalsIgnoreCase(ubicacion.getTipo().name()) &&
				isCargoInOperarioOrOficial(empleado.getCargo())) {
			throw new OperationNotPossibleException(ErrorEnum.EMPLEADO_OFICINA);
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
			sendEmpleadoMessage(EmpleadoStreamModel.of(StreamAction.DELETE, conversionService.convert(empleado, EmpleadoModel.class)));
    	});
    }

    private List<EmpleadoModel> toModel(List<Empleado> listEmpleados) {
		return listEmpleados
				.stream()
				.map(empleado -> conversionService.convert(empleado, EmpleadoModel.class))
				.collect(Collectors.toList());
	}

	private void sendEmpleadoMessage(EmpleadoStreamModel empleado){
		MessageChannel messageChannel = empleadoOutputStreams.outboundEmpleados();
		messageChannel.send(MessageBuilder
				.withPayload(empleado)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build());
	}

	@Override
	public RiesgoLaboral[] getRiesgosLaborales(){
		return RiesgoLaboral.values();
	}
}
