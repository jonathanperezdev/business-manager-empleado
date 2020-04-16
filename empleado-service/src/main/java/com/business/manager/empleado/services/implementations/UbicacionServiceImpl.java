package com.business.manager.empleado.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import com.business.manager.empleado.dao.entities.Ubicacion;
import com.business.manager.empleado.dao.repositories.EmpleadoRepository;
import com.business.manager.empleado.dao.repositories.UbicacionRepository;
import com.business.manager.empleado.empleado.model.UbicacionModel;
import com.business.manager.empleado.exception.NoDataFoundException;
import com.business.manager.empleado.exception.OperationNotPosibleException;
import com.business.manager.empleado.exception.error.ErrorEnum;
import com.business.manager.empleado.services.EmpleadoService;
import com.business.manager.empleado.services.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UbicacionServiceImpl implements UbicacionService {

    @Autowired
	private UbicacionRepository ubicacionRepository;
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private EmpleadoService empleadoService;

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
		Ubicacion ubicacion = conversionService.convert(ubicacionModel, Ubicacion.class);
		ubicacionRepository.save(ubicacion);
		return conversionService.convert(ubicacion, UbicacionModel.class);
	}

	@Override
	public UbicacionModel updateUbicacion(Integer id, UbicacionModel ubicacionModel) {
		ubicacionModel.setId(id);
		return upsertUbicacion(ubicacionModel);
	}
    
    @Override
    public void deleteUbicacion(Integer id) {
    	if(!CollectionUtils.isEmpty(empleadoRepository.findByUbicacion(id))) {
			throw new OperationNotPosibleException(ErrorEnum.DELETE_EMPLEADO_NOT_POSIBLE, "La ubicacion");
		}
    	
    	ubicacionRepository.deleteById(id);
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
}
