package com.business.manager.configuration;

import com.business.manager.converters.EmpleadoModelConverter;
import com.business.manager.converters.UbicacionModelConverter;
import com.business.manager.dao.entities.Empleado;
import com.business.manager.dao.entities.Ubicacion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private EmpleadoModelConverter empleadoModelConverter;

    @Autowired
    private UbicacionModelConverter ubicacionModelConverter;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(empleadoModelConverter);
        registry.addConverter((empleado) -> modelMapper.map(empleado, Empleado.class));
        registry.addConverter(ubicacionModelConverter);
        registry.addConverter((ubicacion) -> modelMapper.map(ubicacion, Ubicacion.class));
    }
}
