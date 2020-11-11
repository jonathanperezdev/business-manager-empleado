package com.business.manager.empleado.services;

import com.business.manager.empleado.dao.entities.Cargo;
import com.business.manager.empleado.empleado.models.CargoModel;

import java.util.List;

public interface CargoService {

    List<CargoModel> findAllCargos();
    CargoModel findCargo(Integer id);
    CargoModel upsertCargo(CargoModel cargoModel);
    CargoModel updateCargo(Integer id, CargoModel cargoModel);
    void deleteCargo(Integer id);

    Cargo findByNombre(String nombre);
}
