package com.guomz.orderingsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.guomz.orderingsys.dao")
public class OrderingsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderingsysApplication.class, args);
	}

}
