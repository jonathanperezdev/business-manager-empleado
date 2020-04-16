package com.business.manager.empleado.dao.repositories;

import com.business.manager.empleado.dao.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
	final String EMPLEADOS_WITHOUT_UBICACION_QUERY =
			"select e " +
			"from Empleado e "+
					"where e.ubicacion is null " +
					"and e.cargo in (:cargos) " +
					"and lower(e.nombres) like lower(concat('%', :nombre,'%')) " +
					"and lower(e.apellidos) like lower(concat('%', :apellido,'%')) " +
					"and e.id != :idEmpleado";

	List<Empleado> findByNombresContainingIgnoreCaseAndApellidosContainingIgnoreCase(String nombres, String apellidos);
	List<Empleado> findByCargoOrderByCargo(Integer cargo);
	List<Empleado> findByUbicacion(Integer ubicacion);
	List<Empleado> findByUbicacionOrderByCargoAscNombresAsc(Integer ubicacion);
	
	@Query(EMPLEADOS_WITHOUT_UBICACION_QUERY)
	List<Empleado> findEmpleadosWithoutUbicacion(
			@Param("cargos") List<Integer> idCargos,
			@Param("nombre") String nombre,
			@Param("apellido") String apellido,
			@Param("idEmpleado") Long idEmpleado);
	
	List<Empleado> findByUbicacionIsNullAndCargoNotInAndNombresContainingIgnoreCaseAndApellidosContainingIgnoreCaseOrderByCargoAscNombresAsc(
            List<Integer> idCargos,
            String nombres,
            String apellidos);

	Empleado findByTipoDocumentoAndNumeroDocumento(
			Integer tipoDocumento,
			String numeroDocumento);
	
}
