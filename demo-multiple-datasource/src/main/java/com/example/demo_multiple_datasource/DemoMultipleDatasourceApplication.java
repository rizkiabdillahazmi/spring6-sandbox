package com.example.demo_multiple_datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DemoMultipleDatasourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMultipleDatasourceApplication.class, args);
	}

}
