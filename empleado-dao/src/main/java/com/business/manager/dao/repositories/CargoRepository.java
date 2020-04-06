package com.business.manager.dao.repositories;

import com.business.manager.dao.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
	Cargo findByNombre(String nombre);
}
