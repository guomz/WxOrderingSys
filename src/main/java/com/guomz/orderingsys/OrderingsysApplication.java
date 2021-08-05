package com.guomz.orderingsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan(basePackages = "com.guomz.orderingsys.dao")
public class OrderingsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderingsysApplication.class, args);
	}

}
