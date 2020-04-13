package com.business.manager.converters.implementations;

import com.business.manager.dao.entities.Empleado;
import com.business.manager.dao.entities.TipoDocumento;
import com.business.manager.dao.entities.Ubicacion;
import com.business.manager.dao.repositories.TipoDocumentoRepository;
import com.business.manager.dao.repositories.UbicacionRepository;
import com.business.manager.model.EmpleadoModel;
import com.business.manager.model.TipoDocumentoModel;
import com.business.manager.model.UbicacionModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoModelConverter implements Converter<Empleado, EmpleadoModel>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    public EmpleadoModel convert(Empleado empleado) {
        EmpleadoModel empleadoModel = modelMapper.map(empleado, EmpleadoModel.class);

        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(empleado.getTipoDocumento()).get();
        empleadoModel.setTipoDocumentoModel(modelMapper.map(tipoDocumento, TipoDocumentoModel.class));

        Ubicacion ubicacion;
        if(empleado.getUbicacion() != null) {
            ubicacion = ubicacionRepository.findById(empleado.getUbicacion()).get();
            empleadoModel.setUbicacionModel(modelMapper.map(ubicacion, UbicacionModel.class));
        }

        return empleadoModel;
    }
}
