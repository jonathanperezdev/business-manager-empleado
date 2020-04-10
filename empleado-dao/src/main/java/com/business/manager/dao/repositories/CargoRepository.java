package com.business.manager.dao.repositories;

import com.business.manager.dao.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
	Cargo findByNombre(String nombre);
}
