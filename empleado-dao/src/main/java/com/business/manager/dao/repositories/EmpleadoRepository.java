package com.business.manager.dao.repositories;

import com.business.manager.dao.entities.Empleado;

import java.util.List;

public interface EmpleadoRepository /*extends JpaRepository<Empleado, Long>*/{
	
	/*List<Empleado> findByNombresContainingAndApellidosContaining(String nombres, String apellidos);
	List<Empleado> findByCargoOrderByCargo(Long cargo);
	List<Empleado> findByUbicacion(Long ubicacion);
	List<Empleado> findByUbicacionOrderByCargoAscNombresAsc(Long ubicacion);
	
	List<Empleado> findByUbicacionAndCargoInAndNombresContainingAndApellidosContainingOrderByCargoAscNombresAsc(
            Long ubicacion,
            List<Long> idCargos,
            String nombres,
            String apellidos);
	
	List<Empleado> findByUbicacionAndCargoNotInAndNombresContainingAndApellidosContainingOrderByCargoAscNombresAsc(
            Long ubicacion,
            List<Long> idCargos,
            String nombres,
            String apellidos);*/
	
}
