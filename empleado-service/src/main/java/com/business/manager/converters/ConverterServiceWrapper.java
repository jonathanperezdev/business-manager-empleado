package com.business.manager.converters;

public interface ConverterServiceWrapper {
    <T> T convert(Object source, Class<T> targetType);
}
