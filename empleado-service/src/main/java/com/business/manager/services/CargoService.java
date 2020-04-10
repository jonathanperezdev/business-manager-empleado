package com.business.manager.services;

import com.business.manager.dao.entities.Cargo;
import com.business.manager.model.CargoModel;

import java.util.List;

public interface CargoService {

    List<CargoModel> findAllCargos();
    CargoModel findCargo(Integer id);
    CargoModel upsertCargo(CargoModel cargoModel);
    CargoModel updateCargo(Integer id, CargoModel cargoModel);
    void deleteCargo(Integer id);

    Cargo findByNombre(String nombre);
}
