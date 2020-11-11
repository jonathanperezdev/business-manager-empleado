package com.business.manager.empleado.services.implementations;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.business.manager.empleado.dao.entities.Ubicacion;
import com.business.manager.empleado.dao.repositories.EmpleadoRepository;
import com.business.manager.empleado.dao.repositories.UbicacionRepository;
import com.business.manager.empleado.empleado.enums.StreamAction;
import com.business.manager.empleado.empleado.models.UbicacionModel;
import com.business.manager.empleado.empleado.models.streams.EmpleadoStreamModel;
import com.business.manager.empleado.empleado.models.streams.UbicacionStreamModel;
import com.business.manager.empleado.exceptions.NoDataFoundException;
import com.business.manager.empleado.exceptions.OperationNotPossibleException;
import com.business.manager.empleado.exceptions.errors.ErrorEnum;
import com.business.manager.empleado.services.EmpleadoService;
import com.business.manager.empleado.services.UbicacionService;
import com.business.manager.empleado.streams.EmpleadoOutputStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeTypeUtils;

@Service
public class UbicacionServiceImpl implements UbicacionService {

    @Autowired
	private UbicacionRepository ubicacionRepository;
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private EmpleadoOutputStreams empleadoOutputStreams;

	@Autowired
	@Qualifier("customConversionService")
	private ConversionService conversionService;

	@Override
	public List<UbicacionModel> findAllUbicaciones() {
		List<UbicacionModel> listUbicacionesModel = toModel(ubicacionRepository.findAllByOrderByTipoAscNombreAsc());
		if(CollectionUtils.isEmpty(listUbicacionesModel)) {
			throw new NoDataFoundException(ErrorEnum.UBICACIONES_NOT_FOUND);
		}
		return listUbicacionesModel;
	}

    @Override
    public UbicacionModel findUbicacion(Integer id) {
    	UbicacionModel ubicacion = conversionService.convert(ubicacionRepository.findById(id).get(), UbicacionModel.class);

    	return ubicacion;
    }

	@Override
	public UbicacionModel upsertUbicacion(UbicacionModel ubicacionModel) {
		Ubicacion ubicacion = ubicacionRepository.findByNombreIgnoreCase(ubicacionModel.getNombre());

		if(Objects.nonNull(ubicacion)) {
			throw new OperationNotPossibleException(ErrorEnum.UBICACION_ALREADY_EXIST, ubicacionModel.getNombre());
		}

		ubicacion = ubicacionRepository.save(conversionService.convert(ubicacionModel, Ubicacion.class));
		ubicacionModel = conversionService.convert(ubicacion, UbicacionModel.class);
		sendUbicacionMessage(UbicacionStreamModel.of(StreamAction.UPSERT, ubicacionModel));
		return ubicacionModel;
	}

	@Override
	public UbicacionModel updateUbicacion(Integer id, UbicacionModel ubicacionModel) {
		ubicacionModel.setId(id);
		Ubicacion ubicacion = ubicacionRepository.save(conversionService.convert(ubicacionModel, Ubicacion.class));
		sendUbicacionMessage(UbicacionStreamModel.of(StreamAction.UPSERT, ubicacionModel));
		return conversionService.convert(ubicacion, UbicacionModel.class);
	}
    
    @Override
    public void deleteUbicacion(Integer id) {
    	if(!CollectionUtils.isEmpty(empleadoRepository.findByUbicacion(id))) {
			throw new OperationNotPossibleException(ErrorEnum.DELETE_EMPLEADO_NOT_POSIBLE, "La ubicacion");
		}

    	UbicacionModel ubicacionModel = conversionService.convert(ubicacionRepository.findById(id).get(), UbicacionModel.class);
    	ubicacionRepository.deleteById(id);
		sendUbicacionMessage(UbicacionStreamModel.of(StreamAction.DELETE, ubicacionModel));
    }
    
    @Override
    public void configuraUbicacion(Integer idUbicacion,
    		Long ingeniero, 
    		Long oficial) {
    	Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion).get();
    	
    	ubicacion.setIngenieroACargo(ingeniero);    	
    	ubicacion.setOficialACargo(oficial);
    	    	
    	ubicacionRepository.save(ubicacion);
    }

    private List<UbicacionModel> toModel(List<Ubicacion> listUbicaciones) {
		return listUbicaciones
				.stream()
				.map(ubicacion -> conversionService.convert(ubicacion, UbicacionModel.class))
				.collect(Collectors.toList());
	}

	private void sendUbicacionMessage(UbicacionStreamModel ubicacion){
		MessageChannel messageChannel = empleadoOutputStreams.outboundUbicaciones();
		messageChannel.send(MessageBuilder
				.withPayload(ubicacion)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build());
	}
}
