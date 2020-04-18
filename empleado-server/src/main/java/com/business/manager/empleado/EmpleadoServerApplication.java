package com.business.manager.empleado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.business.manager.empleado"})
@EntityScan(basePackages = {"com.business.manager.empleado.dao"})
@EnableDiscoveryClient
@RefreshScope
public class EmpleadoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpleadoServerApplication.class, args);
	}

}
