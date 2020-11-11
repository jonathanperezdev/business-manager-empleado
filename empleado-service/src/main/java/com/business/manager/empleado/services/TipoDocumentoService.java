package com.business.manager.empleado.services;

import com.business.manager.empleado.empleado.models.TipoDocumentoModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TipoDocumentoService {
	List<TipoDocumentoModel> getAllTipoDocumentos();
}
