package com.business.manager.empleado.converters;

import com.business.manager.empleado.dao.entities.Empleado;
import com.business.manager.empleado.dao.entities.TipoDocumento;
import com.business.manager.empleado.dao.entities.Ubicacion;
import com.business.manager.empleado.dao.repositories.TipoDocumentoRepository;
import com.business.manager.empleado.dao.repositories.UbicacionRepository;
import com.business.manager.empleado.empleado.models.EmpleadoModel;
import com.business.manager.empleado.empleado.models.TipoDocumentoModel;
import com.business.manager.empleado.empleado.models.UbicacionModel;
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
