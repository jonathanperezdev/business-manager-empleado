package com.business.manager.empleado.services;

import java.util.List;

import com.business.manager.empleado.empleado.enums.RiesgoLaboral;
import com.business.manager.empleado.empleado.models.EmpleadoModel;
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

	List<EmpleadoModel> searchWithTipoUbicacion(Integer idUbicacion,
												Integer tipoDocumento,
												String numeroDocumento,
												String nombres,
												String apellidos);

	RiesgoLaboral[] getRiesgosLaborales();
}
