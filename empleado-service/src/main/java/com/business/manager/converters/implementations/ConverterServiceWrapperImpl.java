package com.business.manager.converters.implementations;

import com.business.manager.converters.ConverterServiceWrapper;
import com.business.manager.dao.entities.Empleado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ConverterServiceWrapperImpl implements ConverterServiceWrapper {
    
    @Autowired
    private DefaultConversionService conversionService;

    @Autowired
    private EmpleadoModelConverter empleadoModelConverter;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    private void init() {
        conversionService.addConverter(empleadoModelConverter);
        conversionService.addConverter(empleado -> modelMapper.map(empleado, Empleado.class));
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType){
        return conversionService.convert(source, targetType);
    }
}
