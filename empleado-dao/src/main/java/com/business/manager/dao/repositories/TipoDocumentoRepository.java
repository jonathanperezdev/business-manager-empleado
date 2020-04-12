package com.business.manager.dao.repositories;

import com.business.manager.dao.entities.Empleado;
import com.business.manager.dao.entities.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
    List<TipoDocumento> findAllByOrderByOrdenAsc();
}
