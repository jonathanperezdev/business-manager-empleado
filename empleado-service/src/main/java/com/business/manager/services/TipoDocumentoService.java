package com.business.manager.services;

import com.business.manager.model.TipoDocumentoModel;
import com.business.manager.model.UbicacionModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TipoDocumentoService {
	List<TipoDocumentoModel> getAllTipoDocumentos();
}
