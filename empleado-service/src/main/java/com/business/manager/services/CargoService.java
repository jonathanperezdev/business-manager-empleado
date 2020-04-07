package com.business.manager.services;

import com.business.manager.dao.entities.Cargo;
import com.business.manager.exception.NoDataFoundException;
import com.business.manager.generic.GenericService;
import com.business.manager.model.CargoModel;

public interface CargoService extends GenericService<Cargo, CargoModel> {
    void deleteCargo(Long id);
    Cargo findByNombre(String nombre);
}
