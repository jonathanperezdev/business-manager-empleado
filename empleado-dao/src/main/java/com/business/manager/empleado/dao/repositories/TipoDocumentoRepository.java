package com.business.manager.empleado.dao.repositories;

import com.business.manager.empleado.dao.entities.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
    List<TipoDocumento> findAllByOrderByOrdenAsc();
}
