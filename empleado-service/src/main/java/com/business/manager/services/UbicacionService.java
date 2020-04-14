package com.business.manager.services;

import java.util.List;

import com.business.manager.model.UbicacionModel;
import org.springframework.stereotype.Service;

@Service
public interface UbicacionService{

	List<UbicacionModel> findAllUbicaciones();
	UbicacionModel findUbicacion(Integer id);
	UbicacionModel upsertUbicacion(UbicacionModel ubicacionModel);
	UbicacionModel updateUbicacion(Integer id, UbicacionModel ubicacionModel);
	void deleteUbicacion(Integer id);
	
    void configuraUbicacion(Integer idUbicacion,
    	Long ingeniero, 
    	Long oficial);
}
