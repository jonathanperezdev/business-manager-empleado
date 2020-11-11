package com.business.manager.empleado.configurations;

import com.business.manager.empleado.streams.EmpleadoOutputStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(EmpleadoOutputStreams.class)
public class StreamsConfig {
}
