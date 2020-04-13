package com.business.manager.converters;

import com.business.manager.dao.entities.Empleado;
import com.business.manager.dao.entities.Ubicacion;
import com.business.manager.dao.repositories.EmpleadoRepository;
import com.business.manager.model.EmpleadoModel;
import com.business.manager.model.UbicacionModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UbicacionModelConverter implements Converter<Ubicacion, UbicacionModel>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public UbicacionModel convert(Ubicacion ubicacion) {
        UbicacionModel ubicacionModel = modelMapper.map(ubicacion, UbicacionModel.class);

        if(Objects.nonNull(ubicacionModel.getIngenieroACargo())) {
            ubicacionModel.setIngeniero(getEmpleadoById(ubicacionModel.getIngenieroACargo()));
        }

        if(Objects.nonNull(ubicacionModel.getOficialACargo())) {
            ubicacionModel.setIngeniero(getEmpleadoById(ubicacionModel.getOficialACargo()));
        }

        return ubicacionModel;
    }

    private EmpleadoModel getEmpleadoById(Long idEmpleado){
        Empleado empleado = empleadoRepository.findById(idEmpleado).get();
        EmpleadoModel empleadoModel =  modelMapper.map(empleado, EmpleadoModel.class);

        return empleadoModel;
    }
}
