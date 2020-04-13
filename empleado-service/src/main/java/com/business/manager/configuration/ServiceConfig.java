package com.business.manager.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ServiceConfig {

    @Bean
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }

    @Bean
    public ConversionService conversionService () {
        DefaultConversionService service = new DefaultConversionService();
        return service;
    }
}
