package com.business.manager.empleado.configurations;

import com.business.manager.empleado.converters.EmpleadoModelConverter;
import com.business.manager.empleado.converters.UbicacionModelConverter;
import com.business.manager.empleado.dao.entities.Cargo;
import com.business.manager.empleado.dao.entities.Empleado;
import com.business.manager.empleado.dao.entities.TipoDocumento;
import com.business.manager.empleado.dao.entities.Ubicacion;
import com.business.manager.empleado.empleado.model.CargoModel;
import com.business.manager.empleado.empleado.model.EmpleadoModel;
import com.business.manager.empleado.empleado.model.TipoDocumentoModel;
import com.business.manager.empleado.empleado.model.UbicacionModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConversionConfig {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmpleadoModelConverter empleadoModelConverter;

    @Autowired
    private UbicacionModelConverter ubicacionModelConverter;

    private Converter<EmpleadoModel, Empleado> empleadoEntityConverter = new Converter<EmpleadoModel, Empleado>() {
        @Override
        public Empleado convert(EmpleadoModel empleadoModel) {
            return modelMapper.map(empleadoModel, Empleado.class);
        }
    };

    private Converter<UbicacionModel, Ubicacion> ubicacionEntityConverter = new Converter<UbicacionModel, Ubicacion>() {
        @Override
        public Ubicacion convert(UbicacionModel ubicacionModel) {
            return modelMapper.map(ubicacionModel, Ubicacion.class);
        }
    };

    private Converter<TipoDocumentoModel, TipoDocumento> tipoDocumentoEntityConverter = new Converter<TipoDocumentoModel, TipoDocumento>() {
        @Override
        public TipoDocumento convert(TipoDocumentoModel tipoDocumentoModel) {
            return modelMapper.map(tipoDocumentoModel, TipoDocumento.class);
        }
    };

    private Converter<TipoDocumento, TipoDocumentoModel> tipoDocumentoModelConverter = new Converter<TipoDocumento, TipoDocumentoModel>() {
        @Override
        public TipoDocumentoModel convert(TipoDocumento tipoDocumento) {
            return modelMapper.map(tipoDocumento, TipoDocumentoModel.class);
        }
    };

    private Converter<CargoModel, Cargo> cargoEntityConverter = new Converter<CargoModel, Cargo>() {
        @Override
        public Cargo convert(CargoModel cargoModel) {
            return modelMapper.map(cargoModel, Cargo.class);
        }
    };

    private Converter<Cargo, CargoModel> cargoModelConverter = new Converter<Cargo, CargoModel>() {
        @Override
        public CargoModel convert(Cargo cargo) {
            return modelMapper.map(cargo, CargoModel.class);
        }
    };

    @Bean
    @Qualifier("customConversionService")
    public ConversionService customConversionService() {
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();

        converters.add(ubicacionEntityConverter);
        converters.add(ubicacionModelConverter);
        converters.add(empleadoEntityConverter);
        converters.add(empleadoModelConverter);
        converters.add(tipoDocumentoModelConverter);
        converters.add(tipoDocumentoEntityConverter);
        converters.add(cargoModelConverter);
        converters.add(cargoEntityConverter);

        factory.setConverters(converters);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}
