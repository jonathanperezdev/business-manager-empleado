package com.business.manager.services;

import java.util.List;

import com.business.manager.model.EmpleadoModel;
import org.springframework.stereotype.Service;

@Service
public interface EmpleadoService {

	EmpleadoModel findEmpleado(Long id);
	EmpleadoModel upsertEmpleado(EmpleadoModel empleadoModel);
	EmpleadoModel updateEmpleado(Long id, EmpleadoModel empleadoModel);
	void deleteEmpleado(Long id);

	List<EmpleadoModel> search(final Integer tipoDocumento,
							   final String numeroDocumento,
							   final String nombres,
							   final String apellidos);

	List<EmpleadoModel> searchByCargo(String cargoNombre);
	
	List<EmpleadoModel> findByUbicacion(Integer idUbicacion);
	
	void updateUbicacion(List<Long> idEmpleados, Integer idUbicacion);

	List<EmpleadoModel> searchWithTipoUbicacion(String tipoUbicacion, Long id, String nombres, String apellidos);
	    
}
