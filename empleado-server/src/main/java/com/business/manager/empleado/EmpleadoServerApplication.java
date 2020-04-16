package com.business.manager.empleado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.business.manager.empleado"})
@EntityScan(basePackages = {"com.business.manager.empleado.dao"})
public class EmpleadoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpleadoServerApplication.class, args);
	}

}
