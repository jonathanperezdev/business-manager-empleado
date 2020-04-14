package com.business.manager.dao.repositories;

import com.business.manager.dao.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
	Cargo findByNombreIgnoreCase(String nombre);
	List<Cargo> findAllByOrderByIdAsc();
}
