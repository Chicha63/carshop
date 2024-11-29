package com.chicha.carshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CarshopApplication  {
	public static void main(String[] args) {
		SpringApplication.run(CarshopApplication.class, args);
	}

}
