package com.business.manager.empleado.dao.repositories;

import java.util.List;

import com.business.manager.empleado.dao.entities.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {
	List<Ubicacion> findAllByOrderByTipoAscNombreAsc();
	Ubicacion findByNombreIgnoreCase(String nombre);
}
