package com.business.manager.dao.repositories;

import com.business.manager.dao.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
	
	List<Empleado> findByNombresContainingAndApellidosContaining(String nombres, String apellidos);
	List<Empleado> findByCargoOrderByCargo(Integer cargo);
	List<Empleado> findByUbicacion(Integer ubicacion);
	List<Empleado> findByUbicacionOrderByCargoAscNombresAsc(Integer ubicacion);
	
	List<Empleado> findByUbicacionAndCargoInAndNombresContainingAndApellidosContainingOrderByCargoAscNombresAsc(
            Long ubicacion,
            List<Integer> idCargos,
            String nombres,
            String apellidos);
	
	List<Empleado> findByUbicacionAndCargoNotInAndNombresContainingAndApellidosContainingOrderByCargoAscNombresAsc(
            Long ubicacion,
            List<Integer> idCargos,
            String nombres,
            String apellidos);
	
}
